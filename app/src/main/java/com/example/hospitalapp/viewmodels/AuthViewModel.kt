package com.example.hospitalapp.viewmodels

import android.app.Activity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hospitalapp.db.FirebaseRepo
import com.example.hospitalapp.db.SignUpData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.FirebaseException
import java.util.concurrent.TimeUnit

class AuthViewModel : ViewModel() {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _authState = MutableLiveData<AuthState>()
    val authState: LiveData<AuthState> = _authState

    // OTP related properties
    private var storedVerificationId: String? = null
    private var resendToken: PhoneAuthProvider.ForceResendingToken? = null

    private val _otpState = MutableLiveData<OtpState>()
    val otpState: LiveData<OtpState> = _otpState

    init {
        checkAuthState()
    }

    fun checkAuthState() {
        if (auth.currentUser == null) {
            _authState.value = AuthState.Unauthenticated
        } else {
            _authState.value = AuthState.Authenticated
        }
    }

    fun login(email: String, password: String) {

        if (email.isEmpty() || password.isEmpty()) {
            _authState.value = AuthState.Error("Email and password cannot be empty")
            return
        }
        _authState.value = AuthState.Loading
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _authState.value = AuthState.Authenticated
                } else {
                    _authState.value = AuthState.Error(task.exception?.message ?: "Login failed")
                }
            }
    }

    fun signUp(signUpData: SignUpData, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        val email = if (signUpData.email.isNotEmpty()) {
            signUpData.email
        } else {
            // Create a dummy email for Firebase Auth when using phone number
            "${signUpData.phoneNumber.replace("+", "").replace(" ", "")}@hospitalapp.com"
        }
        val password = signUpData.password

        if ((signUpData.email.isEmpty() && signUpData.phoneNumber.isEmpty()) || password.isEmpty()) {
            onFailure("Email/phone and password cannot be empty")
            return
        }

        _authState.value = AuthState.Loading

        // First create Firebase Auth account
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Then save user data to Realtime Database
                    FirebaseRepo.addUser(
                        signUpData,
                        onSuccess = {
                            _authState.value = AuthState.Authenticated
                            onSuccess()
                        },
                        onFailure = { error: String ->
                            _authState.value = AuthState.Error("Failed to save user data: $error")
                            onFailure(error)
                        }
                    )
                } else {
                    val errorMessage = task.exception?.message ?: "SignUp failed"
                    _authState.value = AuthState.Error(errorMessage)
                    onFailure(errorMessage)
                }
            }
    }

    fun signOut() {
        auth.signOut()
        _authState.value = AuthState.Unauthenticated
    }

    fun sendOtp(phoneNumber: String, activity: Activity) {
        _otpState.value = OtpState.Loading

        val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                // Auto-verification completed
                signInWithPhoneAuthCredential(credential)
            }

            override fun onVerificationFailed(e: FirebaseException) {
                _otpState.value = OtpState.Error(e.message ?: "Verification failed")
            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                storedVerificationId = verificationId
                resendToken = token
                _otpState.value = OtpState.CodeSent
            }
        }

        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(activity)
            .setCallbacks(callbacks)
            .build()

        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    fun verifyOtp(code: String, signUpData: SignUpData? = null) {
        if (storedVerificationId == null) {
            _otpState.value = OtpState.Error("Verification ID not found")
            return
        }

        _otpState.value = OtpState.Loading
        val credential = PhoneAuthProvider.getCredential(storedVerificationId!!, code)

        if (signUpData != null) {
            // For sign up flow - sign in and save user data
            signInWithPhoneAuthCredential(credential, signUpData)
        } else {
            // For login flow - just sign in
            signInWithPhoneAuthCredential(credential)
        }
    }

    fun resendOtp(phoneNumber: String, activity: Activity) {
        if (resendToken == null) {
            // First time sending
            sendOtp(phoneNumber, activity)
            return
        }

        _otpState.value = OtpState.Loading

        val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                signInWithPhoneAuthCredential(credential)
            }

            override fun onVerificationFailed(e: FirebaseException) {
                _otpState.value = OtpState.Error(e.message ?: "Verification failed")
            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                storedVerificationId = verificationId
                resendToken = token
                _otpState.value = OtpState.CodeSent
            }
        }

        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(activity)
            .setCallbacks(callbacks)
            .setForceResendingToken(resendToken!!)
            .build()

        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    private fun signInWithPhoneAuthCredential(
        credential: PhoneAuthCredential,
        signUpData: SignUpData? = null
    ) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    if (signUpData != null) {
                        // Save user data for sign up
                        FirebaseRepo.addUser(
                            signUpData,
                            onSuccess = {
                                _authState.value = AuthState.Authenticated
                                _otpState.value = OtpState.Verified
                            },
                            onFailure = { error ->
                                _authState.value =
                                    AuthState.Error("Failed to save user data: $error")
                                _otpState.value = OtpState.Error("Failed to save user data: $error")
                            }
                        )
                    } else {
                        _authState.value = AuthState.Authenticated
                        _otpState.value = OtpState.Verified
                    }
                } else {
                    val errorMessage = task.exception?.message ?: "OTP verification failed"
                    _authState.value = AuthState.Error(errorMessage)
                    _otpState.value = OtpState.Error(errorMessage)
                }
            }
    }

}

sealed class AuthState {
    object Authenticated : AuthState()
    object Unauthenticated : AuthState()
    object Loading : AuthState()
    data class Error(val message: String) : AuthState()
}

sealed class OtpState {
    object Idle : OtpState()
    object Loading : OtpState()
    object CodeSent : OtpState()
    object Verified : OtpState()
    data class Error(val message: String) : OtpState()
}
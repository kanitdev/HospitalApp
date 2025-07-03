package com.example.hospitalapp.screens.logInandSignIn

import android.app.Activity
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.hospitalapp.R
import com.example.hospitalapp.db.SignUpData
import com.example.hospitalapp.ui.components.CustomDatePickerDialog
import com.example.hospitalapp.ui.components.OtpDialog
import com.example.hospitalapp.ui.theme.Inter
import com.example.hospitalapp.viewmodels.AuthViewModel
import com.example.hospitalapp.viewmodels.OtpState


@Composable
fun SignInScreen(modifier: Modifier, navController: NavController, authViewModel: AuthViewModel) {
    var name by remember { mutableStateOf("") }
    var selectedButton by remember { mutableStateOf("email") }
    var emailOrPhoneValue by remember { mutableStateOf("") }
    var dobText by remember { mutableStateOf("") }
    var showDatePickerDialog by remember { mutableStateOf(false) }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }

    // OTP dialog states
    var showOtpDialog by remember { mutableStateOf(false) }
    var signUpDataForOtp by remember { mutableStateOf<SignUpData?>(null) }
    var otp by remember { mutableStateOf("") }

    val authState = authViewModel.authState.observeAsState()
    val otpState = authViewModel.otpState.observeAsState()
    val context = LocalContext.current





    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 0.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),

                    ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 32.dp, bottom = 18.dp)
                    )
                    {

                        IconButton(
                            onClick = ({
                                navController.navigate("LoginScreen")
                            }),
                            modifier = Modifier
                                .padding(start = 18.dp, end = 18.dp)
                                .height(18.66.dp)
                                .width(19.42.dp)

                        ) {
                            Icon(
                                painter = painterResource(R.drawable.back_icon),
                                contentDescription = null,
                            )
                        }


                        Text(
                            text = "Create Account",
                            color = Color(0xFFE94D88),
                            fontSize = 20.sp,
                            fontFamily = FontFamily.Default,
                            fontWeight = FontWeight.W500
                        )
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 24.dp, end = 24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center

                    ) {
                        Row(
                            horizontalArrangement = Arrangement.Start,
                            modifier = Modifier.fillMaxWidth()
                        )
                        {
                            Text(
                                text = "Full Name",
                                color = Color(0xFF374151),
                                fontSize = 14.sp,
                                fontFamily = FontFamily.Default,
                                fontWeight = FontWeight.W500,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                        }

                        TextField(
                            value = name,
                            onValueChange = { name = it },
                            textStyle = TextStyle(
                                color = Color(0xFF374151)
                            ),
                            modifier = Modifier
                                .fillMaxWidth(),
                            placeholder = {
                                Text(
                                    text = "Enter your full name",
                                    color = Color(0xFF9CA3AF),
                                    fontSize = 14.sp,
                                    fontFamily = Inter,
                                    fontWeight = FontWeight.W500,
                                )
                            },
                            singleLine = true,
                            colors = TextFieldDefaults.colors(
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent,
                                disabledContainerColor = Color.Transparent,
                                focusedContainerColor = Color(0xFFFFF1F6),
                                unfocusedContainerColor = Color(0xFFFFF1F6)

                            ),
                            shape = RoundedCornerShape(8.dp),
                        )
                        Row(
                            modifier = Modifier
                                .padding(top = 6.dp)
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(16.dp)

                        ) {
                            Button(
                                onClick = { selectedButton = "email" },
                                modifier = Modifier.weight(1f),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (selectedButton == "email") Color(0xFFD53F8C) else Color(
                                        0xFFFFF1F6
                                    ),
                                    contentColor = if (selectedButton == "email") Color.White else Color(
                                        0xFF4B5563
                                    )
                                )
                            ) {
                                Text(text = "Email")
                            }
                            Button(
                                onClick = { selectedButton = "phone" },
                                modifier = Modifier.weight(1f),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (selectedButton == "phone") Color(0xFFD53F8C) else Color(
                                        0xFFFFF1F6
                                    ),
                                    contentColor = if (selectedButton == "phone") Color.White else Color(
                                        0xFF4B5563
                                    )
                                )

                            ) {
                                Text(text = "Phone")
                            }
                        }
                        Row(
                            horizontalArrangement = Arrangement.Start,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 12.dp)
                        )
                        {
                            Text(
                                text = if (selectedButton == "email") "Email" else "Mobile Number",
                                color = Color(0xFF374151),
                                fontSize = 14.sp,
                                fontFamily = FontFamily.Default,
                                fontWeight = FontWeight.W500,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                        }
                        TextField(
                            value = emailOrPhoneValue,
                            onValueChange = { emailOrPhoneValue = it },
                            textStyle = TextStyle(
                                color = Color(0xFF374151)
                            ),
                            modifier = Modifier
                                .fillMaxWidth(),
                            placeholder = {
                                Text(
                                    text = if (selectedButton == "email") "example@example.com" else "+1 234 567 890",
                                    color = Color(0xFF374151),
                                    fontSize = 14.sp,
                                    fontFamily = Inter,
                                    fontWeight = FontWeight.W500,
                                )
                            },
                            singleLine = true,
                            colors = TextFieldDefaults.colors(
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent,
                                disabledContainerColor = Color.Transparent,
                                focusedContainerColor = Color(0xFFFFF1F6),
                                unfocusedContainerColor = Color(0xFFFFF1F6)

                            ),
                            shape = RoundedCornerShape(8.dp),
                        )
                        Row(
                            horizontalArrangement = Arrangement.Start,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 12.dp)
                        )
                        {
                            Text(
                                text = "Date Of Birth",
                                color = Color(0xFF374151),
                                fontSize = 14.sp,
                                fontFamily = FontFamily.Default,
                                fontWeight = FontWeight.W500,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                        }

                        TextField(
                            value = dobText,
                            onValueChange = { dobText = it },
                            textStyle = TextStyle(
                                color = Color(0xFF374151)
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { showDatePickerDialog = true },
                            placeholder = {
                                Text(
                                    text = "DD /MM /YYYY",
                                    color = Color(0xFF374151),
                                    fontSize = 14.sp,
                                    fontFamily = Inter,
                                    fontWeight = FontWeight.W500,
                                )
                            },
                            singleLine = true,
                            colors = TextFieldDefaults.colors(
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent,
                                disabledContainerColor = Color.Transparent,
                                focusedContainerColor = Color(0xFFFFF1F6),
                                unfocusedContainerColor = Color(0xFFFFF1F6)

                            ),
                            shape = RoundedCornerShape(8.dp),
                            trailingIcon = {
                                IconButton(onClick = { showDatePickerDialog = true }) {
                                    Icon(
                                        painter = painterResource(R.drawable.dob_icon),
                                        contentDescription = "dob",
                                        modifier = Modifier.clickable {
                                            showDatePickerDialog = true
                                        }
                                    )
                                }
                            },
                            readOnly = true
                        )
                        if (showDatePickerDialog) {
                            CustomDatePickerDialog(
                                showDialog = showDatePickerDialog,
                                onDismiss = { showDatePickerDialog = false },
                                onDateSelected = { day, month, year ->
                                    dobText = String.format("%02d/%02d/%04d", day, month, year)
                                    showDatePickerDialog = false
                                }
                            )
                        }
                        Row(
                            horizontalArrangement = Arrangement.Start,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 12.dp)
                        )
                        {
                            Text(
                                text = "Password",
                                color = Color(0xFF374151),
                                fontSize = 14.sp,
                                fontFamily = FontFamily.Default,
                                fontWeight = FontWeight.W500,
                                modifier = Modifier.padding(bottom = 4.dp)
                            )
                        }

                        TextField(
                            value = password,
                            onValueChange = { password = it },
                            textStyle = TextStyle(
                                color = Color(0xFF374151)
                            ),
                            modifier = Modifier
                                .fillMaxWidth(),
                            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                            trailingIcon = {
                                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                    Icon(
                                        painter = painterResource(R.drawable.eye_icon),
                                        contentDescription = "password toggle"
                                    )
                                }
                            },
                            placeholder = {
                                Text(
                                    text = "•••••••••••••",
                                    color = Color(0xFF9CA3AF),
                                    fontSize = 14.sp,
                                    fontFamily = Inter,
                                    fontWeight = FontWeight.W500,
                                )
                            },
                            singleLine = true,
                            colors = TextFieldDefaults.colors(
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent,
                                disabledContainerColor = Color.Transparent,
                                focusedContainerColor = Color(0xFFFFF1F6),
                                unfocusedContainerColor = Color(0xFFFFF1F6)

                            ),
                            shape = RoundedCornerShape(8.dp),
                        )
                        Row(
                            horizontalArrangement = Arrangement.Start,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp)
                        )
                        {
                            Text(
                                text = "Confirm Password",
                                color = Color(0xFF374151),
                                fontSize = 14.sp,
                                fontFamily = FontFamily.Default,
                                fontWeight = FontWeight.W500,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                        }

                        TextField(
                            value = confirmPassword,
                            onValueChange = { confirmPassword = it },
                            textStyle = TextStyle(
                                color = Color(0xFF374151)
                            ),
                            modifier = Modifier
                                .fillMaxWidth(),
                            visualTransformation = if (confirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                            trailingIcon = {
                                IconButton(onClick = {
                                    confirmPasswordVisible = !confirmPasswordVisible
                                }) {
                                    Icon(
                                        painter = painterResource(R.drawable.eye_icon),
                                        contentDescription = "password toggle"
                                    )
                                }
                            },
                            placeholder = {
                                Text(
                                    text = "•••••••••••••",
                                    color = Color(0xFF9CA3AF),
                                    fontSize = 14.sp,
                                    fontFamily = Inter,
                                    fontWeight = FontWeight.W500,
                                )
                            },
                            singleLine = true,
                            colors = TextFieldDefaults.colors(
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent,
                                disabledContainerColor = Color.Transparent,
                                focusedContainerColor = Color(0xFFFFF1F6),
                                unfocusedContainerColor = Color(0xFFFFF1F6)

                            ),
                            shape = RoundedCornerShape(8.dp),
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "By continuing, you agree to ",
                                color = Color(0xFF4B5563),
                                fontSize = 12.sp,
                                fontWeight = FontWeight.W400
                            )

                            TextButton(
                                onClick = ({ TODO() }),
                                contentPadding = PaddingValues(0.dp),
                                colors = ButtonDefaults.textButtonColors(
                                    contentColor = Color(
                                        0xFF1F2937
                                    )
                                )
                            ) {
                                Text(
                                    text = "Terms of Use",
                                    fontWeight = FontWeight.Bold,

                                    )
                            }

                            Text(
                                text = " and ",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color(0xFF4B5563)
                            )

                            TextButton(
                                onClick = ({ TODO() }),
                                contentPadding = PaddingValues(0.dp),
                                colors = ButtonDefaults.textButtonColors(
                                    contentColor = Color(
                                        0xFF1F2937
                                    )
                                )
                            ) {
                                Text(
                                    text = "Privacy Policy",
                                    fontWeight = FontWeight.Bold,
                                    style = MaterialTheme.typography.bodySmall,
                                    maxLines = 1
                                )
                            }

                            Text(
                                text = ".",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.Gray
                            )
                        }

                        val context = LocalContext.current

                        Button(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 8.dp),
                            elevation = ButtonDefaults.buttonElevation(8.dp),
                            onClick = {
                                when {
                                    name.trim().isEmpty() -> {
                                        Toast.makeText(
                                            context,
                                            "Please enter your full name",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }

                                    emailOrPhoneValue.trim().isEmpty() -> {
                                        Toast.makeText(
                                            context,
                                            "Please enter your ${if (selectedButton == "email") "email" else "phone number"}",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }

                                    dobText.trim().isEmpty() -> {
                                        Toast.makeText(
                                            context,
                                            "Please select your date of birth",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }

                                    password.length < 6 -> {
                                        Toast.makeText(
                                            context,
                                            "Password must be at least 6 characters long.",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }

                                    password != confirmPassword -> {
                                        Toast.makeText(
                                            context,
                                            "Oops! Your passwords are having a disagreement. Make them friends! 🔒😅",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }

                                    else -> {
                                        val signUpData = if (selectedButton == "email") {
                                            SignUpData(
                                                email = emailOrPhoneValue.trim(),
                                                password = password,
                                                name = name.trim(),
                                                phoneNumber = "",
                                                dateOfBirth = dobText
                                            )
                                        } else {
                                            SignUpData(
                                                email = "",
                                                password = password,
                                                name = name.trim(),
                                                phoneNumber = emailOrPhoneValue.trim(),
                                                dateOfBirth = dobText
                                            )
                                        }

                                        if (selectedButton == "email") {
                                            // Email sign up - use existing method
                                            authViewModel.signUp(
                                                signUpData,
                                                onSuccess = {
                                                    Toast.makeText(
                                                        context,
                                                        "Account created successfully! ",
                                                        Toast.LENGTH_SHORT
                                                    ).show()
                                                    navController.navigate("LoginScreen")
                                                },
                                                onFailure = { error ->
                                                    Toast.makeText(
                                                        context,
                                                        "Failed to create account: $error",
                                                        Toast.LENGTH_LONG
                                                    ).show()
                                                }
                                            )
                                        } else {
                                            // Phone sign up - use OTP verification
                                            signUpDataForOtp = signUpData
                                            val activity = context as? Activity
                                            if (activity != null) {
                                                authViewModel.sendOtp(
                                                    emailOrPhoneValue.trim(),
                                                    activity
                                                )
                                                showOtpDialog = true
                                            } else {
                                                Toast.makeText(
                                                    context,
                                                    "Unable to send OTP. Please try again.",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            }
                                        }
                                    }
                                }
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFD53F8C),
                                contentColor = Color.White
                            ),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(text = "Sign Up")
                        }

                        if (showOtpDialog) {
                            OtpDialog(
                                isVisible = showOtpDialog,
                                phoneNumber = emailOrPhoneValue.trim(),
                                onDismiss = { showOtpDialog = false },
                                onVerify = { enteredOtp ->
                                    signUpDataForOtp?.let { signUpData ->
                                        authViewModel.verifyOtp(enteredOtp, signUpData)
                                    }
                                },
                                onResend = {
                                    val activity = context as? Activity
                                    if (activity != null) {
                                        authViewModel.resendOtp(emailOrPhoneValue.trim(), activity)
                                    }
                                },
                                isLoading = otpState.value is OtpState.Loading
                            )
                        }

                        // Handle OTP state changes
                        when (otpState.value) {
                            is OtpState.Verified -> {
                                showOtpDialog = false
                                Toast.makeText(
                                    context,
                                    "Account created successfully!",
                                    Toast.LENGTH_SHORT
                                ).show()
                                navController.navigate("LoginScreen")
                            }

                            is OtpState.Error -> {
                                Toast.makeText(
                                    context,
                                    "OTP verification failed: ${(otpState.value as OtpState.Error).message}",
                                    Toast.LENGTH_LONG
                                ).show()
                            }

                            else -> {}
                        }

                        Text(
                            text = "or sign up with",
                            color = Color(0xFF6B7280),
                            fontFamily = FontFamily.Default,
                            fontWeight = FontWeight.W400,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        )
                        {
                            IconButton(
                                onClick = { /*TODO*/ },
                                modifier = Modifier
                                    .size(56.dp)
                                    .clip(CircleShape)
                                    .background(color = Color.White)
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.icon_apple),
                                    contentDescription = null,

                                    )
                            }

                            IconButton(
                                onClick = { /*TODO*/ },
                                modifier = Modifier
                                    .size(56.dp)
                                    .clip(CircleShape)
                                    .background(color = Color.White)
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.icon_google),
                                    contentDescription = null,

                                    )
                            }

                        }

                        Row(
                            modifier = Modifier
                                .wrapContentSize()
                                .padding(bottom = 32.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Already have an account?",
                                fontSize = 14.sp,
                                fontFamily = FontFamily.Default,
                                fontWeight = FontWeight.W400,
                                color = Color(0xFF4B5563),
                            )
                            TextButton(
                                onClick = {
                                    navController.navigate("LoginScreen")
                                },
                                contentPadding = PaddingValues(0.dp)
                            ) {
                                Text(
                                    text = "Login",
                                    fontSize = 14.sp,
                                    fontFamily = FontFamily.Default,
                                    fontWeight = FontWeight.W600,
                                    color = Color(0xFFD53F8C),
                                )
                            }


                        }


                    }


                }
            }
        }
    }
}










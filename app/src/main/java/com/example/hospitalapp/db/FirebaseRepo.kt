package com.example.hospitalapp.db

import android.util.Log
import com.google.firebase.database.FirebaseDatabase

object FirebaseRepo {
    private val db = FirebaseDatabase.getInstance().getReference("users")

    fun addUser(user: SignUpData, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        try {
            Log.d("FirebaseRepo", "Attempting to save user: $user")
            val userId = db.push().key

            if (userId == null) {
                Log.e("FirebaseRepo", "Failed to generate user ID")
                onFailure("Failed to generate user ID")
                return
            }

            Log.d("FirebaseRepo", "Generated user ID: $userId")

            db.child(userId).setValue(user)
                .addOnSuccessListener {
                    Log.d("FirebaseRepo", "User data saved successfully")
                    onSuccess()
                }
                .addOnFailureListener { exception ->
                    Log.e("FirebaseRepo", "Failed to save user data", exception)
                    onFailure(exception.message ?: "Failed to save user data")
                }
        } catch (e: Exception) {
            Log.e("FirebaseRepo", "Exception in addUser", e)
            onFailure("Exception occurred: ${e.message}")
        }
    }

    fun getUserData(userId: String, onSuccess: (SignUpData) -> Unit, onFailure: (String) -> Unit) {
        try {
            Log.d("FirebaseRepo", "Attempting to get user data for ID: $userId")
            db.orderByChild("email").equalTo("\${userId}@hospitalapp.com").get()
                .addOnSuccessListener { snapshot ->
                    if (snapshot.exists()) {
                        val userData =
                            snapshot.children.firstOrNull()?.getValue(SignUpData::class.java)
                        if (userData != null) {
                            Log.d("FirebaseRepo", "User data found")
                            onSuccess(userData)
                        } else {
                            Log.d("FirebaseRepo", "User data not found")
                            onFailure("User data not found")
                        }
                    } else {
                        Log.d("FirebaseRepo", "No user data exists")
                        onFailure("No user data exists")
                    }
                }
                .addOnFailureListener { exception ->
                    Log.e("FirebaseRepo", "Failed to get user data", exception)
                    onFailure(exception.message ?: "Failed to get user data")
                }
        } catch (e: Exception) {
            Log.e("FirebaseRepo", "Exception in getUserData", e)
            onFailure("Exception occurred: \${e.message}")
        }
    }

}
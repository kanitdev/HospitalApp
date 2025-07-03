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

}
package com.example.e_commerce_iti.ui.theme.authentication

import androidx.activity.ComponentActivity
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

object FirebaseAuthManager {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    fun login(email: String, password: String, onResult: (Boolean, String?) -> Unit) {
        if (email.isNotBlank() && password.isNotBlank()) {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        onResult(true, null) // Success
                    } else {
                        onResult(false, task.exception?.message) // Error
                    }
                }
        }
    }

    fun firebaseAuthWithGoogle(
        credential: AuthCredential,activity: ComponentActivity,onResult: (Boolean, String?) -> Unit) {
        auth.signInWithCredential(credential).addOnCompleteListener(activity) { task ->
            if (task.isSuccessful) {
                onResult(true,null)// Sign-in success, navigate to main screen
            } else {
              onResult(false,task.exception?.message)  // Sign-in failed, show error
            }
        }
    }

    fun loginAnonymously(onResult: (Boolean, String?) -> Unit) {
        auth.signInAnonymously()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onResult(true, null) // Success
                } else {
                    onResult(false, task.exception?.message) // Error
                }
            }
    }

    fun signUp(email: String, password: String, onResult: (Boolean, String?) -> Unit) {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        onResult(true, null) // Success
                    } else {
                        onResult(false, task.exception?.message) // Error
                    }
                }

    }
}
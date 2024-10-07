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

    fun firebaseAuthWithGoogle(idToken: String, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onSuccess()
                } else {
                    onFailure(task.exception?.message ?: "Google Sign-In failed.")
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
                        auth.currentUser?.sendEmailVerification()?.addOnCompleteListener {
                            if(task.isSuccessful){
                                onResult(true,null)
                            }else{
                                onResult(false,task.exception?.message)
                            }
                        }

                    } else {
                        onResult(false, task.exception?.message) // Error
                    }
                }

    }
}
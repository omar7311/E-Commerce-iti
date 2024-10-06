package com.example.e_commerce_iti

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.e_commerce_iti.network.NetworkObserver
import com.example.e_commerce_iti.ui.theme._navigation.Navigation
import com.example.e_commerce_iti.ui.theme.authentication.FirebaseAuthManager
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class MainActivity : ComponentActivity() {
    private lateinit var networkObserver: NetworkObserver
    override fun onCreate(savedInstanceState: Bundle?) {
        networkObserver = NetworkObserver(applicationContext)  // Initialize NetworkObserver
        networkObserver.register()  // Register the network callback
        super.onCreate(savedInstanceState)
        // Initialize Firebase Auth



        // Build the One-Tap sign-in request

        setContent {
            /**
             *      here is the start of home and all screens in the below function
             */
            Navigation(networkObserver,this)
        }
    }
    // Start Google sign-in flow using One-Tap


    override fun onDestroy() {
        super.onDestroy()
        networkObserver.unRegister()  // Unregister the network callback
    }
}


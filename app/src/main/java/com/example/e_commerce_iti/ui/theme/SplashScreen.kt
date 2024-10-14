package com.example.e_commerce_iti.ui.theme

import androidx.compose.runtime.Composable

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.airbnb.lottie.compose.*
import com.example.e_commerce_iti.R
import com.example.e_commerce_iti.ui.theme._navigation.Screens
import com.example.e_commerce_iti.ui.theme.cart.MyLottiAni
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {
    LaunchedEffect(key1 = true) {
        delay(4000L)
        val destination =
            if (Firebase.auth.currentUser == null) Screens.Login.route
            else Screens.Home.route

        navController.navigate(destination) {
            popUpTo(Screens.Splash.route){ inclusive = true }
        }
    }
    Box (modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
        MyLottiAni(R.raw.ecommerce1)
    }
}

/**
 * if (Firebase.auth.currentUser == null) Screens.Login.route else Screens.Home.route
 */
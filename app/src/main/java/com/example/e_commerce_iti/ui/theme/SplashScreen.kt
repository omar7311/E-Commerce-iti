package com.example.e_commerce_iti.ui.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.runtime.Composable

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.airbnb.lottie.compose.*
import com.example.e_commerce_iti.R
import com.example.e_commerce_iti.gradientBrush
import com.example.e_commerce_iti.navyBlue
import com.example.e_commerce_iti.ui.theme._navigation.Screens
import com.example.e_commerce_iti.ui.theme.cart.MyLottiAni
import com.example.e_commerce_iti.ui.theme.home.CustomText
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
    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Splash Screen Image",
            modifier = Modifier.fillMaxWidth().height(400.dp)
                .align(Alignment.Center).padding(bottom = 128.dp)
        )
        MyLottiAni(R.raw.ecommerce1,Modifier.align(Alignment.BottomCenter))
    }
}
/**
 * if (Firebase.auth.currentUser == null) Screens.Login.route else Screens.Home.route
 */
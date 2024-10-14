package com.example.e_commerce_iti.ui.theme.cart

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.example.e_commerce_iti.NetworkErrorContent
import com.example.e_commerce_iti.currentUser
import com.example.e_commerce_iti.lightSeaGreen
import com.example.e_commerce_iti.network.NetworkObserver
import com.example.e_commerce_iti.ui.theme.guest.GuestScreen
import com.example.e_commerce_iti.ui.theme.home.CustomButtonBar
import com.example.e_commerce_iti.ui.theme.home.CustomTopBar
import com.example.e_commerce_iti.ui.theme.viewmodels.cartviewmodel.CartViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(
    cartViewModel: CartViewModel,
    controller: NavController,
    context: Context,
    netWorkObserver: NetworkObserver
) {
    Scaffold(
        containerColor = Color.White,
        topBar = { CustomTopBar("Cart", controller) },  // Update title to "Cart"
        bottomBar = {
            CustomButtonBar(
                controller,
                context = context
            )
        },     // Keep the navigation controller for buttons
    ) { innerPadding ->
        val isConnected = netWorkObserver.isConnected.collectAsState()
        if (isConnected.value) {

           if (Firebase.auth.currentUser != null && !Firebase.auth.currentUser!!.email.isNullOrBlank()) {
               val e= currentUser.observeAsState()
               if (e.value!=null) {
                 Carts(controller, Modifier.padding(innerPadding), viewModel = cartViewModel)
               }else{
                   Box(Modifier.fillMaxSize().padding(innerPadding), contentAlignment = Alignment.Center) {
                       CircularProgressIndicator(color = lightSeaGreen)
                   }
               }
           } else {
               GuestScreen(controller)
          }

        } else {
            NetworkErrorContent()
        }
    }
}


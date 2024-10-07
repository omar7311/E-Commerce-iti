package com.example.e_commerce_iti.ui.theme.cart

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.e_commerce_iti.ui.theme.home.CustomButtonBar
import com.example.e_commerce_iti.ui.theme.home.CustomTopBar
import com.example.e_commerce_iti.ui.theme.home.SimpleText
import com.example.e_commerce_iti.ui.theme.viewmodels.cartviewmodel.CartViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(cartViewModel: CartViewModel, controller: NavController,context: Context) {
    Scaffold(
        topBar = { CustomTopBar("Cart", controller) },  // Update title to "Cart"
        bottomBar = { CustomButtonBar(controller, context =context ) },     // Keep the navigation controller for buttons
    ) { innerPadding ->                                // Use padding for the content
        Carts(Modifier.padding(innerPadding), viewModel = cartViewModel)
    }
}


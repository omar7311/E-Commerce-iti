package com.example.e_commerce_iti.ui.theme.category

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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryScreen(controller: NavController) {
    Scaffold(
        topBar = { CustomTopBar("Category", controller) },  // Update title to "Cart"
        bottomBar = { CustomButtonBar(controller) },     // Keep the navigation controller for buttons
    ) { innerPadding ->                                // Use padding for the content
        Box(modifier = Modifier.padding(innerPadding)) {
            SimpleText("Category Screen Content")
        }
    }
}
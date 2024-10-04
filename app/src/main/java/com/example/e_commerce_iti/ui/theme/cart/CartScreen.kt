package com.example.e_commerce_iti.ui.theme.cart

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.e_commerce_iti.ui.theme.home.CustomButtonBar
import com.example.e_commerce_iti.ui.theme.home.CustomText
import com.example.e_commerce_iti.ui.theme.home.CustomTopBar
import com.example.e_commerce_iti.ui.theme.home.HomeContent
import com.example.e_commerce_iti.ui.theme.home.SimpleText
import com.example.e_commerce_iti.ui.theme.viewmodels.home_viewmodel.HomeViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(controller: NavController) {
    Scaffold(
        topBar = { CustomTopBar("Cart", controller) },  // Update title to "Cart"
        bottomBar = { CustomButtonBar(controller) },     // Keep the navigation controller for buttons
    ) { innerPadding ->                                // Use padding for the content
        Box(modifier = Modifier.padding(innerPadding)) {
            SimpleText("Cart Screen Content")
        }
    }
}


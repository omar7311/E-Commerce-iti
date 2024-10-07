package com.example.e_commerce_iti.ui.theme.product_details

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.e_commerce_iti.ui.theme.home.CustomButtonBar
import com.example.e_commerce_iti.ui.theme.home.CustomTopBar
import com.example.e_commerce_iti.ui.theme.home.SimpleText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetails(productId: Long, controller: NavController) {
    Scaffold(
        topBar = { CustomTopBar("Product Details", controller) },  // Update title to "Cart"
        bottomBar = { CustomButtonBar(controller) },     // Keep the navigation controller for buttons
    ) { innerPadding ->                                // Use padding for the content
        Column(modifier = Modifier.padding(innerPadding).verticalScroll(rememberScrollState())) {
            val images = listOf(
                "https://via.placeholder.com/600/92c952",
                "https://via.placeholder.com/600/771796",
                "https://via.placeholder.com/600/24f355"
            )
            val description="This is some very long scrollable text. " +
                    "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. " +
                    "Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. " +
                    "Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. " +
                    "Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum. " +
                    "More text goes here to make it longer. " +
                    "And even more content to demonstrate scrolling."
            ImageCarousel(images)
            ProductInfo("T-shirt","200","$",3)
            ProductDescription(description)
            Actions(true)
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun ProductDetailsPreview(){
    val controller= rememberNavController()
    ProductDetails(123456,controller)
}
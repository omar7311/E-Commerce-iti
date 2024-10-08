package com.example.e_commerce_iti.ui.theme.product_details

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.e_commerce_iti.model.apistates.UiState
import com.example.e_commerce_iti.model.pojos.Product
import com.example.e_commerce_iti.ui.theme.home.CustomButtonBar
import com.example.e_commerce_iti.ui.theme.home.CustomTopBar
import com.example.e_commerce_iti.ui.theme.home.SimpleText
import com.example.e_commerce_iti.ui.theme.viewmodels.cartviewmodel.CartViewModel
import com.example.e_commerce_iti.ui.theme.viewmodels.home_viewmodel.HomeViewModel
import com.google.firebase.auth.FirebaseAuth

@Composable
fun ProductDetails(product: Product, controller: NavController,cartViewModel: CartViewModel) {
fun ProductDetails(product: Product, controller: NavController,context: Context) {
    Scaffold(
        topBar = { CustomTopBar("Product Details", controller) },  // Update title to "Cart"
        bottomBar = { CustomButtonBar(controller,context) },     // Keep the navigation controller for buttons
    ) { innerPadding ->
    // Use padding for the content
        Column(modifier = Modifier.padding(innerPadding).verticalScroll(rememberScrollState())){
            val images = mutableListOf<String>()
            for (i in 0 until  product.images.size){
                images.add(product.images[i].src)
            }
            val description=product.body_html.replace("+"," ")
            ImageCarousel(images)
            ProductInfo(product.title,product.variants[0].price,"EG",3)
            ProductDescription(description)
            FirebaseAuth.getInstance().currentUser?.let { Actions(it.isAnonymous,controller,cartViewModel) }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun ProductDetailsPreview(){

}


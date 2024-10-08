package com.example.e_commerce_iti.ui.theme.favorite

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.e_commerce_iti.model.pojos.Product
import com.example.e_commerce_iti.ui.theme._navigation.Screens
import com.example.e_commerce_iti.ui.theme.home.CustomButtonBar
import com.example.e_commerce_iti.ui.theme.home.CustomImage
import com.example.e_commerce_iti.ui.theme.home.CustomText
import com.example.e_commerce_iti.ui.theme.home.CustomTopBar
import com.example.e_commerce_iti.ui.theme.home.SimpleText
import com.example.e_commerce_iti.ui.theme.products.ProductItem
import com.example.e_commerce_iti.ui.theme.viewmodels.cartviewmodel.CartViewModel


@Composable
fun FavoriteScreen(cartViewModel: CartViewModel, controller: NavController) {
    Scaffold(
        topBar = { CustomTopBar("Favorite", controller) },  // Update title to "Cart"
        bottomBar = { CustomButtonBar(controller) },     // Keep the navigation controller for buttons
    ) { innerPadding ->                                // Use padding for the content
        Box(modifier = Modifier.padding(innerPadding).fillMaxSize()) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxSize()
            ) {
//                itemsIndexed(favProducts) { _, product ->
//                     FavouriteItem(product)
//                }
            }
        }
    }
}

@Composable
fun FavouriteItem(src:String,title:String){
    Card(
        modifier = Modifier.padding(8.dp), // Padding around the card
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp), // Set elevation
        shape = RoundedCornerShape(10.dp), // Rounded corners
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(Color.White), // Background color of the card
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CustomImage(src)
            Spacer(modifier = Modifier.size(10.dp))
            CustomText(
                title,
                Color.White,
                textColor = Color.Black,
                fontSize = 16.sp
            ) // Title
            Image(imageVector = Icons.Filled.Delete, contentDescription = null,
                modifier = Modifier.clickable {

                })
        }
    }
}
@Preview
@Composable
fun FavouritePreview(){
    FavouriteItem("https://via.placeholder.com/600/92c952","title")
}
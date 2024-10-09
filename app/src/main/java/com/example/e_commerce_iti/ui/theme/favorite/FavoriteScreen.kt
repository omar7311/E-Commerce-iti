package com.example.e_commerce_iti.ui.theme.favorite

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key.Companion.I
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.e_commerce_iti.currentUser
import com.example.e_commerce_iti.model.apistates.UiState
import com.example.e_commerce_iti.model.pojos.Product
import com.example.e_commerce_iti.transparentBrush
import com.example.e_commerce_iti.ui.theme.ShimmerLoadingGrid
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

    LaunchedEffect(Unit) {
        currentUser?.fav?.let { cartViewModel.getCartDraftOrder(it) }
    }
    val product by cartViewModel.product.collectAsState()


    var productList=mutableListOf<Product>()
    Scaffold(
        topBar = { CustomTopBar("Favorite", controller) },  // Update title to "Cart"
        bottomBar = { CustomButtonBar(controller,LocalContext.current) },     // Keep the navigation controller for buttons
    ) { innerPadding ->                                // Use padding for the content
        Box(modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()) {

                when(product) {
                    is UiState.Loading -> {
                            ShimmerLoadingGrid()
                    }
                    is UiState.Success -> {
                        productList = (product as UiState.Success).data
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            modifier = Modifier
                                .fillMaxSize()
                        ) {
                            itemsIndexed(productList) { _, product ->
                                FavouriteItem(product)
                            }
                        }
                    }

                    is UiState.Error -> {}
                    is UiState.Failure -> {}
                    UiState.Non -> {}
                }

            }
        }
    }


@Preview(showSystemUi = true)
@Composable
fun FavouriteScreenPreview(){
    val controller= rememberNavController()
    //FavoriteScreen(controller)

}
@Composable
fun FavouriteItem(product: Product){
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Card(
            modifier = Modifier.padding(8.dp), // Padding around the card
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp), // Set elevation
            shape = RoundedCornerShape(10.dp), // Rounded corners
        ) {
            Column(
                modifier = Modifier
                    .wrapContentWidth()
                    .wrapContentHeight()
                    .background(Color.White), // Background color of the card
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CustomImage(product.images[0].src)
                Spacer(modifier = Modifier.size(10.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    CustomText(
                        product.title,
                        transparentBrush,
                        textColor = Color.Black,
                        fontSize = 16.sp
                    )
                    Icon(imageVector = Icons.Filled.Delete, tint = Color.Red ,contentDescription = null,
                        modifier = Modifier.clickable {

                        })
                }
            }
        }
    }
}
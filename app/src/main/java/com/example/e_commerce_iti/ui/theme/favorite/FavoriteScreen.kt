package com.example.e_commerce_iti.ui.theme.favorite

import android.annotation.SuppressLint
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.input.key.Key.Companion.I
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.e_commerce_iti.currentUser
import com.example.e_commerce_iti.model.apistates.UiState
import com.example.e_commerce_iti.model.pojos.Product
import com.example.e_commerce_iti.model.pojos.draftorder.DraftOrder
import com.example.e_commerce_iti.model.pojos.draftorder.LineItems
import com.example.e_commerce_iti.transparentBrush
import com.example.e_commerce_iti.ui.theme.ShimmerLoadingGrid
import com.example.e_commerce_iti.ui.theme._navigation.Screens
import com.example.e_commerce_iti.ui.theme.cart.MyAlertDialog
import com.example.e_commerce_iti.ui.theme.home.CustomButtonBar
import com.example.e_commerce_iti.ui.theme.home.CustomImage
import com.example.e_commerce_iti.ui.theme.home.CustomText
import com.example.e_commerce_iti.ui.theme.home.CustomTopBar
import com.example.e_commerce_iti.ui.theme.home.SimpleText
import com.example.e_commerce_iti.ui.theme.products.ProductItem
import com.example.e_commerce_iti.ui.theme.viewmodels.cartviewmodel.CartViewModel
import com.google.gson.Gson
import java.nio.file.WatchEvent


@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun FavoriteScreen(cartViewModel: CartViewModel, controller: NavController) {

    LaunchedEffect(Unit) {
        currentUser?.fav?.let { cartViewModel.getCartDraftOrder(it) }
    }
    val product by cartViewModel.product.collectAsState()



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
                       val productList = (product as UiState.Success).data
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            modifier = Modifier
                                .fillMaxSize()
                        ) {
                            itemsIndexed(productList) { index, product ->
                                FavouriteItem(controller,cartViewModel,product,index)
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
@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun FavouriteItem(controller: NavController,cartViewModel: CartViewModel,product: Product,index:Int){
    val draftOrder = (cartViewModel.cartState.value as? UiState.Success<DraftOrder>)?.data
    val showDialog = rememberSaveable { mutableStateOf(false) }
    if (showDialog.value) {
        MyAlertDialog(draftOrder?.line_items!!.get(index) ,{ lineItem:LineItems->
            cartViewModel.updateCart(product, lineItem)
        }, showDialog)
    }
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Card(
            modifier = Modifier.padding(8.dp).clickable {
                controller.navigate(Screens.ProductDetails.createDetailRoute(Gson().toJson(product)))
            }, // Padding around the card
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
                    CustomText(
                        product.title,
                        transparentBrush,
                        textColor = Color.Black,
                        fontSize = 16.sp
                    )
                Button(onClick = {
                   showDialog.value=true
                }, colors = ButtonDefaults.buttonColors(Color.Red)) {
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Delete" , color = Color.White, fontSize = 18.sp)
                    Spacer(modifier = Modifier.width(12.dp))
                    Icon(imageVector = Icons.Filled.Delete, tint = Color.White ,contentDescription = null)
                }
                }

        }
    }
}
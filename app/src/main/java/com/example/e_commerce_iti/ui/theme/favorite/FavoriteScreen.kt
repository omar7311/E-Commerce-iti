package com.example.e_commerce_iti.ui.theme.favorite

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.e_commerce_iti.NetworkErrorContent
import com.example.e_commerce_iti.R
import com.example.e_commerce_iti.currentUser
import com.example.e_commerce_iti.model.apistates.UiState
import com.example.e_commerce_iti.model.pojos.Product
import com.example.e_commerce_iti.model.pojos.draftorder.DraftOrder
import com.example.e_commerce_iti.network.NetworkObserver
import com.example.e_commerce_iti.transparentBrush
import com.example.e_commerce_iti.ui.theme.ShimmerLoadingGrid
import com.example.e_commerce_iti.ui.theme._navigation.Screens
import com.example.e_commerce_iti.ui.theme.cart.MyAlertDialog
import com.example.e_commerce_iti.ui.theme.cart.MyLottiAni
import com.example.e_commerce_iti.ui.theme.guest.GuestScreen
import com.example.e_commerce_iti.ui.theme.home.CustomButtonBar
import com.example.e_commerce_iti.ui.theme.home.CustomImage
import com.example.e_commerce_iti.ui.theme.home.CustomText
import com.example.e_commerce_iti.ui.theme.home.CustomTopBar
import com.example.e_commerce_iti.ui.theme.product_details.addToCardOrFavorite
import com.example.e_commerce_iti.ui.theme.viewmodels.cartviewmodel.CartViewModel
import com.example.e_commerce_iti.ui.theme.viewmodels.productInfo_viewModel.ProductInfoViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.gson.Gson


@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun FavoriteScreen(
    productInfoViewModel: ProductInfoViewModel,
    cartViewModel: CartViewModel,
    controller: NavController,
    context: Context,
    networkObserver: NetworkObserver
) {
    val product by cartViewModel.product.collectAsState()
    Scaffold(
        topBar = { CustomTopBar("Favorite", controller) },  // Update title to "Cart"
        bottomBar = {
            CustomButtonBar(
                controller,
                LocalContext.current
            )
        },     // Keep the navigation controller for buttons
    ) { innerPadding ->

        val isConnected = networkObserver.isConnected.collectAsState()
        if (isConnected.value) {
            if (Firebase.auth.currentUser != null && !Firebase.auth.currentUser!!.email.isNullOrBlank()) {

                if (currentUser.observeAsState().value!=null){



                LaunchedEffect(Unit) {
                    currentUser.value!!.fav.let { cartViewModel.getCartDraftOrder(it) }
                }
                // Use padding for the content
                Box(
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize()
                ) {
                    when (product) {
                        is UiState.Loading -> {
                            ShimmerLoadingGrid()
                        }

                        is UiState.Success -> {
                            val productList = (product as UiState.Success).data
                            if (productList.isNotEmpty()) {
                                LazyVerticalGrid(
                                    columns = GridCells.Fixed(2),
                                    modifier = Modifier
                                        .fillMaxSize()
                                ) {
                                    itemsIndexed(productList) { index, product ->
                                        FavouriteItem(
                                            context,
                                            productInfoViewModel,
                                            controller,
                                            cartViewModel,
                                            product,
                                            index+1
                                        )
                                    }
                                }
                            } else {
                                Column(modifier = Modifier.fillMaxWidth()) {
                                    MyLottiAni(R.raw.animation_no_data)
                                }
                            }
                        }

                        is UiState.Error -> {}
                        is UiState.Failure -> {}
                        UiState.Non -> {}
                    }
                }
            }else{
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                    CircularProgressIndicator()
                }
            }

        }else {
                GuestScreen(controller)
            }
        }
            else {
            NetworkErrorContent()
        }
    }
}


@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun FavouriteItem(
    context: Context,
    productInfoViewModel: ProductInfoViewModel,
    controller: NavController,
    cartViewModel: CartViewModel,
    product: Product,
    index: Int
) {
    val draftOrder = (cartViewModel.cartState.value as? UiState.Success<DraftOrder>)?.data
    var isAddingToCards by remember { mutableStateOf(false) }
    val draftOrderState by productInfoViewModel.draftOrderState.collectAsState()
    val showDialog = rememberSaveable { mutableStateOf(false) }
    if (showDialog.value) {
        MyAlertDialog(draftOrder?.line_items?.get(index)!!, { lineItem ->
            cartViewModel.updateCart(product, lineItem)
        }, showDialog)
    }
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Card(
            modifier = Modifier
                .padding(8.dp)
                .clickable {
                    controller.navigate(
                        Screens.ProductDetails.createDetailRoute(
                            Gson().toJson(
                                product
                            )
                        )
                    )
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
                Button(
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(Color(0xFF76c7c0)),
                    onClick = {
                        currentUser.value!!.cart.let {
                            isAddingToCards = true
                            productInfoViewModel.getDraftOrder(it)
                        } ?: run {
                            // Handle null cart case
                            println("User not logged in or cart is null")
                        }
                    }) {
                    Text(text = "Add to cart", color = Color.White, fontSize = 16.sp)
                    Spacer(Modifier.width(8.dp))
                    Icon(
                        imageVector = Icons.Filled.ShoppingCart,
                        contentDescription = null
                    )
                }
                Button(onClick = {
                    showDialog.value = true
                }, colors = ButtonDefaults.buttonColors(Color.Red)) {
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Delete", color = Color.White, fontSize = 18.sp)
                    Spacer(modifier = Modifier.width(12.dp))
                    Icon(
                        imageVector = Icons.Filled.Delete,
                        tint = Color.White,
                        contentDescription = null
                    )
                }


            }

        }
    }

    LaunchedEffect(draftOrderState) {
        if (isAddingToCards) {
            when (draftOrderState) {
                is UiState.Success -> {
                    val draftOrder = (draftOrderState as UiState.Success).data
                    // Check if product is already in cart or add new product to cart
                    if (!draftOrder.line_items.any { it.product_id == product.id }) {
                        addToCardOrFavorite(productInfoViewModel, product, draftOrder)
                        isAddingToCards = false
                       Toast.makeText(context,"the product ia adding successfully",Toast.LENGTH_LONG).show()
                    }
                }

                is UiState.Error -> {
                    // Handle error, show message
                    println("Error fetching draft order")
                    isAddingToCards = false
                }

                else -> {}
            }
        }
    }

}

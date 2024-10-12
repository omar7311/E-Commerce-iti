package com.example.e_commerce_iti.ui.theme.cart

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.saveable.rememberSaveableStateHolder
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.e_commerce_iti.CurrentUser
import com.example.e_commerce_iti.CurrentUserSaver
import com.example.e_commerce_iti.R
import com.example.e_commerce_iti.currentUser
import com.example.e_commerce_iti.getCurrent
import com.example.e_commerce_iti.gradientBrush
import com.example.e_commerce_iti.ingredientColor1
import com.example.e_commerce_iti.model.apistates.UiState
import com.example.e_commerce_iti.model.local.LocalDataSourceImp
import com.example.e_commerce_iti.model.pojos.Product
import com.example.e_commerce_iti.model.pojos.Variant
import com.example.e_commerce_iti.model.pojos.draftorder.DraftOrder
import com.example.e_commerce_iti.model.remote.RemoteDataSourceImp
import com.example.e_commerce_iti.model.reposiatory.ReposiatoryImpl
import com.example.e_commerce_iti.ui.theme.ECommerceITITheme
import com.example.e_commerce_iti.ui.theme._navigation.Screens
import com.example.e_commerce_iti.ui.theme.viewmodels.cartviewmodel.CartViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

@SuppressLint("StateFlowValueCalledInComposition")

@Composable
fun Carts(navController: NavController,modifier: Modifier = Modifier, viewModel: CartViewModel) {
    LaunchedEffect(Unit) {
        Log.i("CartScreen", "LaunchedEffect called ${currentUser.value!!.cart}")
        viewModel.getCartDraftOrder(currentUser.value!!.cart)
        viewModel.getCurrency()
    }
    if (viewModel.navigateto.collectAsState().value) {
       navController.navigate(Screens.Payment.route)
        viewModel.endnav()
    }

    Log.i("CartScreen", "Screen Rebuild")

    val currency = viewModel.currentCurrency.collectAsState()
    val productState = viewModel.product.collectAsState()
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(bottom = 10.dp)
    ) {
        when (val state = productState.value) {
            is UiState.Success -> {
                val products = state.data // Products fetched successfully
                val draftOrder = (viewModel.cartState.value as? UiState.Success<DraftOrder>)?.data
                val currencyData = (currency.value as? UiState.Success<Pair<String, Float>>)?.data

                if (products.isNotEmpty()) {
                    LazyColumn(
                        modifier = Modifier.weight(25f),
                        contentPadding = PaddingValues(14.dp)
                    ) {
                        items(products.size, key = { index -> products[index].id }) { index ->
                            val product = products[index]
                            val variant = draftOrder?.line_items
                                ?.flatMap { lineItem -> product.variants.filter { it.id == lineItem.variant_id } }
                                ?.firstOrNull()
                            variant?.let {
                                CartItem(
                                    draftOrder.line_items[index + 1],
                                    currencyData!!,
                                    image = product.images[0].src,
                                    name = product.title,
                                    price = it.price.toDouble(),
                                    numberOfItemsChosen = remember { mutableStateOf(draftOrder.line_items[index + 1].quantity!!.toInt()) },
                                    quantity = it.inventory_quantity,
                                    totalAmount = viewModel,
                                    e = { e -> viewModel.updateCart(product, e) }
                                )
                                Spacer(Modifier.height(10.dp))
                            }
                        }
                    }

                    Spacer(Modifier.height(10.dp))
                    TotalPriceText(viewModel, currencyData!!)
                    Spacer(Modifier.height(10.dp))
                    CheckoutButton(viewModel)
                } else {
                    // Handle empty product list
                    Column(modifier = Modifier.fillMaxWidth()) {
                        MyLottiAni(R.raw.animation_no_data)
                    }
                }
            }
            is UiState.Loading -> {
                // Show loading animation
                Column(modifier = Modifier.fillMaxWidth()) {
                    MyLottiAni(R.raw.loadingcart)
                }
            }
            is UiState.Error -> {
                // Handle error state, perhaps show a message
                Text("Error loading products", modifier = Modifier.padding(16.dp))
            }

            UiState.Non -> TODO()
            is UiState.Failure -> TODO()
        }
    }
}

@Composable
fun MyLottiAni(id: Int) {
    // Load the Lottie animation from the assets folder
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(id))

    // Lottie animation control
    LottieAnimation(
        modifier = Modifier.fillMaxSize(),
        composition = composition,
        iterations = LottieConstants.IterateForever // This will loop the animation
    )
}

@Composable
fun TotalPriceText(model: CartViewModel, currency2: Pair<String, Float>) {
    // Collect the state from totalAmount and observe it
    val totalAmount = model.totalAmount.collectAsState(initial = 0.0).value

    Text(
        fontSize = 19.sp,
        color = ingredientColor1,
        fontWeight = FontWeight.Bold,
        text = model.gettotalValue(currency2.second, currency2.first),
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 60.dp)
    )
}


@Composable
fun CheckoutButton(viewModel: CartViewModel) {
    Button(
        onClick = {

            viewModel.submit()

                  },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 50.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF76c7c0), // Replace with your desired color
            contentColor = Color.White // Text color
        )
    ) {
        Text(
            fontSize = 20.sp,
            text = "Checkout",
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.bodyMedium // Optional: apply a text style
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CartsPreview() {
    ECommerceITITheme {
        val context = LocalContext.current
        val viewModel = CartViewModel(
            ReposiatoryImpl(
                RemoteDataSourceImp(),
                LocalDataSourceImp(context.getSharedPreferences(currentUser.value?.email ?:"null", Context.MODE_PRIVATE))
            )
        )
        currentUser.value=CurrentUser(id = 7494620905649, cart = 1003013144753L)
        Carts(viewModel = viewModel,navController = NavController(context))
    }
}

fun Double.roundToTwoDecimalPlaces(): Double {
    return String.format("%.2f", this).toDouble()
}

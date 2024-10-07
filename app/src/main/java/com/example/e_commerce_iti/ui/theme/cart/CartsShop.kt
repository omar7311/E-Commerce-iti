package com.example.e_commerce_iti.ui.theme.cart

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.e_commerce_iti.R
import com.example.e_commerce_iti.currentUser
import com.example.e_commerce_iti.getCurrent
import com.example.e_commerce_iti.model.apistates.UiState
import com.example.e_commerce_iti.model.local.LocalDataSourceImp
import com.example.e_commerce_iti.model.local.LocalDataSourceImp.Companion.currentCurrency
import com.example.e_commerce_iti.model.pojos.Product
import com.example.e_commerce_iti.model.pojos.Variant
import com.example.e_commerce_iti.model.pojos.draftorder.DraftOrder
import com.example.e_commerce_iti.model.remote.RemoteDataSourceImp
import com.example.e_commerce_iti.model.reposiatory.ReposiatoryImpl
import com.example.e_commerce_iti.ui.theme.ECommerceITITheme
import com.example.e_commerce_iti.ui.theme.viewmodels.cartviewmodel.CartViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun Carts(modifier: Modifier = Modifier, viewModel: CartViewModel) {
    if (currentUser!=null) {
        viewModel.getCartDraftOrder(currentUser!!.cart)
    }
    viewModel.getCurrency()
    Log.i("CartScreen", "Screen Rebuild")
    val currency = viewModel.currentCurrency.collectAsState()
    // Observing the product and draft order states
    val productState = viewModel.product.collectAsState()

    // Store total amount in a separate state that won't trigger LazyColumn recomposition
    val totalAmount = remember { mutableStateOf(0.0) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(bottom = 50.dp)
    ) {
        if (productState.value is UiState.Success){
            val products = (productState.value as? UiState.Success<MutableList<Product>>)?.data
            val draftOrder = (viewModel.cartState.value as? UiState.Success<DraftOrder>)?.data
            val currency2 = (currency.value as? UiState.Success<Pair<String, Float>>)!!.data

            if (products!!.isNotEmpty()) {
                LazyColumn(
                    modifier = Modifier.weight(25f),
                    contentPadding = PaddingValues(14.dp)
                ) {

                         products?.let { productList ->
                        draftOrder?.let { order ->
                            items(productList.size, key = { productList[it].bodyHtml }) { index ->
                                val product = productList[index]
                                val variant = order.line_items
                                    .flatMap { lineItem -> product.variants.filter { it.id == lineItem.variant_id } }
                                    .firstOrNull()

                                variant?.let {
                                    CartItem(
                                        currency2,
                                        image = product.images[0].src,
                                        name = product.title,
                                        price = order.line_items[index].price!!.toDouble(),
                                        quantity = it.inventory_quantity, totalAmount = totalAmount
                                    )
                                    Spacer(Modifier.height(10.dp))
                                }
                            }
                        }
                    }
                }

                // The total price will update without affecting the LazyColumn
                Spacer(Modifier.height(10.dp))
                TotalPriceText(totalAmount = totalAmount,currency2)
                Spacer(Modifier.height(10.dp))
                CheckoutButton()
            }else{
                Column(modifier = Modifier.fillMaxWidth()){
                MyLottiAni(R.raw.emptycart)
            }
            }
        }else if (productState.value is UiState.Loading){
            Column(modifier = Modifier.fillMaxWidth()){
            MyLottiAni(R.raw.loadingcart)
        }
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
fun TotalPriceText(totalAmount: MutableState<Double>,currency2: Pair<String, Float>) {
    // Only this text recomposes when the total amount changes
    Text(
        text = "Price ${totalAmount.value.roundToTwoDecimalPlaces() * currency2.second} in ${currency2.first}",
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 60.dp)
    )
}

@Composable
fun CheckoutButton() {
    Button(
        onClick = { /* Checkout Action */ },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 50.dp)
    ) {
        Text(text = "Checkout")
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
                LocalDataSourceImp(context.getSharedPreferences(currentCurrency, Context.MODE_PRIVATE))
            )
        )
        Carts(viewModel = viewModel)
    }
}

fun Double.roundToTwoDecimalPlaces(): Double {
    return String.format("%.2f", this).toDouble()
}

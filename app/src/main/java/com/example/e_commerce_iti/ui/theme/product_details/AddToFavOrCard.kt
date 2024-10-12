package com.example.e_commerce_iti.ui.theme.product_details

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.e_commerce_iti.currentUser
import com.example.e_commerce_iti.model.apistates.UiState
import com.example.e_commerce_iti.model.pojos.Product
import com.example.e_commerce_iti.model.pojos.draftorder.DraftOrder
import com.example.e_commerce_iti.model.pojos.draftorder.LineItems
import com.example.e_commerce_iti.ui.theme.viewmodels.productInfo_viewModel.ProductInfoViewModel


@Composable
fun Actions(
    context: Context,
    product: Product,
    productInfoViewModel: ProductInfoViewModel,
) {
    val draftOrderState by productInfoViewModel.draftOrderState.collectAsState()

    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
    ) {
        Button(
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(Color(0xFF76c7c0), Color.White),
            onClick = {
                currentUser.value?.fav?.let {
                    productInfoViewModel.getDraftOrder(it)
                } ?: run {
                    // Handle null cart case
                    println("User not logged in or cart is null")
                }
            }) {
            Text(text = "Add to favorite", color = Color.White, fontSize = 16.sp)
            Spacer(Modifier.width(8.dp))
            Icon(
                imageVector = Icons.Filled.Favorite,
                contentDescription = null
            )
        }

        Button(
            shape = RoundedCornerShape(16.dp),
            colors =ButtonDefaults.buttonColors(Color(0xFF76c7c0), Color.White),
            onClick = {
                currentUser.value?.cart?.let {
                    productInfoViewModel.getDraftOrder(it)
                } ?: run {
                    // Handle null cart case
                    println("User not logged in or cart is null")
                }
            }) {
            Text(text = "Add to cart", color = Color.White ,fontSize = 16.sp)
            Spacer(Modifier.width(8.dp))
            Icon(
                imageVector = Icons.Filled.ShoppingCart,
                contentDescription = null
            )
        }
    }

    // Observe changes in draft order state
    LaunchedEffect(draftOrderState) {
        when (draftOrderState) {
            is UiState.Success -> {
                val draftOrder = (draftOrderState as UiState.Success).data
                // Check if product is already in cart or add new product to cart
                if (!draftOrder.line_items.any { it.product_id == product.id }) {
                    addToCardOrFavorite(productInfoViewModel, product, draftOrder)
                        Toast.makeText(
                            context,
                            "the product is adding successfully",
                            Toast.LENGTH_LONG
                        )
                            .show()
                }
            }
            is UiState.Error -> {
                // Handle error, show message
                println("Error fetching draft order")
            }
            else -> {}
        }
    }
}





fun addToCardOrFavorite(
    productInfoViewModel: ProductInfoViewModel,
    product: Product,
    draftOrder: DraftOrder
) {
    val lineItem = LineItems(
        title = product.title,
        price = product.variants[0].price,
        quantity = 1,
        variant_id = product.variants[0].id,
        product_id = product.id
    )

    val updatedLineItems = ArrayList<LineItems>(draftOrder.line_items).apply {
        add(lineItem)
    }

    draftOrder.line_items = updatedLineItems
    productInfoViewModel.updateDraftOrder(draftOrder)
}



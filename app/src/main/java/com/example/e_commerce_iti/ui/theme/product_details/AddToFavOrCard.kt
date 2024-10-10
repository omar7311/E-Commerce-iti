package com.example.e_commerce_iti.ui.theme.product_details

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Snackbar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.DefaultFillType
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.e_commerce_iti.currentUser
import com.example.e_commerce_iti.model.apistates.UiState
import com.example.e_commerce_iti.model.pojos.Product
import com.example.e_commerce_iti.model.pojos.draftorder.DraftOrder
import com.example.e_commerce_iti.model.pojos.draftorder.LineItems
import com.example.e_commerce_iti.ui.theme.viewmodels.productInfo_viewModel.ProductInfoViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch


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
            colors = ButtonDefaults.buttonColors(Color(0xFFCCC2DC)),
            onClick = {
                currentUser?.fav?.let {
                    productInfoViewModel.getDraftOrder(it)
                } ?: run {
                    // Handle null cart case
                    println("User not logged in or cart is null")
                }
            }) {
            Text(text = "Add to favorite", color = Color.Black, fontSize = 16.sp)
            Spacer(Modifier.width(8.dp))
            Icon(
                imageVector = Icons.Filled.Favorite,
                contentDescription = null
            )
        }

        Button(
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(Color(0xFFCCC2DC)),
            onClick = {
                currentUser?.cart?.let {
                    productInfoViewModel.getDraftOrder(it)
                } ?: run {
                    // Handle null cart case
                    println("User not logged in or cart is null")
                }
            }) {
            Text(text = "Add to cart", color = Color.Black, fontSize = 16.sp)
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
                if (product.variants[0].inventory_quantity!=0 && !draftOrder.line_items.any { it.product_id == product.id }) {
                    addToCardOFavorite(productInfoViewModel, product, draftOrder)
                    Toast.makeText(context,"the product is adding successfully",Toast.LENGTH_LONG).show()
                } else{
                    Toast.makeText(context,"the product is not available",Toast.LENGTH_LONG).show()

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





fun addToCardOFavorite(
    productInfoViewModel: ProductInfoViewModel,
    product: Product,
    draftOrder: DraftOrder
) {
    val lineItem = LineItems(
        title = product.title,
        price = product.variants[0].price,
        quantity = product.variants[0].inventory_quantity.toLong(),
        variant_id = product.variants[0].id,
        product_id = product.id
    )

    val updatedLineItems = ArrayList<LineItems>(draftOrder.line_items).apply {
        add(lineItem)
    }

    draftOrder.line_items = updatedLineItems
    productInfoViewModel.updateDraftOrder(draftOrder)
}



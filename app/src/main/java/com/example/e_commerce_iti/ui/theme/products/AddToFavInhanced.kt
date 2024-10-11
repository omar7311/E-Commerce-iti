package com.example.e_commerce_iti.ui.theme.products

import androidx.compose.runtime.Composable
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.e_commerce_iti.currentUser
import com.example.e_commerce_iti.ingredientColor1
import com.example.e_commerce_iti.model.apistates.UiState
import com.example.e_commerce_iti.model.pojos.Product
import com.example.e_commerce_iti.model.pojos.draftorder.DraftOrder
import com.example.e_commerce_iti.ui.theme.product_details.addToCardOrFavorite
import com.example.e_commerce_iti.ui.theme.viewmodels.cartviewmodel.CartViewModel
import com.example.e_commerce_iti.ui.theme.viewmodels.productInfo_viewModel.ProductInfoViewModel

@Composable
fun FavoriteButton(
    product: Product,
    productInfoViewModel: ProductInfoViewModel,
    cartViewModel: CartViewModel,
    context: Context
) {
    var isInFavorites by remember { mutableStateOf(false) }
    var isAddingToFavorites by remember { mutableStateOf(false) }
    val showRemoveDialog = rememberSaveable { mutableStateOf(false) }

    val draftOrderState by productInfoViewModel.draftOrderState.collectAsState()

    LaunchedEffect(draftOrderState) {
        if (draftOrderState is UiState.Success) {
            val draftOrder = (draftOrderState as UiState.Success<DraftOrder>).data
            isInFavorites = draftOrder.line_items.any { it.product_id == product.id }
        }
    }

    IconButton(
        modifier = Modifier.padding(bottom = 3.dp),
        onClick = {
            if (isInFavorites) {
                showRemoveDialog.value = true
            } else {
                currentUser?.fav?.let { favId ->
                    isAddingToFavorites = true
                    productInfoViewModel.getDraftOrder(favId)
                } ?: run {
                    Toast.makeText(context, "User not logged in or no favorite list available", Toast.LENGTH_LONG).show()
                }
            }
        }
    ) {
        Icon(
            imageVector = if (isInFavorites) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
            contentDescription = if (isInFavorites) "Remove from Favorites" else "Add to Favorites",
            tint = if (isInFavorites) Color.Red else ingredientColor1,
            modifier = Modifier.size(60.dp)
        )
    }

    LaunchedEffect(draftOrderState) {
        if (isAddingToFavorites) {
            when (draftOrderState) {
                is UiState.Success -> {
                    val draftOrder = (draftOrderState as UiState.Success<DraftOrder>).data
                    if (!draftOrder.line_items.any { it.product_id == product.id }) {
                        addToCardOrFavorite(productInfoViewModel, product, draftOrder)
                        Toast.makeText(context, "The product is added to favorites", Toast.LENGTH_LONG).show()
                        isInFavorites = true
                    } else {
                        Toast.makeText(context, "The product is already in favorites", Toast.LENGTH_LONG).show()
                    }
                    isAddingToFavorites = false
                }
                is UiState.Error -> {
                    println("Error fetching draft order")
                    isAddingToFavorites = false
                }
                else -> {}
            }
        }
    }

}

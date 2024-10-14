package com.example.e_commerce_iti.ui.theme.products

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
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

@SuppressLint("CommitPrefEdits")
@Composable
fun FavoriteButton(
    product: Product,
    productInfoViewModel: ProductInfoViewModel,
    context: Context
) {

    var isInFavorites by remember { mutableStateOf(false) }
    var isAddingToFavorites by remember { mutableStateOf(false) }

    val draftOrderState by productInfoViewModel.draftOrderState.collectAsState()

    LaunchedEffect(draftOrderState) {
        if (draftOrderState is UiState.Success) {
            val draftOrder = (draftOrderState as UiState.Success<DraftOrder>).data
            isInFavorites = draftOrder.line_items.any { it.product_id == product.id }
        }
    }

    IconButton( colors = IconButtonDefaults.iconButtonColors(ingredientColor1, Color.White),
        modifier = Modifier.size(35.dp),
        onClick = {
                currentUser.value?.fav.let { favId ->
                    isAddingToFavorites = true
                    favId?.let { productInfoViewModel.getDraftOrder(it) }
                }

        }
    ) {
        Icon(
            imageVector = Icons.Filled.Favorite,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.size(25.dp)
        )
    }

    LaunchedEffect(draftOrderState) {
        if (isAddingToFavorites) {
            when (draftOrderState) {
                is UiState.Success -> {
                    val draftOrder = (draftOrderState as UiState.Success<DraftOrder>).data
                    if (!draftOrder.line_items.any { it.product_id == product.id }) {
                        addToCardOrFavorite(productInfoViewModel, product, draftOrder,
                            mutableStateOf(0)
                        )
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

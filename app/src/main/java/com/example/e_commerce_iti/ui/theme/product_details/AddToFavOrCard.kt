package com.example.e_commerce_iti.ui.theme.product_details

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.example.e_commerce_iti.model.pojos.draftorder.LineItems
import com.example.e_commerce_iti.ui.theme.viewmodels.productInfo_viewModel.ProductInfoViewModel


@Composable
fun Actions(product: Product,productInfoViewModel: ProductInfoViewModel,navController: NavController){
    val draftOrderState by productInfoViewModel.draftOrderState.collectAsState()
    when(draftOrderState){
        is UiState.Loading->{}
        is UiState.Success->{
            var draftOrder=(draftOrderState as UiState.Success).data
            var flag=false
            for (i in draftOrder.line_items){
                if(i.product_id!=null && product.id==i.product_id){
                    flag=true
                }
            }
            if(flag==false) {
                val lineItem = LineItems()
                lineItem.title = product.title
                lineItem.price = product.variants[0].price
                lineItem.quantity = product.variants[0].inventory_quantity.toLong()
                lineItem.variant_id = product.variants[0].id
                lineItem.product_id = product.id
                val arrayLineItems = ArrayList<LineItems>(draftOrder.line_items)
                arrayLineItems.add(lineItem)
                draftOrder.line_items = arrayLineItems.toList()
                productInfoViewModel.updateDraftOrder(draftOrder)
            }
            }
        is UiState.Failure->{ }
        is UiState.Error -> {}
        UiState.Non -> {}
    }
    Row(horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth().height(50.dp)) {
        Button( shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(Color(0xFFCCC2DC)),
            onClick = {
                //currentUser?.fav?.let { productInfoViewModel.getDraftOrder(it) }
            }) {
            Text(text = "add to favourite",color = Color.Black,fontSize = 16.sp)
            Spacer(Modifier.width(8.dp))
            Icon(imageVector = Icons.Filled.Favorite
                ,contentDescription = null)
        }
        Button( shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(Color(0xFFCCC2DC)) ,
            onClick = {
                // currentUser?.cart?.let { productInfoViewModel.getDraftOrder(it) }
            }) {
            Text(text = "add to card", color = Color.Black, fontSize = 16.sp)
            Spacer(Modifier.width(8.dp))
            Icon(imageVector = Icons.Filled.ShoppingCart
                ,contentDescription = null)
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun ActionsPreview(){
    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
       // Actions(true)
        Row(horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth().height(50.dp)) {
            Button( shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(Color(0xFFCCC2DC)),
                onClick = {
                //currentUser?.fav?.let { productInfoViewModel.getDraftOrder(it) }
            }) {
                Text(text = "add to favourite",color = Color.Black,fontSize = 16.sp)
                Spacer(Modifier.width(8.dp))
                Icon(imageVector = Icons.Filled.Favorite
                    ,contentDescription = null)
            }
            Button( shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(Color(0xFFCCC2DC)) ,
                onClick = {
               // currentUser?.cart?.let { productInfoViewModel.getDraftOrder(it) }
            }) {
                Text(text = "add to card", color = Color.Black, fontSize = 16.sp)
                Spacer(Modifier.width(8.dp))
                Icon(imageVector = Icons.Filled.ShoppingCart
                    ,contentDescription = null)
            }
        }
    }
}
package com.example.e_commerce_iti

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.e_commerce_iti.ui.theme.ECommerceITITheme

data class CartItem(val product: String, val quantity: Int = 4, val price: Double = 0.0)
@Composable
fun Carts(){
    val totalAmount = remember { mutableStateOf(0.0) }
    val i = remember { mutableStateListOf(CartItem("item1", quantity = 3, price = 10.0), CartItem("item2", price = 20.0), CartItem("item3", quantity = 5, price = 15.0)) }
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(bottom = 50.dp)){
    LazyColumn(
        modifier = Modifier.weight(25F), contentPadding = PaddingValues(14.dp)
    ) {
        items(i.size, key = { i[it].product }) {
            CartItem({ price->
                i.removeAt(it)
                totalAmount.value-=price
            } ,i[it].product, i[it].price, i[it].quantity,totalAmount)
            Spacer(Modifier.height(10.dp))
        }
      }
//        Spacer(Modifier.height(10.dp))
//        Row { Button(onClick = {}) { } }
        Spacer(Modifier.height(10.dp))
        Text("price ${totalAmount.value.roundToTwoDecimalPlaces()}  in usd", modifier = Modifier
            .weight(1F)
            .padding(start = 60.dp))
        Spacer(Modifier.height(10.dp))

        Button(onClick = {}, modifier = Modifier
            .fillMaxWidth()
            .padding(start = 50.dp, end = 50.dp)) {
            Text(text = "checkout")
        }
    }
}

@Composable
fun BottomItems(MutableState: MutableState<Int>) {

}
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CartsPreview() {
    ECommerceITITheme {
        Carts()
    }
}
fun Double.roundToTwoDecimalPlaces(): Double {
    return String.format("%.2f", this).toDouble()
}
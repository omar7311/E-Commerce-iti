package com.example.e_commerce_iti.ui.theme.cart

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.e_commerce_iti.R
import com.example.e_commerce_iti.ui.theme.ECommerceITITheme
import com.example.e_commerce_iti.ui.theme.viewmodels.cartviewmodel.CartViewModel
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun CartItem(
    lineItems: com.example.e_commerce_iti.model.pojos.draftorder.LineItems
    ,
    currency: Pair<String, Float>
      ,
    numberOfItemsChosen:MutableState<Int>
    ,
    e:(Linedata:com.example.e_commerce_iti.model.pojos.draftorder.LineItems)->Unit
    ,
    image: String = "",
    name: String = "",
    price: Double = 0.0,
    quantity: Int = 0,
    totalAmount: CartViewModel
) {
    Log.i("CartItem", "$image $name $price $quantity")
    val showDialog = rememberSaveable { mutableStateOf(false) }
    // Show confirmation dialog for item deletion
    if (showDialog.value) {
        MyAlertDialog(order = lineItems,e, showDialog)
    }

    // Main Card for displaying the product item
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .border(3.dp, MaterialTheme.colorScheme.primary)
            .height(100.dp)
            .padding(top = 10.dp, bottom = 10.dp)
    ) {
        Row {
            // Image display for the product
            GlideImage(
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .weight(1f)
                    .height(100.dp)
                    .clickable { /* Handle image click */ },
                imageModel =image
            )
            Spacer(Modifier.width(10.dp))

            // Product name and price column
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(2f)
            ) {
                Spacer(Modifier.height(10.dp))
                Text(
                    text = "$name!",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(Modifier.height(20.dp))
                Text(
                    text = "${(price* currency.second).roundToTwoDecimalPlaces()} ${currency.first}",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }
            Spacer(Modifier.width(5.dp))

            // Quantity controls (Add/Remove)
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f),
                verticalArrangement = Arrangement.Bottom
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    // Remove button
                    IconButton(
                        modifier = Modifier.weight(1f),
                        onClick = {
                            if (numberOfItemsChosen.value  > 1) {
                                totalAmount.sub(price)
                                numberOfItemsChosen.value--
                                lineItems.quantity = numberOfItemsChosen.value.toLong()
                            }
                        }
                    ) {
                        Icon(
                            modifier = Modifier.padding(bottom = 10.dp),
                            painter = painterResource(id = R.drawable.baseline_minimize_24),
                            contentDescription = null
                        )
                    }
                    // Display chosen item count
                    Text(textAlign = TextAlign.Center, text = "${numberOfItemsChosen.value}", modifier = Modifier.weight(1f))


                    // Add button
                    IconButton(
                        modifier = Modifier.weight(1f),
                        onClick = {
                            if (numberOfItemsChosen.value < quantity) {
                                numberOfItemsChosen.value++
                                Log.i("CartItem", "add ${numberOfItemsChosen.value}")
                                totalAmount.add(price)
                                lineItems.quantity = numberOfItemsChosen.value.toLong()
                            }
                        }
                    ) {
                        Icon(imageVector = Icons.Filled.Add, contentDescription = null)
                    }
                }
            }

            // Delete icon
            Column(
                modifier = Modifier.fillMaxHeight()
            ) {
                IconButton(onClick = { showDialog.value = true }) {
                    Icon(imageVector = Icons.Filled.Delete, contentDescription = null)
                }
            }
        }
    }
}

@Composable
fun MyAlertDialog(order: com.example.e_commerce_iti.model.pojos.draftorder.LineItems,e:(Linedata:com.example.e_commerce_iti.model.pojos.draftorder.LineItems)->Unit,shouldShowDialog: MutableState<Boolean>) {
    if (shouldShowDialog.value) { // 2
        AlertDialog( // 3
            onDismissRequest = { // 4
                shouldShowDialog.value = false
            },
            // 5
            title = { Text(text = "Alert Dialog") },
            text = { Text(text = "Jetpack Compose Alert Dialog") },
            confirmButton = { // 6
                Button(
                    onClick = {
                        shouldShowDialog.value = false
                        e(order)
                    }
                ) {
                    Text(
                        text = "Confirm",
                        color = Color.Cyan
                    )
                }
            }
        )
    }
}
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    ECommerceITITheme {
        //CartItem(Pair("USD",1.0F),"Android","Android",10.0,10,totalAmount = rememberSaveable { mutableStateOf(0.0) })
    }
}
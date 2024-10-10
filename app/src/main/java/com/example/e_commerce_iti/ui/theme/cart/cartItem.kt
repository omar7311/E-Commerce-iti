package com.example.e_commerce_iti.ui.theme.cart

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.e_commerce_iti.R
import com.example.e_commerce_iti.ingredientColor1
import com.example.e_commerce_iti.khaki
import com.example.e_commerce_iti.lightSeaGreen
import com.example.e_commerce_iti.limeGreen
import com.example.e_commerce_iti.mediumSeaGreen
import com.example.e_commerce_iti.ui.theme.ECommerceITITheme
import com.example.e_commerce_iti.ui.theme.viewmodels.cartviewmodel.CartViewModel
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun CartItem(
    lineItems: com.example.e_commerce_iti.model.pojos.draftorder.LineItems,
    currency: Pair<String, Float>,
    numberOfItemsChosen: MutableState<Int>,
    e: (Linedata: com.example.e_commerce_iti.model.pojos.draftorder.LineItems) -> Unit,
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
        MyAlertDialog(order = lineItems, e, showDialog)
    }

    // Main Card for displaying the product item
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp), // Added padding for better spacing
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        shape = RoundedCornerShape(12.dp) // Rounded corners for a modern look
    ) {
        Row(
            modifier = Modifier.padding(16.dp), // Add padding inside the card
            verticalAlignment = Alignment.CenterVertically // Center content vertically
        ) {
            // Image display for the product in a circular shape
            GlideImage(
                contentScale = ContentScale.Crop, // Fill the circular image
                modifier = Modifier
                    .size(110.dp) // Set a fixed size for the circular image
                    .clip(RoundedCornerShape(20.dp)) // Clip the image into a circular shape
                    .clickable { /* Handle image click */ },
                imageModel = image
            )

            Spacer(Modifier.width(16.dp))

            // Product name and price column
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(2f),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "$name!",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Start,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Spacer(Modifier.height(14.dp))
                Text(
                    text = "${(price * currency.second).roundToTwoDecimalPlaces()} ${currency.first}",
                    modifier = Modifier
                        .wrapContentWidth(),
                    textAlign = TextAlign.Start,
                    color = mediumSeaGreen,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }

            Spacer(Modifier.width(10.dp))

            // Quantity controls (Add/Remove)
            Column(
                modifier = Modifier
                    .wrapContentWidth()
                    .fillMaxHeight()
                    .weight(1f),
                verticalArrangement = Arrangement.Bottom
            ) {

                // Delete icon
                IconButton(
                    modifier = Modifier.align(Alignment.End),
                    onClick = { showDialog.value = true }) {
                    Icon(
                        imageVector = Icons.Filled.Delete,
                        contentDescription = null,
                        tint = Color.Red
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    IconButton(
                        modifier = Modifier.weight(1f),
                        onClick = {
                            if (numberOfItemsChosen.value < quantity) {
                                numberOfItemsChosen.value++
                                totalAmount.add(price)
                                lineItems.quantity = numberOfItemsChosen.value.toLong()
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Add,
                            contentDescription = null
                        )
                    }
                    Text(
                        textAlign = TextAlign.Center,
                        text = "${numberOfItemsChosen.value}",
                        modifier = Modifier.weight(1f),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )

                    IconButton(
                        modifier = Modifier.weight(1f),
                        onClick = {
                            if (numberOfItemsChosen.value > 1) {
                                totalAmount.sub(price)
                                numberOfItemsChosen.value--
                                lineItems.quantity = numberOfItemsChosen.value.toLong()
                            }
                        }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_minimize_24),
                            contentDescription = null,
                            modifier = Modifier.padding(bottom = 5.dp)
                        )
                    }

                }
            }


        }
    }
}

@Composable
fun MyAlertDialog(
    order: com.example.e_commerce_iti.model.pojos.draftorder.LineItems,
    onConfirm: (Linedata: com.example.e_commerce_iti.model.pojos.draftorder.LineItems) -> Unit,
    shouldShowDialog: MutableState<Boolean>
) {
    if (shouldShowDialog.value) {
        AlertDialog(
            onDismissRequest = {
                shouldShowDialog.value = false
            },
            title = {
                Text(
                    text = "Delete Item",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Red
                )
            },
            text = {
                Column {
                    Icon(
                        painter = painterResource(id = R.drawable.trash), // Replace with your trash icon resource
                        contentDescription = "Delete Item",
                        modifier = Modifier
                            .size(40.dp)
                            .padding(bottom = 8.dp),
                        tint = ingredientColor1
                    )
                    Text(
                        text = "Are you sure you want to delete '${order.name}' from your cart?",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Black,
                        textAlign = TextAlign.Start
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        shouldShowDialog.value = false
                        onConfirm(order)
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = ingredientColor1)
                ) {
                    Text(
                        text = "Delete",
                        color = Color.White
                    )
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        shouldShowDialog.value = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Gray)
                ) {
                    Text(
                        text = "Cancel",
                        color = Color.White
                    )
                }
            },
            modifier = Modifier.padding(16.dp)
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
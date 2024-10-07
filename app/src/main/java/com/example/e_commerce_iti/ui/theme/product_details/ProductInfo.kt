package com.example.e_commerce_iti.ui.theme.product_details

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductInfo(name:String, price:String, currency:String, rating: Int){

    // State for controlling bottom sheet visibility
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true // Only fully expanded or hidden state
    )

    var isSheetVisible by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxWidth().height(105.dp).padding(8.dp)) {
        Text(text = name, fontSize = 24.sp, modifier = Modifier.align(Alignment.TopStart))
        Text(text ="$price $currency", fontSize =20.sp , modifier = Modifier.align(Alignment.BottomStart))
        Text(text = "Review", fontSize = 18.sp, modifier = Modifier.clickable {
            isSheetVisible = true          // view all review
        }.align(Alignment.TopEnd))
        if (isSheetVisible) {
            ModalBottomSheet(
                onDismissRequest = { isSheetVisible = false },
                sheetState = sheetState
            ) {
                // Content of the bottom sheet
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(text = "This is the bottom sheet", style = MaterialTheme.typography.bodyLarge)
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = "This is the bottom sheet", style = MaterialTheme.typography.bodyLarge)
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = "This is the bottom sheet", style = MaterialTheme.typography.bodyLarge)
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = "This is the bottom sheet", style = MaterialTheme.typography.bodyLarge)
                }
            }
        }

        RatingBar(rating = rating, modifier = Modifier.align(Alignment.BottomEnd))
    }
}
@Preview(showSystemUi = true)
@Composable
fun ProductInfoPreview(){
    Column(modifier = Modifier.fillMaxSize(),Arrangement.Center) {
        ProductInfo("T-shirt", "200", "EG",3)
    }
}

@Composable
fun RatingBar(
    rating: Int,
    modifier: Modifier = Modifier,
    starCount: Int = 5,
    starSize: Int = 32,
    filledStarColor: Color = Color.Yellow,
    unfilledStarColor: Color = Color.Gray,
) {
    Row(modifier = modifier) {
        for (i in 1..starCount) {
            IconButton(onClick = {  }) {
                Icon(
                    imageVector = if (i <= rating) Icons.Filled.Star else Icons.Outlined.Star,
                    contentDescription = null,
                    tint = if (i <= rating) filledStarColor else unfilledStarColor,
                    modifier = Modifier.size(starSize.dp)
                )
            }
        }
    }
}
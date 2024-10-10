package com.example.e_commerce_iti.ui.theme.product_details

import android.widget.RatingBar
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductInfo(name:String, price:String, rating: Int){
    // State for controlling bottom sheet visibility
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true // Only fully expanded or hidden state
    )
    var isSheetVisible by remember { mutableStateOf(false) }
    Card(colors = CardDefaults.cardColors(Color.White),modifier = Modifier.fillMaxWidth().wrapContentHeight()
        .padding(horizontal = 12.dp, vertical = 4.dp),
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)){
        Row(modifier = Modifier.fillMaxWidth().height(70.dp), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = name, fontSize = 20.sp, modifier = Modifier.padding(start = 12.dp,top=4.dp).width(250.dp))
            Text(
                text = "Review",
                color = Color.Red,
                fontSize = 20.sp,
                modifier = Modifier.padding(end = 12.dp,top=4.dp).clickable {
                    isSheetVisible = true          // view all review
                }
            )
        }
        if (isSheetVisible) {
            ModalBottomSheet(
                onDismissRequest = { isSheetVisible = false },
                sheetState = sheetState
            ) {
                // Content of the bottom sheet
                Reviews()
            }
        }
        Row(modifier = Modifier.fillMaxWidth().height(40.dp), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(
                text = "$price",
                fontSize = 22.sp,
                modifier = Modifier.padding(start = 12.dp, top = 4.dp)
            )
            RatingBar(rating = rating, modifier = Modifier.padding(end=12.dp))
        }
    }
}
@Preview(showSystemUi = true)
@Composable
fun ProductInfoPreview(){
    Column(modifier = Modifier.fillMaxSize(),Arrangement.Center) {
        ProductInfo("T-shirt kfsdkkjhgg321f;sdlkf;cvnvb", "200",3)
    }
}

@Composable
fun RatingBar(
    rating: Int,
    modifier: Modifier = Modifier,
    starCount: Int = 5,
    starSize: Int = 26,
    filledStarColor: Color = Color.Yellow,
    unfilledStarColor: Color = Color.Gray,
) {
    Row(modifier = modifier) {
        for (i in 1..starCount) {
                Icon(
                    imageVector = if (i <= rating) Icons.Filled.Star else Icons.Outlined.Star,
                    contentDescription = null,
                    tint = if (i <= rating) filledStarColor else unfilledStarColor,
                    modifier = Modifier.size(starSize.dp)
                )

        }
    }
}
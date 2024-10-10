package com.example.e_commerce_iti.ui.theme.product_details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SizeAndColors(colors:List<String>,sizes:List<String>){
    Card(colors = CardDefaults.cardColors(Color.White),modifier = Modifier.fillMaxWidth().wrapContentHeight()
        .padding(horizontal = 12.dp, vertical = 4.dp),
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)){
        Row(modifier = Modifier.fillMaxWidth().wrapContentHeight(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Text(text = "Sizes:", fontSize = 18.sp)
            LazyRow {  }
        }
        Row(modifier = Modifier.fillMaxWidth().wrapContentHeight(),
            horizontalArrangement = Arrangement.spacedBy(12.dp) ) {
            Text(text = "Colors", fontSize = 18.sp)
        }
    }
}

@Composable
fun SizeAndColorsPreview(){
    var colors= listOf("red","yellow","white","black","blue","orange")
}
package com.example.e_commerce_iti.ui.theme.product_details

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.e_commerce_iti.navyBlue
import com.example.e_commerce_iti.paleGoldenrod
import com.example.e_commerce_iti.pastelBrush
import com.example.e_commerce_iti.sienna
import com.example.e_commerce_iti.transparentBrush
import com.example.e_commerce_iti.ui.theme.home.CustomText

@Composable
fun SizeAndColors(colors:MutableList<String>, sizes:MutableList<String>, size:MutableState<Int>){
    var selectedSize by remember { mutableStateOf(sizes[0]) }
    Card(colors = CardDefaults.cardColors(Color.White),modifier = Modifier.fillMaxWidth().wrapContentHeight()
        .padding(horizontal = 12.dp, vertical = 4.dp),
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)){
        Row(modifier = Modifier.fillMaxWidth().wrapContentHeight(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Text(text = "Sizes:", fontSize = 18.sp, modifier = Modifier.padding(8.dp))
            LazyRow(
                modifier = Modifier.wrapContentWidth()
            ) {
                items(sizes) { item ->
                    // Customize each item in the LazyRow
                    CustomText(item, pastelBrush, modifier = Modifier.clickable {
                        selectedSize=item
                        size.value=sizes.indexOf(selectedSize)

                    }, isSelected = selectedSize==item)
                    Spacer(Modifier.width(8.dp))
                }
            }
        }

        Row(modifier = Modifier.fillMaxWidth().wrapContentHeight(),
            horizontalArrangement = Arrangement.spacedBy(12.dp) ) {
            Text(text = "Colors:", fontSize = 18.sp, modifier = Modifier.padding(8.dp))
            LazyRow(
                modifier = Modifier.wrapContentWidth().padding(top = 8.dp)
            ) {
                items(colors) { item ->
                    // Customize each item in the LazyRow
                    Box(modifier = Modifier.size(25.dp).background(color = colors(item) , shape = CircleShape)
                        .border(1.dp, Color.Black , CircleShape))
                    Spacer(Modifier.width(8.dp))
                }
            }
        }
    }
}

fun colors(color:String):Color{
    return if(color=="red"|| color=="burgandy") Color.Red
    else if(color=="yellow") Color.Yellow
    else if(color=="white") Color.White
    else if(color=="black") Color.Black
    else if(color=="blue") Color.Blue
    else if(color=="light_brown") sienna
    else if(color=="beige") paleGoldenrod
    else if(color.contains("gray")) Color.Gray
    else Color.Unspecified
}


@Preview(showBackground = true)
@Composable
fun SizeAndColorsPreview(){
    var colors= listOf("red","yellow","white","black","blue","orange")
    var sizes= listOf("1","2","3","4","5","6")
//SizeAndColors(colors.toMutableList(), sizes.toMutableList(),size)
}
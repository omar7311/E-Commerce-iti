package com.example.e_commerce_iti.ui.theme.product_details

import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ProductDescription(description:String){
    Column(modifier = Modifier.fillMaxWidth().height(195.dp).padding(8.dp)) {
        Text(text = "Description", fontSize = 28.sp)
        Spacer(Modifier.height(4.dp))
        Text(text = description, fontSize = 20.sp,
            modifier = Modifier.fillMaxWidth().verticalScroll(rememberScrollState()))
    }
}
@Preview(showSystemUi = true)
@Composable
fun ProductDescriptionPreview(){
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        ProductDescription(
           "This is some very long scrollable text. " +
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. " +
                    "Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. " +
                    "Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. " +
                    "Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum. " +
                    "More text goes here to make it longer. " +
                    "And even more content to demonstrate scrolling."
        )
    }
}







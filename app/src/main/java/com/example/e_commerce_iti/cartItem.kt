package com.example.e_commerce_iti

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.e_commerce_iti.ui.theme.ECommerceITITheme

@Composable
fun MyGreeting(name: String) {
    Card() {
        Row(
            modifier = Modifier.fillMaxWidth().wrapContentHeight()
                .border(3.dp, MaterialTheme.colorScheme.primary).height(100.dp).padding(10.dp)
        ) {
            //  GlideImage(contentScale = ContentScale.Fit, modifier = Modifier.width(100.dp).height(100.dp),imageModel =  "https://cdn.dummyjson.com/products/images/beauty/Essence%20Mascara%20Lash%20Princess/thumbnail.png")
            Spacer(Modifier.width(10.dp))
            Column(modifier = Modifier.fillMaxHeight()) {
                Spacer(Modifier.height(10.dp))
                Text(text = "Hello $name!")
                Spacer(Modifier.height(20.dp))
                Text(text = "Hello $name!")
            }
            Spacer(Modifier.width(10.dp))
            Column(modifier = Modifier.fillMaxHeight()) {
                Row() {
                    IconButton(onClick = { /*TODO*/ }

                    ) {

                    }
                }
            }
        }
    }
}
package com.example.e_commerce_iti

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.e_commerce_iti.ui.theme.ECommerceITITheme
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun Carts(name: String,price:Double,quantity:Int,total: MutableState<Int>) {
    val showDialog = rememberSaveable { mutableStateOf(false) }
    var state by rememberSaveable { mutableIntStateOf(0) }
    if (showDialog.value){
        MyAlertDialog(showDialog)
    }
    Card() {
        Row(
            modifier = Modifier.fillMaxWidth().wrapContentHeight()
                .border(3.dp, MaterialTheme.colorScheme.primary).height(100.dp).padding(top=10.dp, bottom = 10.dp)
        ) {
              GlideImage(contentScale = ContentScale.Fit, modifier = Modifier.weight(1f).height(100.dp).clickable {  },imageModel =  "https://cdn.dummyjson.com/products/images/beauty/Essence%20Mascara%20Lash%20Princess/thumbnail.png")
            Spacer(Modifier.width(10.dp))
            Column(modifier = Modifier.fillMaxHeight().weight(2f)) {
                Spacer(Modifier.height(10.dp))
                Text(text = "Hello $name!",modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
                Spacer(Modifier.height(20.dp))
                Text(text = "$price USD",modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
            }
            Spacer(Modifier.width(5.dp))
            Column(modifier = Modifier.fillMaxHeight().weight(1f), verticalArrangement = Arrangement.Bottom) {
                Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    IconButton(modifier = Modifier.weight(1f),onClick = {
                        if (state < quantity) {
                            total.value+=price.toInt()
                            state++
                        }
                    }

                    ) {
                        Icon(imageVector = Icons.Filled.Add, contentDescription =null)
                    }
                    Text(textAlign = TextAlign.Center,text = "$state", modifier  = Modifier.weight(1f))
                    IconButton(modifier = Modifier.weight(1f),onClick = {
                        if (state > 0) {
                            total.value-=price.toInt()

                            state--
                    }
                    }
                    ) {
                        Icon(modifier = Modifier.padding(bottom = 10.dp),painter = painterResource(id =R.drawable.baseline_minimize_24) ,contentDescription =null)
                    }
                }
            }
            Column (modifier = Modifier.fillMaxHeight()) {
                IconButton(onClick = {
                    showDialog.value=true
                }) {
                    Icon(imageVector = Icons.Filled.Delete, contentDescription =null)
                }
            }
        }
    }
}
@Composable
fun MyAlertDialog(shouldShowDialog: MutableState<Boolean>) {
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
//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun GreetingPreview() {
//    ECommerceITITheme {
//        MyGreeting("Android")
//    }
//}
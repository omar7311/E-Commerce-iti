package com.example.e_commerce_iti.ui.theme.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.ui.Alignment

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.e_commerce_iti.ui.theme.ECommerceITITheme

@Composable
fun SettingScreen() {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.SpaceAround){
    ItemsSettingScreen("Address")
    ItemsSettingScreen("Currency")
    ItemsSettingScreen("Contact us")
    ItemsSettingScreen("About us")
    Button(onClick = {} , modifier = Modifier.fillMaxWidth()) { Text(text = "Logout") }
    }
}
@Composable
fun ItemsSettingScreen(text:String){
        Card(modifier = Modifier.height(50.dp)) {
            Row(modifier = Modifier.fillMaxWidth().height(50.dp) , horizontalArrangement = Arrangement.SpaceBetween) {
                Column(modifier = Modifier.fillMaxHeight().padding(start = 10.dp),verticalArrangement = Arrangement.Center
                    , horizontalAlignment = Alignment.CenterHorizontally, ) {
                Text(text = text)
                    Column(modifier = Modifier.fillMaxHeight().padding(start = 10.dp),verticalArrangement = Arrangement.Center
                        , horizontalAlignment = Alignment.CenterHorizontally, ) {
                        Text(text = text)
                    }
            }
        }
    }
}
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SettingScreenPreview() {
    ECommerceITITheme {
        SettingScreen()
    }
}

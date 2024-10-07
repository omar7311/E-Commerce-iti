package com.example.e_commerce_iti.ui.theme.product_details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.DefaultFillType
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.e_commerce_iti.R
import com.google.firebase.auth.FirebaseAuth

@Composable
fun Actions(isAnonymous:Boolean){
    var showDialog by remember { mutableStateOf(false) }
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },  // When the dialog is dismissed
            title = {
                Text(text = "Login first")
            },
            text = {
                Text("you are guest currently , you have to login to take this action")
            },
            confirmButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("Login")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
    Row(horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth().height(100.dp)) {
        Button(onClick = {
           if(isAnonymous){
               showDialog = true
           }
            else{

            }
        }) {
            Text(text = "add to favourite")
            Spacer(Modifier.width(8.dp))
            Icon(imageVector = Icons.Filled.Favorite
                ,contentDescription = null)
        }
        Button(onClick = {
            if(isAnonymous){
                showDialog = true
            }
            else{

            }
        }) {
            Text(text = "add to card")
            Spacer(Modifier.width(8.dp))
            Icon(imageVector = Icons.Filled.ShoppingCart
                ,contentDescription = null)
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun ActionsPreview(){
    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
        Actions(true)
    }
}
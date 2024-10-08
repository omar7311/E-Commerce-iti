package com.example.e_commerce_iti.ui.theme.search

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.e_commerce_iti.model.pojos.Product
import com.example.e_commerce_iti.ui.theme.favorite.FavouriteItem
import com.example.e_commerce_iti.ui.theme.home.CustomButtonBar
import com.example.e_commerce_iti.ui.theme.home.CustomTopBar
import com.example.e_commerce_iti.ui.theme.home.SimpleText
import com.example.e_commerce_iti.ui.theme.products.ProductsList


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(controller: NavController,context: Context) {
    val products= mutableListOf<Product>()
    Scaffold(
        topBar = { CustomTopBar("Search", controller) },  // Update title to "Cart"
        bottomBar = { CustomButtonBar(controller,context) },     // Keep the navigation controller for buttons
    ) { innerPadding ->                                // Use padding for the content
        Column(modifier = Modifier.padding(innerPadding)) {
            SearchBarWithClearButton()
            //ProductsList(products,controller)
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxSize()
            ) {
                val str=mutableListOf<String>()
                str.add("omar")
                str.add("ahmed")
                str.add("mostafa")
                str.add("omar")
                str.add("ahmed")
                str.add("mostafa")
                itemsIndexed(str) { _, title ->
                    FavouriteItem(title)
                }
            }
        }
    }
}
@Preview(showSystemUi = true)
@Composable
fun SearchBarWithClearButton() {
    var searchQuery by remember { mutableStateOf("") }
       TextField(
           value = searchQuery,
           onValueChange = { searchQuery = it },
           label = { Text("Search") },
           placeholder = { Text("Type something...") },
           singleLine = true,
           trailingIcon = {
               // Show the clear button if there's text in the search bar
               if (searchQuery.isNotEmpty()) {
                   IconButton(onClick = { searchQuery = "" }) {
                       Icon(Icons.Filled.Close, contentDescription = "Clear search")
                   }
               }
           },
           modifier = Modifier
               .fillMaxWidth()
               .padding(16.dp)
       )


}
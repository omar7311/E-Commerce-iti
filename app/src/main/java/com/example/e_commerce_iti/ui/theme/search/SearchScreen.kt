package com.example.e_commerce_iti.ui.theme.search

import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material.OutlinedTextField
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.e_commerce_iti.model.apistates.BrandsApiState
import com.example.e_commerce_iti.model.apistates.UiState
import com.example.e_commerce_iti.model.pojos.Product
import com.example.e_commerce_iti.ui.theme.ShimmerHorizontalGrid
import com.example.e_commerce_iti.ui.theme.ShimmerLoadingGrid
import com.example.e_commerce_iti.ui.theme.favorite.FavouriteItem
import com.example.e_commerce_iti.ui.theme.home.BrandListItems
import com.example.e_commerce_iti.ui.theme.home.CustomButtonBar
import com.example.e_commerce_iti.ui.theme.home.CustomTopBar
import com.example.e_commerce_iti.ui.theme.home.SimpleText
import com.example.e_commerce_iti.ui.theme.products.ProductsList
import com.example.e_commerce_iti.ui.theme.viewmodels.cartviewmodel.CartViewModel
import com.example.e_commerce_iti.ui.theme.viewmodels.currencyviewmodel.CurrencyViewModel
import com.example.e_commerce_iti.ui.theme.viewmodels.productInfo_viewModel.ProductInfoViewModel
import com.example.e_commerce_iti.ui.theme.viewmodels.searchViewModel.SearchViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(controller: NavController,
                 context: Context,
                 searchViewModel: SearchViewModel,
                 currencyViewModel: CurrencyViewModel,
                 productInfoViewModel: ProductInfoViewModel,
                 cartViewModel: CartViewModel,
) {
    LaunchedEffect(Unit) {
        searchViewModel.getAllProduct()
    }
    // Observe the state of the brands
    val searchList by searchViewModel.allProduct.collectAsState()
    Scaffold(
        topBar = { CustomTopBar("Search", controller) },  // Update title to "Cart"
        bottomBar = { CustomButtonBar(controller,context) },     // Keep the navigation controller for buttons
    ) { innerPadding ->                                // Use padding for the content
        Column(modifier = Modifier.padding(innerPadding)) {
            var searchQuery by remember { mutableStateOf("") }
            OutlinedTextField(
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
            when (searchList) {
                is UiState.Loading -> {
                    ShimmerLoadingGrid()
                }

                is UiState.Success -> {
                    val filterProducts= mutableListOf<Product>()
                    val allProducts=(searchList as UiState.Success).data
                    for (i in allProducts){
                        if(i.title.contains(searchQuery,true)){
                            filterProducts.add(i)
                        }
                    }
                   ProductsList(filterProducts,controller,currencyViewModel, productInfoViewModel, cartViewModel ,context)

                }

                is UiState.Failure -> {

                }

                is UiState.Error -> {}
                UiState.Non -> {}
            }

            }
        }
    }


@Preview(showSystemUi = true)
@Composable
fun SearchBarWithClearButton() {



}
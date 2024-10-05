package com.example.e_commerce_iti.ui.theme.category

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.e_commerce_iti.model.apistates.CustomCollectionStates
import com.example.e_commerce_iti.model.apistates.ProductsApiState
import com.example.e_commerce_iti.model.pojos.CustomCollection
import com.example.e_commerce_iti.model.pojos.Product
import com.example.e_commerce_iti.ui.theme.home.CustomButtonBar
import com.example.e_commerce_iti.ui.theme.home.CustomTopBar
import com.example.e_commerce_iti.ui.theme.products.ProductItem
import com.example.e_commerce_iti.ui.theme.viewmodels.home_viewmodel.HomeViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryScreen(homeViewModel: HomeViewModel, controller: NavController) {
    var collectionId by remember { mutableStateOf(0L) }

    Scaffold(
        topBar = { CustomTopBar("Category", controller) },
        bottomBar = { CustomButtonBar(controller) },
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            Column  {
                FetchCustomCollections(homeViewModel) { selectedCollection ->
                    if (selectedCollection.id != collectionId) {
                        collectionId = selectedCollection.id
                        Log.i("CategoryScreen", "Selected collection ID: $collectionId")
                    }
                }
                if (collectionId != 0L) {
                    FetchProductsByCustomCollection(homeViewModel, controller, collectionId)
                }
            }
        }
    }
    // Fetch products when collectionId changes
    LaunchedEffect(collectionId) {
        if (collectionId != 0L) {
            homeViewModel.getProductsByCustomCollection(collectionId)
        }
    }
}



@Composable
fun FetchCustomCollections(
    homeViewModel: HomeViewModel,
    onCategorySelected: (CustomCollection) -> Unit
) {
    // Observe the state of the brands  and use it to prevent calling api multiple times when ui is built
    LaunchedEffect(Unit) {
        homeViewModel.getCustomCollections()
    }
    val customCollections by homeViewModel.customCollectionStateFlow.collectAsState()

    when (customCollections) {
        is CustomCollectionStates.Loading -> {
            CircularProgressIndicator()
        }

        is CustomCollectionStates.Success -> {  // test to see if the custom collections is come or not
            val customCollections =
                (customCollections as CustomCollectionStates.Success).customCollections
            CustomCollectionColumn(customCollections) { selectedCollection ->
                onCategorySelected(selectedCollection) // Pass the selected collection
            }
        }

        is CustomCollectionStates.Failure -> {
            val error = (customCollections as CustomCollectionStates.Failure).msg
        }
    }
}

@Composable
fun FetchProductsByCustomCollection(
    homeViewModel: HomeViewModel,
    controller: NavController,
    collectionId: Long
) {
    LaunchedEffect(Unit) {
        homeViewModel.getProductsByCustomCollection(collectionId)
    }
    val customProducts by homeViewModel.productsByCustomCollectionStateFlow.collectAsState()
    when (customProducts) {
        is ProductsApiState.Loading -> {
            CircularProgressIndicator()
        }

        is ProductsApiState.Success -> {
            val products = (customProducts as ProductsApiState.Success).products
            ProductGrid(products, controller)  // pass the products here to use it in the grid
        }

        is ProductsApiState.Failure -> {
            val error = (customProducts as ProductsApiState.Failure).msg
        }
    }
}

/**
 *      category columns in the left
 */
@Composable
fun CustomCollectionColumn(
    customCollection: List<CustomCollection>,
    onCategorySelected: (CustomCollection) -> Unit
) {
    LazyRow (
        modifier = Modifier.padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        itemsIndexed(customCollection) { _, customCollection ->
            CustomCollectionItem(customCollection, onCategorySelected)
        }
    }
}

@Composable
fun CustomCollectionItem(
    customCollection: CustomCollection,
    onCategorySelected: (CustomCollection) -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clickable { onCategorySelected(customCollection) }  // when press on specific collection
            .padding(8.dp)
    ) {
        Image(
            painter = rememberAsyncImagePainter(customCollection.image.src),
            contentDescription = customCollection.handle,
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surface),
            contentScale = ContentScale.FillBounds
        )
        Text(text = customCollection.title, fontSize = 14.sp)
    }
}

// create a  lazy Grid to show the products in the right
@Composable
fun ProductGrid(products: List<Product>, controller: NavController) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.padding(8.dp),
        contentPadding = PaddingValues(8.dp)
    ) {
        items(products) { product ->
            ProductItem(product, controller) // to navigate when press on it
        }
    }
}


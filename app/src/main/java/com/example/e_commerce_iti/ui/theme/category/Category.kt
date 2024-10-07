package com.example.e_commerce_iti.ui.theme.category

import android.content.Context
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
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
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.e_commerce_iti.DEFAULT_CUSTOM_COLLECTION_ID
import com.example.e_commerce_iti.R
import com.example.e_commerce_iti.model.apistates.CustomCollectionStates
import com.example.e_commerce_iti.model.apistates.ProductsApiState
import com.example.e_commerce_iti.model.pojos.CustomCollection
import com.example.e_commerce_iti.model.pojos.Product
import com.example.e_commerce_iti.network.NetworkObserver
import com.example.e_commerce_iti.ui.theme.ShimmerLoadingCustomCollection
import com.example.e_commerce_iti.ui.theme.ShimmerLoadingGrid
import com.example.e_commerce_iti.ui.theme.home.CustomButtonBar
import com.example.e_commerce_iti.ui.theme.home.CustomTopBar
import com.example.e_commerce_iti.ui.theme.home.MyLottieAnimation
import com.example.e_commerce_iti.ui.theme.products.FilterButtonWithSlider
import com.example.e_commerce_iti.ui.theme.products.ProductItem
import com.example.e_commerce_iti.ui.theme.viewmodels.home_viewmodel.HomeViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryScreen(
    homeViewModel: HomeViewModel,
    controller: NavController,
    networkObserver: NetworkObserver,context: Context

) {
    var collectionId by remember { mutableStateOf(DEFAULT_CUSTOM_COLLECTION_ID) } // default collection id
    var selectedPrice by remember { mutableStateOf(10000f) }  // Single value for the selected price
    var selectedCategory by remember { mutableStateOf<String?>(null) } //state for selected category

    val minPrice = 0f  // Minimum price fixed
    val maxPrice = 5000f  // Maximum price fixed
    // Observe network state
    val isNetworkAvailable by networkObserver.isConnected.collectAsState(initial = false)
    Scaffold(
        topBar = { CustomTopBar("Category", controller) },
        bottomBar = { CustomButtonBar(controller,context) },
    ) { innerPadding ->

        if(isNetworkAvailable){
            Box(
                modifier = Modifier
                    .padding(innerPadding)

                    .fillMaxSize() // Fill the entire screen
            ) {
                Column(
                    modifier = Modifier.padding(15.dp)
                ) {
                    FetchCustomCollections(homeViewModel) { selectedCollection ->
                        if (selectedCollection.id != collectionId) {
                            collectionId = selectedCollection.id
                            getUpdatedCollectionId(collectionId.toLong(), homeViewModel) // get the new collection id
                        }
                    }

                    FilterButtonWithSlider(minPrice, maxPrice) { price ->
                        selectedPrice = price
                    }

                    if (collectionId != 0L) {
                        FetchProductsByCustomCollection(
                            homeViewModel,
                            controller,
                            collectionId,
                            selectedPrice,
                            selectedCategory
                        )
                    }
                }

                // FloatingActionButton (Extended or Expandable)
                ExpandableFab(
                    onFilterClothes = { selectedCategory = "T-SHIRTS" }, // Set selected category
                    onFilterShoes = { selectedCategory = "SHOES" },
                    onFilterAccessories = { selectedCategory = "ACCESSORIES" },
                    onFabClose = {
                        selectedCategory = null
                    }, // Reset selected category when FAB is closed
                    modifier = Modifier
                        .align(Alignment.BottomEnd) // Align to bottom-right
                        .padding(16.dp) // Add some padding from the edges
                )
            }
        }else
        {
            MyLottieAnimation()  // play lotti when no network
        }

    }

    // Fetch products when collectionId changes
  /*  LaunchedEffect(collectionId) {
        if (collectionId != 0L&&isNetworkAvailable)
            Log.d("Tag", "CategoryScreen: $collectionId")
            homeViewModel.getProductsByCustomCollection(collectionId)
        }*/
    }


fun getUpdatedCollectionId(collectionId: Long,homeViewModel: HomeViewModel) {
     homeViewModel.getProductsByCustomCollection(collectionId)
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
            ShimmerLoadingCustomCollection()
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
            Log.e("Categories", "Failed to load custom collections: $error")
        }
    }
}

@Composable
fun FetchProductsByCustomCollection(
    homeViewModel: HomeViewModel,
    controller: NavController,
    collectionId: Long,
    maxPrice: Float,
    selectedCategory: String? // TO FILTER THE PRODUCTS BY type
) {
    LaunchedEffect(Unit) {
        homeViewModel.getProductsByCustomCollection(collectionId)
    }
    val customProducts by homeViewModel.productsByCustomCollectionStateFlow.collectAsState()
    when (customProducts) {
        is ProductsApiState.Loading -> {
            ShimmerLoadingGrid()        }

        is ProductsApiState.Success -> {
            val products = (customProducts as ProductsApiState.Success).products
            val filteredProducts =
                if (selectedCategory != null) {
                    products.filter { product ->
                        product.product_type == selectedCategory
                    }
                } else {
                    products.filter { product ->
                        val productPrice = product.variants[0].price.toFloatOrNull() ?: 0f
                        productPrice <= maxPrice  // Filter products with price <= slider value
                    }
                }

            ProductGrid(filteredProducts, controller)  // here im passing the products
        }

        is ProductsApiState.Failure -> {
            val error = (customProducts as ProductsApiState.Failure).msg
            Log.e("Categories", "Failed to load custom collections: $error")
        }
    }
}

/**
 *      category columns in the left
 */
@Composable
fun CustomCollectionColumn(
    customCollections: List<CustomCollection>,
    onCustomCollectionSelected: (CustomCollection) -> Unit
) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    var selectedCollection by remember { mutableStateOf<CustomCollection?>(null) }

    LazyRow(
        modifier = Modifier
            .padding(7.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(customCollections) { customCollection ->
            CustomCollectionItem(
                customCollection = customCollection,
                onCustomCollectionSelected = {
                    onCustomCollectionSelected(customCollection)
                    selectedCollection = customCollection
                },
                modifier = Modifier.width(screenWidth / 4),// Adjusts item width based on screen size
                isSelected = selectedCollection == customCollection // Pass whether this item is selected

            )
        }
    }
}

@Composable
fun CustomCollectionItem(
    customCollection: CustomCollection,
    onCustomCollectionSelected: (CustomCollection) -> Unit,
    modifier: Modifier = Modifier,
    isSelected: Boolean,

    ) {
    val scale by animateFloatAsState(if (isSelected) 1.1f else 1f) // Pop up slightly when selected
    val offsetY by animateDpAsState(if (isSelected) (-10).dp else 0.dp) // Move up slightly when selected
    Column(
        modifier = modifier
            .clickable { onCustomCollectionSelected(customCollection) }
            .scale(scale) // Apply scale transformation
            .offset(y = offsetY) // Apply translation in the Y direction for pop-up effect
            .padding(4.dp)

    ) {
        Image(
            painter = rememberAsyncImagePainter(customCollection.image.src),
            contentDescription = customCollection.handle,
            modifier = Modifier
                .size(70.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surface),
            contentScale = ContentScale.FillBounds
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = customCollection.title,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onSurface),
            textAlign = TextAlign.Center
        )
    }
}


// create a  lazy Grid to show the products in the right
@Composable
fun ProductGrid(products: List<Product>, controller: NavController) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.padding(1.dp),
        contentPadding = PaddingValues(8.dp)
    ) {
        items(products) { product ->
            ProductItem(product, controller) // to navigate when press on it
        }
    }
}


@Composable
fun ExpandableFab(
    onFilterClothes: () -> Unit,
    onFilterShoes: () -> Unit,
    onFilterAccessories: () -> Unit,
    onFabClose: () -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    val rotationState = animateFloatAsState(if (expanded) 45f else 0f) // Rotate the FAB


    Column(
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = modifier

    ) {
        // Expandable Buttons
        AnimatedVisibility(visible = expanded) {
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                FloatingActionButton(onClick = {
                    onFilterClothes()
                    expanded = false // Close the FAB
                }) {
                    Icon(
                        painterResource(R.drawable.clothesfilter),
                        contentDescription = "Filter by Clothes"
                    )
                }
                FloatingActionButton(onClick = {
                    onFilterShoes()
                    expanded = false // Close the FAB
                }) {
                    Icon(
                        painterResource(R.drawable.shoes),
                        contentDescription = "Filter by Shoes"
                    )
                }

                FloatingActionButton(onClick = {
                    expanded = false // Close the FAB

                    onFilterAccessories()
                }) {
                    Icon(
                        painterResource(R.drawable.accessories2),
                        contentDescription = "Filter by Accessories"
                    )
                }
            }
        }

        // Main Floating Action Button
        FloatingActionButton(onClick = {
            expanded = !expanded
            if (expanded) {
                onFabClose()
            }
        })
        {
            Icon(
                painterResource(
                    R.drawable.filter
                ),
                contentDescription = "Filter",
                modifier = Modifier.rotate(rotationState.value)
            )
        }
    }
}




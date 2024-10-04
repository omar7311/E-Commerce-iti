package com.example.e_commerce_iti.ui.theme.products

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.e_commerce_iti.R
import com.example.e_commerce_iti.model.apistates.ProductsApiState
import com.example.e_commerce_iti.model.pojos.Product
import com.example.e_commerce_iti.ui.theme._navigation.Screens
import com.example.e_commerce_iti.ui.theme.home.CustomButtonBar
import com.example.e_commerce_iti.ui.theme.home.CustomImage
import com.example.e_commerce_iti.ui.theme.home.CustomText
import com.example.e_commerce_iti.ui.theme.home.CustomTopBar
import com.example.e_commerce_iti.ui.theme.home.SimpleText
import com.example.e_commerce_iti.ui.theme.viewmodels.home_viewmodel.HomeViewModel
import kotlin.math.min

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductScreen(homeVieVModel: HomeViewModel, controller: NavController, vendorName: String) {

    Scaffold(
        topBar = { CustomTopBar("Products", controller) },  // Update title to "Cart"
        bottomBar = { CustomButtonBar(controller) },     // Keep the navigation controller for buttons
    ) { innerPadding ->                                // Use padding for the content

        ProductsContent(
            homeVieVModel,
            controller,
            vendorName,
            Modifier.padding(innerPadding)
        )
    }
}


@Composable
fun ProductsContent(
    homeViewModel: HomeViewModel,
    controller: NavController,
    vendorName: String,
    modifier: Modifier
) {
    var selectedPrice by remember { mutableStateOf(10000f) }  // Single value for the selected price
    val minPrice = 0f  // Minimum price fixed
    val maxPrice = 5000f  // Maximum price fixed

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(5.dp)
    ) {
        CustomText("Available Products", Color.White, padding = PaddingValues(15.dp))

        FilterButtonWithSlider(minPrice, maxPrice) { price ->
            selectedPrice = price  // Update the selected price
        }        // Pass the selected price from the slider


        // Pass the selected price for filtering products
        FetchingProductsByVendor(homeViewModel, controller, vendorName, selectedPrice)
    }
}



@Composable
fun FetchingProductsByVendor(
    homeViewModel: HomeViewModel,
    controller: NavController,
    vendorName: String,
    maxPrice: Float
) {

    // Observe the state of the brands
    LaunchedEffect(Unit) {
        homeViewModel.getProductsByVendor(vendorName)
    }

    val productsState by homeViewModel.productStateFlow.collectAsState()

    when (productsState) {

        is ProductsApiState.Loading -> {
            // Show loading indicator
            CircularProgressIndicator()

        }

        is ProductsApiState.Success -> {
            val products = (productsState as ProductsApiState.Success).products
            // Display the products
            Log.i("Products", "Products: $products")

            val filteredProducts = products.filter { product ->
                val productPrice = product.variants[0].price.toFloatOrNull() ?: 0f
                productPrice <= maxPrice  // Filter products with price <= slider value
            }
            ProductsList(filteredProducts, controller)

        }

        is ProductsApiState.Failure -> {
            // Show error message
            val eror = (productsState as ProductsApiState.Failure).msg

        }

        else -> {
            // Handle other states if needed

        }
    }
}


@Composable
fun ProductsList(products: List<Product>, controller: NavController) {

    LazyVerticalGrid(
        columns = androidx.compose.foundation.lazy.grid.GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxSize()
    ) {
        itemsIndexed(products) { _, product ->
            ProductItem(product, controller)
        }
    }
}

@Composable
fun ProductItem(product: Product, controller: NavController) {
    Column(
        modifier = Modifier
            .clickable { } // navigation to Product Details here
            .padding(8.dp)
            .fillMaxWidth()
            .wrapContentHeight()
            .clip(RoundedCornerShape(10.dp))
            .background(Color.White)
            .wrapContentHeight(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        CustomImage(product.images[0].src)
        Spacer(modifier = Modifier.size(10.dp))
        CustomText(product.title, Color.LightGray)  // title
        CustomText(product.variants[0].price, Color.White)  // price
    }
}


/**
 *      this function for price filter using the slider
 */

@Composable
fun PriceFilterSlider(
    minPrice: Float,
    maxPrice: Float,
    onValueChange: (Float) -> Unit
) {
    var sliderPosition by remember { mutableFloatStateOf(minPrice) }  // One value for the slider position

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
    ) {
        Text(text = "Price: \$${sliderPosition.toInt()}")  // Display the current price selected
        Slider(
            value = sliderPosition,
            onValueChange = {
                sliderPosition = it
                onValueChange(sliderPosition)  // Update the selected price value
            },
            valueRange = minPrice..maxPrice,  // Range between minimum and maximum price
            colors = SliderDefaults.colors(
                thumbColor = MaterialTheme.colorScheme.secondary,
                activeTrackColor = MaterialTheme.colorScheme.secondary,
                inactiveTrackColor = MaterialTheme.colorScheme.secondaryContainer,
            )
        )
    }
}


/**
 *      toggle button and show the slider
 */
@Composable
fun FilterButtonWithSlider(minPrice: Float, maxPrice: Float, onPriceSelected: (Float) -> Unit) {
    var isSelected by remember { mutableStateOf(false) }

    Column (
        modifier = Modifier.padding(16.dp),
    ) {
        // Filter button with icon
        Box(
            modifier = Modifier
                .size(60.dp)
                .background(
                    if (isSelected) MaterialTheme.colorScheme.primary else Color.LightGray,
                    CircleShape
                )
                .clickable {
                    isSelected = !isSelected // Toggle slider visibility
                },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = if (isSelected) R.drawable.filterlist else R.drawable.filteralt),
                contentDescription = "Filter",
                tint = Color.White
            )
        }

        if (isSelected) {
            PriceFilterSlider(minPrice, maxPrice) { price ->
                onPriceSelected(price)  // Update the selected price
            }
        }
    }
}

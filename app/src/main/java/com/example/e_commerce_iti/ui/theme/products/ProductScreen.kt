package com.example.e_commerce_iti.ui.theme.products

import android.content.Context
import android.util.Log
import android.widget.ImageButton
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.materialIcon
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.e_commerce_iti.R
import com.example.e_commerce_iti.coralColor
import com.example.e_commerce_iti.crimson
import com.example.e_commerce_iti.currentUser
import com.example.e_commerce_iti.firebrick
import com.example.e_commerce_iti.gradientBrush
import com.example.e_commerce_iti.indigo
import com.example.e_commerce_iti.ingredientColor1
import com.example.e_commerce_iti.limeGreen
import com.example.e_commerce_iti.model.apistates.ProductsApiState
import com.example.e_commerce_iti.model.apistates.UiState
import com.example.e_commerce_iti.model.pojos.Product
import com.example.e_commerce_iti.model.pojos.draftorder.DraftOrder
import com.example.e_commerce_iti.model.reposiatory.ReposiatoryImpl
import com.example.e_commerce_iti.navyBlue
import com.example.e_commerce_iti.paleGoldenrod
import com.example.e_commerce_iti.pastelBrush
import com.example.e_commerce_iti.tomato
import com.example.e_commerce_iti.turquoise
import com.example.e_commerce_iti.ui.theme.ShimmerLoadingGrid
import com.example.e_commerce_iti.ui.theme._navigation.Screens
import com.example.e_commerce_iti.ui.theme.home.CustomButtonBar
import com.example.e_commerce_iti.ui.theme.home.CustomImage
import com.example.e_commerce_iti.ui.theme.home.CustomText
import com.example.e_commerce_iti.ui.theme.home.CustomTopBar
import com.example.e_commerce_iti.ui.theme.viewmodels.cartviewmodel.CartViewModel
import com.example.e_commerce_iti.ui.theme.viewmodels.currencyviewmodel.CurrencyViewModel
import com.example.e_commerce_iti.ui.theme.viewmodels.home_viewmodel.HomeViewModel
import com.example.e_commerce_iti.ui.theme.viewmodels.productInfo_viewModel.ProductInfoViewModel
import com.example.e_commerce_iti.whiteBrush
import com.google.gson.Gson

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductScreen(
    homeVieVModel: HomeViewModel,
    currencyViewModle: CurrencyViewModel,
    productInfoViewModel: ProductInfoViewModel,
    cartViewModel: CartViewModel,
    controller: NavController,
    vendorName: String,
    context: Context,
) {

    Scaffold(
        topBar = { CustomTopBar("Products", controller) },  // Update title to "Cart"
        bottomBar = {
            CustomButtonBar(
                controller,
                context = LocalContext.current
            )
        },     // Keep the navigation controller for buttons
    ) { innerPadding ->                                // Use padding for the content

        ProductsContent(
            homeVieVModel,
            currencyViewModle,
            productInfoViewModel,
            cartViewModel ,
            controller,
            vendorName,
            Modifier.padding(innerPadding),
            context
        )
    }
}


@Composable
fun ProductsContent(
    homeViewModel: HomeViewModel,
    currencyViewModle: CurrencyViewModel,
    productInfoViewModel: ProductInfoViewModel,
    cartViewModel: CartViewModel,
    controller: NavController,
    vendorName: String,
    modifier: Modifier,
    context: Context
) {

    var selectedPrice by remember { mutableStateOf(10000f) }  // Single value for the selected price
    val minPrice = 0f  // Minimum price fixed
    val maxPrice = 5000f  // Maximum price fixed

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(5.dp)
    ) {
        CustomText("Available Products", whiteBrush, padding = PaddingValues(15.dp))

        FilterButtonWithSlider(minPrice, maxPrice) { price ->
            selectedPrice = price  // Update the selected price
        }        // Pass the selected price from the slider


        // Pass the selected price for filtering products
        FetchingProductsByVendor(
            homeViewModel,
            currencyViewModle,
            productInfoViewModel,
            cartViewModel ,
            controller,
            vendorName,
            selectedPrice,
            context
        )
    }
}


@Composable
fun FetchingProductsByVendor(
    homeViewModel: HomeViewModel,
    currencyViewModle: CurrencyViewModel,
    productInfoViewModel: ProductInfoViewModel,
    cartViewModel: CartViewModel,
    controller: NavController,
    vendorName: String,
    maxPrice: Float,
    context: Context
) {

    // Observe the state of the brands
    LaunchedEffect(Unit) {
        homeViewModel.getProductsByVendor(vendorName)
    }

    val productsState by homeViewModel.productStateFlow.collectAsState()

    when (productsState) {
        is ProductsApiState.Loading -> {
            // Show loading indicator
            ShimmerLoadingGrid()
        }
        is ProductsApiState.Success -> {
            val products = (productsState as ProductsApiState.Success).products
            // Display the products
            Log.i("Products", "Products: $products")

            val filteredProducts = products.filter { product ->
                val productPrice = product.variants[0].price.toFloatOrNull() ?: 0f
                productPrice <= maxPrice  // Filter products with price <= slider value
            }
            ProductsList(
                filteredProducts,
                controller,
                currencyViewModle,
                productInfoViewModel,
                cartViewModel ,
                context
            )

        }
        is ProductsApiState.Failure -> {
            // Show error message
            val eror = (productsState as ProductsApiState.Failure).msg

        }
    }
}


@Composable
fun ProductsList(
    products: List<Product>,
    controller: NavController,
    currencyViewModle: CurrencyViewModel,
    productInfoViewModel: ProductInfoViewModel,
    cartViewModel: CartViewModel,
    context: Context
) {

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxSize()
    ) {
        itemsIndexed(products) { _, product ->
            ProductItem(product, controller, currencyViewModle, productInfoViewModel, cartViewModel , context)
        }
    }
}

@Composable
fun ProductItem(
    product: Product,
    controller: NavController,
    currencyViewModel: CurrencyViewModel,
    productInfoViewModel: ProductInfoViewModel,
    cartViewModel: CartViewModel,
    context: Context
) {
    var visible by remember { mutableStateOf(false) }

    // Launch effect to make the item visible
    LaunchedEffect(Unit) {
        visible = true
    }

    // Animation for the product item
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(animationSpec = tween(durationMillis = 400)) +
                scaleIn(initialScale = 0.9f, animationSpec = tween(durationMillis = 400)) +
                slideInVertically(
                    initialOffsetY = { -it },
                    animationSpec = tween(durationMillis = 400)
                ),
        exit = fadeOut(animationSpec = tween(durationMillis = 300)) +
                scaleOut(targetScale = 0.8f, animationSpec = tween(durationMillis = 300)) +
                slideOutVertically(
                    targetOffsetY = { it },
                    animationSpec = tween(durationMillis = 300)
                )
    ) {
        Card(
            modifier = Modifier
                .clickable {
                    // Navigation to Product Details here
                    controller.navigate(
                        Screens.ProductDetails.createDetailRoute(
                            Gson().toJson(
                                product
                            )
                        )
                    )
                }
                .padding(2.dp), // Padding around the card
            elevation = CardDefaults.cardElevation(defaultElevation = 10.dp), // Set elevation
            shape = RoundedCornerShape(10.dp), // Rounded corners
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .background(Color.White), // Background color of the card
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Image display
                CustomImage(product.images[0].src)
                Spacer(modifier = Modifier.size(10.dp))
                // Title
                CustomText(
                    product.title,
                    whiteBrush,
                    textColor = Color.Black,
                    fontSize = 16.sp
                )
                Row(
                    modifier = Modifier.wrapContentWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    getCurrencyAndPrice(product.variants[0].price, currencyViewModel)?.let {
                        CustomText(
                            it,
                            pastelBrush,
                            textColor = navyBlue,
                            fontSize = 18.sp,
                            padding = PaddingValues(10.dp)
                        )
                    }



                    FavoriteButton(
                        product = product,
                        productInfoViewModel = productInfoViewModel,
                        context = context
                    )
                }
            }
        }
    }
}




@Composable
fun getCurrencyAndPrice(price: String, currencyViewModle: CurrencyViewModel): String? {

    var priceAndCurrency: String? = null
    LaunchedEffect(Unit) {
        currencyViewModle.getCurrency()
    }
    val currency = currencyViewModle.currencyStateFlow.collectAsState()
    when (currency.value) {
        is UiState.Success -> {
            val currency = (currency.value as UiState.Success).data
            val priceNum = (price.toFloat() * currency.second)
            val formattedPrice = String.format("%.2f", priceNum)
            val currc = currency.first
            priceAndCurrency = "$formattedPrice  $currc"
        }

        is UiState.Error -> "Price Error"
        is UiState.Failure -> "Price Error"
        UiState.Loading -> {}
        UiState.Non -> {}
    }
    return priceAndCurrency
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
            .padding(10.dp)
    ) {
        Text(text = "Price: \$${sliderPosition.toInt()}")  // Display the current price selected
        Slider(
            value = sliderPosition,
            onValueChange = {
                sliderPosition = it
                onValueChange(sliderPosition)
            },
            valueRange = minPrice..maxPrice,
            modifier = Modifier.height(20.dp),
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

    Row(
        modifier = Modifier.padding(10.dp),
    ) {
        // Filter button with icon
        Box(
            modifier = Modifier
                .size(40.dp)
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
                tint = Color.White,
                modifier = Modifier.size(24.dp) // Set a specific size for the icon
            )
        }

        if (isSelected) {
            PriceFilterSlider(minPrice, maxPrice) { price ->
                onPriceSelected(price)  // Update the selected price
            }
        }
    }
}


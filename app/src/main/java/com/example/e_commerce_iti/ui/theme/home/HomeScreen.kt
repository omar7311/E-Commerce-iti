package com.example.e_commerce_iti.ui.theme.home

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.Navigator
import coil.compose.rememberAsyncImagePainter
import com.example.e_commerce_iti.R
import com.example.e_commerce_iti.model.apistates.BrandsApiState
import com.example.e_commerce_iti.model.pojos.BrandData
import com.example.e_commerce_iti.model.remote.RemoteDataSourceImp
import com.example.e_commerce_iti.model.reposiatory.IReposiatory
import com.example.e_commerce_iti.model.reposiatory.ReposiatoryImpl
import com.example.e_commerce_iti.ui.theme.viewmodels.HomeViewModel
import com.example.e_commerce_iti.ui.theme.viewmodels.HomeViewModelFactory

/**
 *      don't forget navigation
 */
val repository: IReposiatory = ReposiatoryImpl(RemoteDataSourceImp())
val factory: HomeViewModelFactory = HomeViewModelFactory(repository)


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {

    Scaffold(

        topBar = { CustomTopBar("Home") },
        bottomBar = { CustomButtonBar() },
    ) { innerPadding ->
        HomeContent(modifier = Modifier.padding(innerPadding))
    }

}


@Composable
fun HomeContent(modifier: Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(5.dp)

    ) {
        /**
         *  here we put all content of home Screen
         */
        FetchingBrandData()
    }
}

@Composable
fun FetchingBrandData() {
    // Create ViewModel using the factory
    val homeViewModel: HomeViewModel = viewModel(factory = factory)
    LaunchedEffect(Unit) {
        homeViewModel.getBrands()
    }
    // Observe the state of the brands
    val brandsState by homeViewModel.brandStateFlow.collectAsState()
    when (brandsState) {
        is BrandsApiState.Loading -> {
            Log.i("Brands", "Loading brands data...")
        }

        is BrandsApiState.Success -> {
            Log.i("Brands", "Successfully loaded brands data.")
            val brands = (brandsState as BrandsApiState.Success).brands
            Log.i("Brands", "Brands: $brands")
            BrandListItems(brands, PaddingValues(16.dp))
        }

        is BrandsApiState.Failure -> {
            Log.e(
                "Brands",
                "Failed to load brands data: ${(brandsState as BrandsApiState.Failure).msg}"
            )
        }

        else -> {
            Log.w("Brands", "Unexpected state: $brandsState")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewFunctions() {
    CustomButtonBar()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopBar(customTitle: String) {
    TopAppBar(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .fillMaxWidth()
            .background(Color.LightGray),
        title = { Text(customTitle, modifier = Modifier.wrapContentWidth()) },
        navigationIcon = {
            IconButton(onClick = {}, modifier = Modifier.padding(5.dp)) {
                Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
            }
        },
        actions = {
            // Favorite icon on the right
            IconButton(onClick = {}) {
                Icon(
                    modifier = Modifier.padding(5.dp),
                    imageVector = Icons.Default.FavoriteBorder,
                    contentDescription = "Favorite"
                )
            }
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = Color.LightGray // Set a light gray background color
        ),
    )
}

@Composable
fun CustomButtonBar(
) {
    var selectedItem by remember { mutableStateOf(0) }

    NavigationBar {
        NavigationBarItem(
            icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
            label = { Text("Home") },
            selected = selectedItem == 0,
            onClick = { selectedItem = 0 }
        )

        NavigationBarItem(
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.category),
                    contentDescription = "Category",
                    modifier = Modifier.size(24.dp)
                )
            },
            selected = selectedItem == 1,
            onClick = { selectedItem = 1 },
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.ShoppingCart, contentDescription = "Cart") },
            label = { Text("Cart") },
            selected = selectedItem == 2,
            onClick = { selectedItem = 2 }
        )

        NavigationBarItem(
            icon = { Icon(Icons.Default.Person, contentDescription = "Profile") },
            label = { Text("Profile") },
            selected = selectedItem == 3,
            onClick = { selectedItem = 3 }
        )
    }
}

@Composable
fun BrandListItems(
    brands: List<BrandData>,
    paddingValues: PaddingValues = PaddingValues(16.dp)
) {
    // Wrapping LazyHorizontalGrid inside a Card
    Card(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxWidth()
            .fillMaxHeight(0.39f),
        shape = RoundedCornerShape(16.dp), // Rounded corners for the Card
        elevation = CardDefaults.cardElevation(4.dp) // Use CardDefaults to set elevation
    ) {
        LazyHorizontalGrid(
            rows = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize(), // Fill the Card space
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalArrangement = Arrangement.Center,
            contentPadding = PaddingValues(16.dp) // Content padding for overall grid
        ) {
            itemsIndexed(brands) { _, brand ->
                BrandItem(brand)
            }
        }
    }
}


@Composable
fun BrandItem(brand: BrandData) {


    Column(
        modifier = Modifier
            .fillMaxWidth() // Ensure the item spans the full width of the cell
            .padding(8.dp)
            .wrapContentHeight()
            .clip(RoundedCornerShape(20.dp))
            .background(Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        brand.imageSrc?.let {   // get brand image from network
            BrandImage(it)
        }
        Spacer(modifier = Modifier.height(4.dp))
        // Text section
        Text(
            text = brand.title,
            color = Color.Black,
            fontSize = 24.sp
        )

    }
}

@Composable
fun BrandImage(url: String) {
    val painter = rememberAsyncImagePainter(
        model = url,
        contentScale = ContentScale.Crop // Ensuring image covers the space
    )

    Image(
        painter = painter,
        contentDescription = null,
        modifier = Modifier
            .size(100.dp) // Adjusted size for better balance
            .clip(RoundedCornerShape(10.dp)) // Soft corners on images
            .background(Color.LightGray),// Background color for loading images
        contentScale = ContentScale.Crop // Ensuring image covers the space

    )
}

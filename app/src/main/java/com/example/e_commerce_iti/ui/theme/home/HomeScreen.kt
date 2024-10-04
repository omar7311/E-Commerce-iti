package com.example.e_commerce_iti.ui.theme.home

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.runtime.livedata.observeAsState
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.example.e_commerce_iti.R
import com.example.e_commerce_iti.model.apistates.BrandsApiState
import com.example.e_commerce_iti.model.pojos.BrandData
import com.example.e_commerce_iti.model.remote.RemoteDataSourceImp
import com.example.e_commerce_iti.model.reposiatory.IReposiatory
import com.example.e_commerce_iti.model.reposiatory.ReposiatoryImpl
import com.example.e_commerce_iti.ui.theme._navigation.Screens
import com.example.e_commerce_iti.ui.theme.viewmodels.home_viewmodel.HomeViewModel
import com.example.e_commerce_iti.ui.theme.viewmodels.coupn_viewmodel.CouponViewModel
import com.example.e_commerce_iti.ui.theme.viewmodels.home_viewmodel.HomeViewModelFactory

/**
 *      don't forget navigation
 */


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(homeViewModel: HomeViewModel,controller : NavController = rememberNavController()) {

    Scaffold(
        topBar = { CustomTopBar("Home",controller) },
        bottomBar = { CustomButtonBar(controller) }, // give it the controller to navigate with it
    ) { innerPadding ->
        HomeContent(homeViewModel , Modifier.padding(innerPadding))
    }

}


@Composable
fun HomeContent(homeViewModel: HomeViewModel ,modifier: Modifier) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState()) // Make the column scrollable
            .fillMaxSize()
            .padding(5.dp)

    ) {
        /**
         *  here we put all content of home Screen
         */
        CustomText("Coupons",Color.White,padding = PaddingValues(15.dp))
        CouponCarousel()
        CustomText("Brands",Color.White, padding = PaddingValues(15.dp))
        FetchingBrandData(homeViewModel)
    }
}

/**
 *  this function to show coupons randomly
 */
@Composable
fun CouponCarousel(viewModel: CouponViewModel = viewModel()) {
    val couponImages by viewModel.couponImages.observeAsState(emptyList())

    // Display the images in a carousel-like format
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        itemsIndexed(couponImages) { _,coupon ->
            Box(
                modifier = Modifier
                    .clickable {  }
                    .height(200.dp)
                    .width(400.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.LightGray),
                contentAlignment = Alignment.Center
            ) {
                // Image display
                Image(
                    painter = rememberImagePainter(coupon.imageUrl),
                    contentDescription = coupon.description,
                    modifier = Modifier.fillMaxWidth(),
                    contentScale = ContentScale.Crop

                )
            }
        }
    }
}


@Composable
fun FetchingBrandData(homeViewModel: HomeViewModel) {

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopBar(customTitle: String,controller: NavController) {
    TopAppBar(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .fillMaxWidth()
            .background(Color.LightGray),
        title = { Text(customTitle, modifier = Modifier.wrapContentWidth()) },
        navigationIcon = {
            // Search icon on the left
            IconButton(onClick = {controller.navigate(Screens.Search.route)}, modifier = Modifier.padding(5.dp)) {
                Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
            }
        },
        actions = {
            // Favorite icon on the right
            IconButton(onClick = {controller.navigate(Screens.Favorite.route)}) {
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
fun CustomButtonBar(controller: NavController) {
    val currentRoute = remember { mutableStateOf(Screens.Home.route) } // Initial route
    val navBackStackEntry by controller.currentBackStackEntryAsState()

    // Update the current route whenever the back stack entry changes
    LaunchedEffect(navBackStackEntry) {
        currentRoute.value = navBackStackEntry?.destination?.route ?: Screens.Home.route
    }

    NavigationBar {
        NavigationBarItem(
            icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
            label = { Text("Home") },
            selected = currentRoute.value == Screens.Home.route,
            onClick = {
                controller.navigate(Screens.Home.route)
            }
        )

        NavigationBarItem(
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.category),
                    contentDescription = "Category",
                    modifier = Modifier.size(24.dp)
                )
            },
            label = { Text("Category") },
            selected = currentRoute.value == Screens.Category.route,
            onClick = {
                controller.navigate(Screens.Category.route)
            }
        )

        NavigationBarItem(
            icon = { Icon(Icons.Default.ShoppingCart, contentDescription = "Cart") },
            label = { Text("Cart") },
            selected = currentRoute.value == Screens.Cart.route,
            onClick = {
                controller.navigate(Screens.Cart.route)
            }
        )

        NavigationBarItem(
            icon = { Icon(Icons.Default.Person, contentDescription = "Profile") },
            label = { Text("Profile") },
            selected = currentRoute.value == Screens.Profile.route,
            onClick = {
                controller.navigate(Screens.Profile.route)
            }
        )
    }
}

@Composable
fun BrandListItems(
    brands: List<BrandData>,
    paddingValues: PaddingValues = PaddingValues(16.dp)
) {
    Card(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxWidth()
            .height(430.dp)
            .wrapContentHeight()
        //.fillMaxHeight(0.60f)
        ,

        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        LazyHorizontalGrid(
            rows = GridCells.Fixed(2),
            modifier =
            Modifier
                .wrapContentHeight()
                .fillMaxSize()
                .fillMaxHeight(),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            contentPadding = PaddingValues(16.dp)
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
            .clickable { }
            .padding(8.dp)
            .fillMaxWidth()
            .wrapContentHeight()
            .clip(RoundedCornerShape(10.dp))
            .background(Color.White)
            .wrapContentHeight(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        brand.imageSrc?.let {
            BrandImage(it)
        }
        Spacer(modifier = Modifier.height(10.dp))
        // Text section
        CustomText(brand.title,Color.LightGray)

    }
}

@Composable
fun BrandImage(url: String) {
    val painter = rememberAsyncImagePainter(
        model = url,
        contentScale = ContentScale.Crop
    )

    Image(
        painter = painter,
        contentDescription = null,
        modifier = Modifier
            .padding(10.dp)
            .size(130.dp)
            .clip(RoundedCornerShape(10.dp)),
        contentScale = ContentScale.Fit

    )
}

@Composable
fun CustomText(brandTitle: String,backGroundColor:Color,padding:PaddingValues =PaddingValues()) {
    Text(
        text = brandTitle,
        color = Color.Black,
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold, // Make it bold
        modifier = Modifier
            .background(backGroundColor)
            .clip(RoundedCornerShape(2.dp))
            .fillMaxWidth()
            .padding(padding)
    )
}

@Composable
fun SimpleText(simpleText:String){

    Text(
        text = simpleText,
        color = Color.Black,
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold, // Make it bold
        modifier = Modifier
            .background(Color.LightGray)
            .clip(RoundedCornerShape(2.dp))
            .fillMaxWidth()
            .padding(10.dp)
    )
}

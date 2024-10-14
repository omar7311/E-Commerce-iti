package com.example.e_commerce_iti.ui.theme.home

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
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
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.e_commerce_iti.R
import com.example.e_commerce_iti.model.apistates.BrandsApiState
import com.example.e_commerce_iti.model.apistates.UiState
import com.example.e_commerce_iti.model.pojos.BrandData
import com.example.e_commerce_iti.network.NetworkObserver
import com.example.e_commerce_iti.ui.theme.ShimmerHorizontalGrid
import com.example.e_commerce_iti.ui.theme._navigation.Screens
import com.example.e_commerce_iti.ui.theme.viewmodels.home_viewmodel.HomeViewModel
import com.example.e_commerce_iti.ui.theme.viewmodels.coupn_viewmodel.CouponViewModel
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.example.e_commerce_iti.NetworkErrorContent
import com.example.e_commerce_iti.earthyBrush
import com.example.e_commerce_iti.gradientBrush
import com.example.e_commerce_iti.ingredientColor1
import com.example.e_commerce_iti.navyBlue
import com.example.e_commerce_iti.pastelBrush
import com.example.e_commerce_iti.transparentBrush
import com.example.e_commerce_iti.ui.theme.ShimmerEffect
import com.example.e_commerce_iti.ui.theme.viewmodels.cartviewmodel.CartViewModel
import com.example.e_commerce_iti.whiteBrush
import kotlinx.coroutines.delay

/**
 *      don't forget navigation
 */


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    context: Context,
    couponViewModel: CouponViewModel,
    homeViewModel: HomeViewModel,
    controller: NavController = rememberNavController(),
    networkObserver: NetworkObserver,
    cartViewModel: CartViewModel
) {

    Scaffold(
containerColor = Color.White,
        modifier = Modifier.background(whiteBrush),
        topBar = { CustomTopBar("Home", controller)},
        bottomBar = {
            CustomButtonBar(
                controller,
                context
            )
        }, // give it the controller to navigate with it
    ) { innerPadding ->
        val isConnected = networkObserver.isConnected.collectAsState()
        if (isConnected.value) {
            HomeContent(couponViewModel, homeViewModel, controller, Modifier.padding(innerPadding))
        } else {
            NetworkErrorContent() // when no connection
        }
    }

}


@Composable
fun HomeContent(
    couponViewModel: CouponViewModel,
    homeViewModel: HomeViewModel,
    controller: NavController,
    modifier: Modifier
) {
    Column(
        modifier = modifier
            .background(whiteBrush)
            .verticalScroll(rememberScrollState()) // Make the column scrollable
            .fillMaxSize()
            .padding(5.dp)

    ) {
        /**
         *  here we put all content of home Screen
         */
        CustomText2(
            "Coupons",
            transparentBrush,
            Color.Black,
            fontSize = 25.sp,
            style = FontWeight.Bold,
            fontFamily = FontFamily.Cursive,
            modifier = Modifier.padding(horizontal = 4.dp)
        )
        CouponCarousel(couponViewModel)
        CustomText2(
            "Brands",
            transparentBrush,
            Color.Black,
            fontSize = 25.sp,
            style = FontWeight.Bold,
            fontFamily = FontFamily.Cursive,
            modifier = Modifier.padding(horizontal = 4.dp)
        )
        FetchingBrandData(homeViewModel, controller)
    }
}

/**
 *  this function to show coupons randomly
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CouponCarousel(viewModel: CouponViewModel) {
    val context = LocalContext.current
    val couponImages by viewModel.couponImages.collectAsState(initial = emptyList())
    val couponsState by viewModel.couponsStateflow.collectAsState()
    val pagerState = rememberPagerState { couponImages.size }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        viewModel.getCoupons()
    }

    LaunchedEffect(pagerState) {
        while (true) {
            delay(4000L) // Change slide every 3 seconds
            val nextPage = (pagerState.currentPage + 1) % (couponImages.size.coerceAtLeast(1))
            pagerState.animateScrollToPage(nextPage)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(2.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (couponsState) {
            is UiState.Loading -> {
                ShimmerEffect()
            }

            is UiState.Success -> {
                val coupons = (couponsState as UiState.Success).data
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier
                        .wrapContentWidth()
                        .height(150.dp)
                ) { page ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 8.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(Color.LightGray)
                            .pointerInput(Unit) {
                                detectTapGestures(
                                    onLongPress = {
                                        copyToClipboard(
                                            context,
                                            coupons[page].discount_codes[0].code
                                        )
                                    }
                                )
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        AsyncImage(
                            model = couponImages[page].imageUrl,
                            contentDescription = couponImages[page].description,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }
                }

                Row(
                    Modifier
                        .height(12.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    repeat(couponImages.size) { iteration ->
                        val color =
                            if (pagerState.currentPage == iteration) ingredientColor1 else Color.LightGray
                        Box(
                            modifier = Modifier
                                .padding(2.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(color)
                                .size(8.dp)
                        )
                    }
                }
            }

            is UiState.Error -> {
                Text("Error loading coupons")
            }

            is UiState.Failure -> {
                Text("Failed to load coupons")
            }

            UiState.Non -> {
                // Handle initial state if needed
            }
        }
    }
}

fun copyToClipboard(context: Context, text: String) {
    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText("Copied Text", text)
    clipboard.setPrimaryClip(clip)
    Toast.makeText(context, "$text copied to clipboard", Toast.LENGTH_LONG).show()
}

@Composable
fun FetchingBrandData(homeViewModel: HomeViewModel, controller: NavController) {

    LaunchedEffect(Unit) {
        homeViewModel.getBrands()
    }
    // Observe the state of the brands
    val brandsState by homeViewModel.brandStateFlow.collectAsState()
    when (brandsState) {
        is BrandsApiState.Loading -> {
            ShimmerHorizontalGrid()
        }

        is BrandsApiState.Success -> {
            Log.i("Brands", "Successfully loaded brands data.")
            val brands = (brandsState as BrandsApiState.Success).brands
            Log.i("Brands", "Brands: $brands")
            BrandListItems(brands, controller, PaddingValues(5.dp))
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
fun CustomTopBar(customTitle: String, controller: NavController) {
    TopAppBar(
        modifier = Modifier
            .padding(horizontal = 15.dp,  vertical = 6.dp)
            .wrapContentHeight()
            .clip(RoundedCornerShape(35.dp))
            .shadow(8.dp, RoundedCornerShape(20.dp))
            .background(brush = gradientBrush),
        title = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                   // .height(50.dp)
                    .padding(horizontal = 30.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
              /*  // App name at the top
                CustomText2("Snap Shop",
                    transparentBrush,
                    navyBlue,/*    Text(
                    text = customTitle,
                    fontSize = 18.sp,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                )*/
                    fontSize = 22.sp,
                    style = FontWeight.Bold,
                    fontFamily = FontFamily.Serif,
                    modifier = Modifier.padding(4.dp)
                )*/
                // App name at the top
                CustomText2(customTitle,
                    gradientBrush,
                    Color.White,
                    fontSize = 22.sp,
                    style = FontWeight.Bold,
                    fontFamily = FontFamily.Serif,
                    modifier = Modifier.padding(4.dp)
                )
                // Screen title below the app name

            }
        },
        navigationIcon = {
            // Search icon on the left
            IconButton(
                onClick = {
                    controller.navigate(Screens.Search.route) {
                        popUpTo(controller.graph.findStartDestination().id) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                },
                modifier = Modifier.padding(start = 10.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = Color.White
                )
            }
        },
        actions = {
            // Favorite icon on the right
            IconButton(onClick = {
                controller.navigate(Screens.Favorite.route) {
                    popUpTo(controller.graph.findStartDestination().id) {
                        inclusive = true
                    }
                    launchSingleTop = true
                }
            }) {
                Icon(
                    modifier = Modifier.padding(end = 12.dp),
                    imageVector = Icons.Default.FavoriteBorder,
                    tint = Color.White,
                    contentDescription = "Favorite"
                )
            }
        },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = Color.Transparent // Transparent to let gradient shine
        )
    )
}


@Composable
fun CustomButtonBar(controller: NavController, context: Context) {
    val currentRoute = remember { mutableStateOf(Screens.Home.route) } // Initial route
    val navBackStackEntry by controller.currentBackStackEntryAsState()

    // Update the current route whenever the back stack entry changes
    LaunchedEffect(navBackStackEntry) {
        currentRoute.value = navBackStackEntry?.destination?.route ?: Screens.Home.route
    }

    NavigationBar(
        containerColor = Color.White,
        modifier = Modifier
            .background(brush = whiteBrush)
            .height(50.dp)
    ) {
        NavigationBarItem(
            icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
            label = { Text("Home") },
            selected = currentRoute.value == Screens.Home.route,
            onClick = {
                controller.navigate(Screens.Home.route) {
                    popUpTo(controller.graph.findStartDestination().id) {
                        inclusive = true
                    }
                    launchSingleTop = true
                }
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
                controller.navigate(Screens.Category.route) {
                    popUpTo(controller.graph.findStartDestination().id) {
                        inclusive = true
                    }
                    launchSingleTop = true
                }
            }
        )

        NavigationBarItem(
            icon = { Icon(Icons.Default.ShoppingCart, contentDescription = "Cart") },
            label = { Text("Cart") },
            selected = currentRoute.value == Screens.Cart.route,
            onClick = {
                /*  if (Firebase.auth.currentUser != null && !Firebase.auth.currentUser!!.email.isNullOrBlank())
                  else
                      Toast.makeText(context, "Please Login First", Toast.LENGTH_SHORT).show()*/
                controller.navigate(Screens.Cart.route) {
                    popUpTo(controller.graph.findStartDestination().id) {
                        inclusive = true
                    }
                    launchSingleTop = true
                }

            }
        )

        NavigationBarItem(
            icon = { Icon(Icons.Default.Person, contentDescription = "Profile") },
            label = { Text("Profile") },
            selected = currentRoute.value == Screens.Profile.route,
            onClick = {
                controller.navigate(Screens.Profile.route) {
                    popUpTo(controller.graph.findStartDestination().id) {
                        inclusive = true
                    }
                    launchSingleTop = true
                }
            }
        )
    }
}

@Composable
fun BrandListItems(
    brands: List<BrandData>,
    controller: NavController,
    paddingValues: PaddingValues = PaddingValues(4.dp)
) {
    LazyHorizontalGrid(
        rows = GridCells.Fixed(2),
        modifier = Modifier
           .padding(paddingValues)
            .height(380.dp) // Set a fixed height
            .fillMaxWidth()
            .wrapContentHeight(),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalArrangement = Arrangement.spacedBy(2.dp),
        contentPadding = PaddingValues(0.dp) // Set content padding to 0
    ) {
        itemsIndexed(brands) { _, brand ->
            BrandItem(brand, controller) // Render each brand item
        }
    }
}


@Composable
fun BrandItem(brand: BrandData, controller: NavController) {
    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        visible = true
    }

    // Animation for the brand item
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(animationSpec = tween(durationMillis = 250)) + // Reduced duration
                scaleIn(initialScale = 0.9f, animationSpec = tween(durationMillis = 250)) +
                slideInVertically(
                    initialOffsetY = { -it },
                    animationSpec = tween(durationMillis = 250)
                ),
        exit = fadeOut(animationSpec = tween(durationMillis = 200)) + // Reduced duration
                scaleOut(targetScale = 0.8f, animationSpec = tween(durationMillis = 200)) +
                slideOutVertically(
                    targetOffsetY = { it },
                    animationSpec = tween(durationMillis = 200)
                )
    ) {
        Card(
            modifier = Modifier
                .padding(horizontal = 4.dp)
                .fillMaxWidth()
                .wrapContentHeight()
                .clickable { // Navigate to product screen with brand id
                    controller.navigate(Screens.ProductSc.createRoute(brand.title))
                },
            elevation = CardDefaults.cardElevation(defaultElevation = 16.dp), // Set elevation
            shape = RoundedCornerShape(10.dp), // Rounded corners
        ) {
            Column(
                modifier = Modifier
                    .background(Color.White)
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(horizontal = 8.dp)
                    .clip(RoundedCornerShape(10.dp)),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                brand.imageSrc?.let {
                    CustomImage(it)
                }
                Spacer(modifier = Modifier.height(4.dp))
                // Text section
                //CustomText(brand.title, pastelBrush, textColor = navyBlue, fontSize = 18.sp)
                CustomText2(brand.title,
                    pastelBrush,
                    navyBlue,
                    fontSize = 18.sp,
                    style = FontWeight.Bold,
                    fontFamily = FontFamily.Serif,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }
        }
    }
}

@Composable
fun CustomImage(url: String) {
    val painter = rememberAsyncImagePainter(
        // this function to load image instead of glide
        model = url,
    )

    Image(
        painter = painter,
        contentDescription = null,
        modifier = Modifier
            .size(140.dp)
            .height(115.dp)
            .clip(RoundedCornerShape(10.dp))
            .padding(10.dp),
        contentScale = ContentScale.Fit

    )
}

@Composable
fun CustomText(
    textToUse: String,
    backGroundColor: Brush,
    textColor: Color = Color.Black,
    fontSize: TextUnit = 20.sp,
    padding: PaddingValues = PaddingValues(),
    modifier: Modifier= Modifier,
    style: FontWeight=FontWeight.Normal,
    isSelected: Boolean = false
) {
    Text(
        text = textToUse,
        color = textColor,
        fontSize = fontSize,
        fontWeight = style,
        modifier =
            modifier
                .padding(padding)
                .clip(RoundedCornerShape(15.dp))
                .background(if(isSelected){
                    earthyBrush} else{backGroundColor})
                .padding(8.dp)
, // Inner padding
        maxLines = 1, // Limit to one line
        overflow = TextOverflow.Ellipsis,
    )
}

@Composable
fun CustomText2(
    textToUse: String,
    backGroundColor: Brush,
    textColor: Color = Color.Black,
    fontSize: TextUnit = 20.sp,
    modifier: Modifier = Modifier,
    style: FontWeight = FontWeight.Bold,
    fontFamily: FontFamily? = null // New parameter for font family
) {
    Text(
        text = textToUse,
        color = textColor,
        fontSize = fontSize,
        fontWeight = style,
        fontFamily = fontFamily, // Use the font family parameter
        modifier = modifier
            .clip(RoundedCornerShape(15.dp))
            .background(backGroundColor)
            .padding(4.dp), // Inner padding
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
    )
}


@Composable
fun SimpleText(simpleText: String) {

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

@Composable
fun MyLottieAnimation(
    modifier: Modifier
) {
    val lottieComposition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.network2))
    val isAnimationPlaying = lottieComposition != null

    if (isAnimationPlaying) {
        LottieAnimation(
            composition = lottieComposition,
            iterations = 1
        )
    } else {

    }
}




package com.example.e_commerce_iti.ui.theme._navigation

import android.app.Activity
import android.content.Context
import android.util.Log

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.e_commerce_iti.LoginScreen
import com.example.e_commerce_iti.PRODUCT_ID
import com.example.e_commerce_iti.R
import com.example.e_commerce_iti.SignupScreen
import com.example.e_commerce_iti.VENDOR_NAME
import com.example.e_commerce_iti.currentUser
import com.example.e_commerce_iti.getCurrent
import com.example.e_commerce_iti.model.local.LocalDataSourceImp
import com.example.e_commerce_iti.model.pojos.Order
import com.example.e_commerce_iti.model.pojos.Product
import com.example.e_commerce_iti.model.remote.RemoteDataSourceImp
import com.example.e_commerce_iti.model.reposiatory.IReposiatory
import com.example.e_commerce_iti.model.reposiatory.ReposiatoryImpl
import com.example.e_commerce_iti.network.NetworkObserver
import com.example.e_commerce_iti.ui.theme.cart.CartScreen
import com.example.e_commerce_iti.ui.theme.category.CategoryScreen
import com.example.e_commerce_iti.ui.theme.changeuserinfo.ChangeUserDataScreen
import com.example.e_commerce_iti.ui.theme.favorite.FavoriteScreen
import com.example.e_commerce_iti.ui.theme.home.HomeScreen
import com.example.e_commerce_iti.ui.theme.orders.OrderDetailsScreen
import com.example.e_commerce_iti.ui.theme.orders.OrdersScreen
import com.example.e_commerce_iti.ui.theme.product_details.ProductDetails
import com.example.e_commerce_iti.ui.theme.products.ProductScreen
import com.example.e_commerce_iti.ui.theme.profile.ProfileScreen
import com.example.e_commerce_iti.ui.theme.search.SearchScreen
import com.example.e_commerce_iti.ui.theme.settings.SettingScreen
import com.example.e_commerce_iti.ui.theme.viewmodels.cartviewmodel.CartViewModel
import com.example.e_commerce_iti.ui.theme.viewmodels.cartviewmodel.CartViewModelFac
import com.example.e_commerce_iti.ui.theme.viewmodels.changeuserdata.ChangeUserDataViewModel
import com.example.e_commerce_iti.ui.theme.viewmodels.changeuserdata.ChangeUserDataViewModelFactory
import com.example.e_commerce_iti.ui.theme.viewmodels.coupn_viewmodel.CouponViewModel
import com.example.e_commerce_iti.ui.theme.viewmodels.coupn_viewmodel.CouponsViewModelFactory
import com.example.e_commerce_iti.ui.theme.viewmodels.currencyviewmodel.CurrenciesViewModelFactory
import com.example.e_commerce_iti.ui.theme.viewmodels.currencyviewmodel.CurrencyViewModel
import com.example.e_commerce_iti.ui.theme.viewmodels.home_viewmodel.HomeViewModel
import com.example.e_commerce_iti.ui.theme.viewmodels.home_viewmodel.HomeViewModelFactory
import com.example.e_commerce_iti.ui.theme.viewmodels.orders.OrdersFactory
import com.example.e_commerce_iti.ui.theme.viewmodels.orders.OrdersViewModel
import com.example.e_commerce_iti.ui.theme.viewmodels.productInfo_viewModel.ProductInfoViewModel
import com.example.e_commerce_iti.ui.theme.viewmodels.productInfo_viewModel.ProductInfoViewModelFac

import com.google.firebase.app
import com.google.firebase.auth.auth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.gson.Gson
import java.net.URLEncoder

/**
 *      sealed Class to manage navigation between Screens in the app
 */

sealed class Screens(val route: String) {

    object Orders : Screens(route = "orders")
    object OrderDetails : Screens(route = "order_details/{order}") {
        fun createRoute(orderJson: String): String {
            val encodedOrder = URLEncoder.encode(orderJson, "UTF-8") // Encode the JSON string
            return "order_details/$encodedOrder"
        }
    }

    //
    object Setting : Screens(route = "setting")
    object Home : Screens(route = "home")
    object Category : Screens(route = "category")
    object Signup : Screens(route = "signUP")
    object Login : Screens(route = "Login")
    object Cart : Screens(route = "cart")
    object Profile : Screens(route = "profile")
    object Favorite : Screens(route = "favorite")
    object ChangeUserData : Screens(route = "change_user_data")
    object Search : Screens(route = "search")
    object ProductSc : Screens(route = "product/{$VENDOR_NAME}") {
        fun createRoute(vendorName: String) = "product/$vendorName"
    }

    object ProductDetails : Screens(route = "product_details/{product}") {
        fun createDetailRoute(gsonProduct: String) :String{
            val encodedProduct=URLEncoder.encode(gsonProduct,"UTF-8")
            return "product_details/$encodedProduct"
    }
    }

}


@Composable
fun Navigation(networkObserver: NetworkObserver, context: Activity) {
    val navController = rememberNavController()
    val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(context.getString(R.string.web_api_key))
        .requestEmail()
        .build()

    val googleSignInClient = GoogleSignIn.getClient(context, googleSignInOptions)
    NavHost(navController = navController, startDestination = Screens.Login.route) {
        val repository: IReposiatory = ReposiatoryImpl(
            RemoteDataSourceImp(), LocalDataSourceImp(
                context.getSharedPreferences(
                    LocalDataSourceImp.currentCurrency, Context.MODE_PRIVATE
                )
            )
        )
        val curreneyFactory: CurrenciesViewModelFactory = CurrenciesViewModelFactory(repository)
        val cartFactory: CartViewModelFac = CartViewModelFac(repository)
        val homeFactory: HomeViewModelFactory = HomeViewModelFactory(repository)
        val couponFactory: CouponsViewModelFactory = CouponsViewModelFactory(repository)
        val cartViewModelFac = CartViewModelFac(repository)
        val productInfoViewModelFac= ProductInfoViewModelFac(repository)
        val changeUserDataFactory: ChangeUserDataViewModelFactory =
            ChangeUserDataViewModelFactory(repository)
        val ordersFactory: OrdersFactory = OrdersFactory(repository)
        composable(route = Screens.Home.route) {
            val homeViewModel: HomeViewModel = viewModel(factory = homeFactory)
            val CopuonsViewModel: CouponViewModel = viewModel(factory = couponFactory)
            val cartViewModel:CartViewModel= viewModel(factory = cartFactory)
            LaunchedEffect(Unit) {
                if (Firebase.auth.currentUser != null && !Firebase.auth.currentUser!!.email.isNullOrBlank()) {
                    val e = Firebase.auth.currentUser
                    getCurrent(e!!.email!!, repository)
                }
            }
            HomeScreen(context, CopuonsViewModel, homeViewModel,
                navController, networkObserver,cartViewModel)
        }

        composable(route = Screens.Category.route) {
            val homeViewModel: HomeViewModel = viewModel(factory = homeFactory)
            CategoryScreen(homeViewModel, navController, networkObserver, LocalContext.current)
        }
        composable(route = Screens.Cart.route) {
            val cartViewModel: CartViewModel = viewModel(factory = cartFactory)
            CartScreen(cartViewModel, navController, context)
        }
        composable(route = Screens.Profile.route) {
            ProfileScreen(navController)
        }
        composable(route = Screens.ChangeUserData.route) {
            val changeUserDataViewModel: ChangeUserDataViewModel =
                viewModel(factory = changeUserDataFactory)
            ChangeUserDataScreen(viewModel = changeUserDataViewModel, navController = navController)
        }
        composable(route = Screens.Favorite.route) {
            val cartViewModel:CartViewModel= viewModel(factory = cartFactory)
            FavoriteScreen(cartViewModel,navController) }
        composable(route = Screens.Search.route) { SearchScreen(navController, context) }
        composable(route = Screens.Signup.route) { SignupScreen(navController, context) }
        composable(route = Screens.Login.route) {
            LoginScreen(navController, context, googleSignInClient) {
                navController.navigate(Screens.Home.route)
            }
        }
        composable(route = Screens.Search.route) { SearchScreen(navController,context) }
        composable(route = Screens.Favorite.route) {
            val cartViewModel:CartViewModel = viewModel(factory = cartFactory)
            FavoriteScreen(cartViewModel,navController) }
        composable(route = Screens.Search.route) { SearchScreen(navController,context) }
        composable(route = Screens.Signup.route) { SignupScreen(navController, context) }
        composable(route = Screens.Login.route) {
            LoginScreen(navController, context, googleSignInClient) {
                navController.navigate(Screens.Home.route)
            }
        }

        // here im modifying the product route to Extract the product ID from the route
        composable(route = Screens.ProductSc.route) {
            // Create ViewModel using the factory
            val homeViewModel: HomeViewModel = viewModel(factory = homeFactory)
            val vendorName = it.arguments?.getString(VENDOR_NAME)
            if (vendorName != null) {
                ProductScreen(homeViewModel, navController, vendorName)
            }
        }
        composable(route = Screens.Setting.route) {
            val currencyViewModel: CurrencyViewModel = viewModel(factory = curreneyFactory)
            SettingScreen(currencyViewModel, navController)
        }

        composable(
            route = Screens.ProductDetails.route,
            arguments = listOf(navArgument("product") {
                type = NavType.StringType // take care of this it to mention that long will sent
            })
        ) { backStackEntry ->
            val productInfoViewModel:ProductInfoViewModel= viewModel(factory = productInfoViewModelFac)
            val gsonProduct = backStackEntry.arguments?.getString("product")
            val gson = Gson()
            val product = gson.fromJson(gsonProduct, Product::class.java)
            ProductDetails(productInfoViewModel,product = product, controller = navController, context)


        }

        composable(route = Screens.Orders.route) {
            val orderViewModel: OrdersViewModel = viewModel(factory = ordersFactory)
            OrdersScreen(context,orderViewModel, navController, networkObserver)
        }

        composable(
            route = Screens.OrderDetails.route,
            arguments = listOf(navArgument("order") { type = NavType.StringType })
        ) {backStackEntry ->
            val orderJson = backStackEntry.arguments?.getString("order")  // get the json
            val gson = Gson()
            val order = gson.fromJson(orderJson, Order::class.java)
            val orderViewModel: OrdersViewModel = viewModel(factory = ordersFactory)

            OrderDetailsScreen(context,order = order, orderViewModel,controller = navController, networkObserver)
        }

    }

    }
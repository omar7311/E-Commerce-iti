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
import com.example.e_commerce_iti.ui.theme.viewmodels.PaymentViewModelFactory
import com.example.e_commerce_iti.ui.theme.viewmodels.PaymentViewModel
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
import com.example.e_commerce_iti.ui.theme.viewmodels.searchViewModel.SearchViewModel
import com.example.e_commerce_iti.ui.theme.viewmodels.searchViewModel.SearchViewModelFac

import com.google.firebase.app
import com.google.firebase.auth.auth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.play.integrity.internal.al

import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.gson.Gson
import java.net.URLEncoder


sealed class Screens(val route: String) {

    object Orders : Screens(route = "orders")
    object OrderDetails : Screens(route = "order_details/{order}") {
        fun createRoute(orderJson: String): String {
            val encodedOrder = URLEncoder.encode(orderJson, "UTF-8") // Encode the JSON string
            return "order_details/$encodedOrder"
        }
    }

    object Setting : Screens(route = "setting")
    object Home : Screens(route = "home")
    object Category : Screens(route = "category")
    object Signup : Screens(route = "signUP")
    object Login : Screens(route = "Login")
    object Cart : Screens(route = "cart")
    object Payment : Screens(route = "Payment")
    object Profile : Screens(route = "profile")
    object Favorite : Screens(route = "favorite")
    object ChangeUserData : Screens(route = "change_user_data")
    object Search : Screens(route = "search")
    object ProductSc : Screens(route = "product/{$VENDOR_NAME}") {
        fun createRoute(vendorName: String) = "product/$vendorName"
    }

    object ProductDetails : Screens(route = "product_details/{product}") {
        fun createDetailRoute(gsonProduct: String): String {
            val encodedProduct = URLEncoder.encode(gsonProduct, "UTF-8")
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
    val repository: IReposiatory = ReposiatoryImpl(
        RemoteDataSourceImp(),
        LocalDataSourceImp(context.getSharedPreferences(LocalDataSourceImp.currentCurrency, Context.MODE_PRIVATE))
    )

    val paymentViewModelFactory = PaymentViewModelFactory(repository)
    val ordersFactory = OrdersFactory(repository)
    val searchFactory = SearchViewModelFac(repository)
    val curreneyFactory = CurrenciesViewModelFactory(repository)
    val cartFactory = CartViewModelFac(repository)
    val homeFactory = HomeViewModelFactory(repository)
    val couponFactory = CouponsViewModelFactory(repository)
    val productInfoViewModelFac = ProductInfoViewModelFac(repository)
    val changeUserDataFactory = ChangeUserDataViewModelFactory(repository)

    NavHost(navController = navController, startDestination = Screens.Login.route) {

        composable(route = Screens.Home.route) {
            val homeViewModel: HomeViewModel = viewModel(factory = homeFactory)
            val CopuonsViewModel: CouponViewModel = viewModel(factory = couponFactory)
            val cartViewModel: CartViewModel = viewModel(factory = cartFactory)
            LaunchedEffect(Unit) {
                if (Firebase.auth.currentUser != null && !Firebase.auth.currentUser!!.email.isNullOrBlank()) {
                    val e = Firebase.auth.currentUser
                    getCurrent(e!!.email!!, repository)
                }
            }
            HomeScreen(context, CopuonsViewModel, homeViewModel, navController, networkObserver, cartViewModel)
        }

        composable(route = Screens.Category.route) {
            val homeViewModel: HomeViewModel = viewModel(factory = homeFactory)
            val currencyViewModel: CurrencyViewModel = viewModel(factory = curreneyFactory)
            CategoryScreen(homeViewModel, currencyViewModel, navController, networkObserver, LocalContext.current)
        }

        composable(route = Screens.Cart.route) {
            val cartViewModel: CartViewModel = viewModel(factory = cartFactory)
            CartScreen(cartViewModel, navController, context,networkObserver)
        }

        composable(route = Screens.Profile.route) {
            ProfileScreen(navController)
        }

        composable(route = Screens.ChangeUserData.route) {
            val changeUserDataViewModel: ChangeUserDataViewModel = viewModel(factory = changeUserDataFactory)
            ChangeUserDataScreen(viewModel = changeUserDataViewModel, navController = navController)
        }

        composable(route = Screens.Favorite.route) {
            val cartViewModel: CartViewModel = viewModel(factory = cartFactory)
            FavoriteScreen(cartViewModel, navController)
        }

        composable(route = Screens.Search.route) {
            val searchViewModel: SearchViewModel = viewModel(factory = searchFactory)
            val currencyViewModel: CurrencyViewModel = viewModel(factory = curreneyFactory)
            SearchScreen(navController, context, searchViewModel, currencyViewModel)
        }

        composable(route = Screens.Signup.route) {
            val homeViewModel: HomeViewModel = viewModel(factory = homeFactory)
            SignupScreen(navController)
        }

        composable(route = Screens.Login.route) {
            LoginScreen(navController, context, googleSignInClient) {
                navController.navigate(Screens.Home.route)
            }
        }

        composable(route = Screens.ProductSc.route) {
            val homeViewModel: HomeViewModel = viewModel(factory = homeFactory)
            val currencyViewModel: CurrencyViewModel = viewModel(factory = curreneyFactory)
            val vendorName = it.arguments?.getString(VENDOR_NAME)
            if (vendorName != null) {
                ProductScreen(homeViewModel, currencyViewModel, navController, vendorName)
            }
        }

        composable(route = Screens.Setting.route) {
            val currencyViewModel: CurrencyViewModel = viewModel(factory = curreneyFactory)
            SettingScreen(currencyViewModel, navController)
        }

        composable(
            route = Screens.ProductDetails.route,
            arguments = listOf(navArgument("product") { type = NavType.StringType })
        ) { backStackEntry ->
            val gsonProduct = backStackEntry.arguments?.getString("product")
            val gson = Gson()
            val product = gson.fromJson(gsonProduct, Product::class.java)
            val productInfoViewModel: ProductInfoViewModel = viewModel(factory = productInfoViewModelFac)
            ProductDetails(productInfoViewModel,product = product, controller = navController, context)
        }

        composable(route = Screens.Orders.route) {
            val orderViewModel: OrdersViewModel = viewModel(factory = ordersFactory)
            OrdersScreen(context, orderViewModel, navController, networkObserver)
        }

        composable(
            route = Screens.OrderDetails.route,
            arguments = listOf(navArgument("order") { type = NavType.StringType })
        ) { backStackEntry ->
            val orderJson = backStackEntry.arguments?.getString("order")
            val gson = Gson()
            val order = gson.fromJson(orderJson, Order::class.java)
            val orderViewModel: OrdersViewModel = viewModel(factory = ordersFactory)
            OrderDetailsScreen(context, order = order, orderViewModel, controller = navController, networkObserver)
        }
    }
}
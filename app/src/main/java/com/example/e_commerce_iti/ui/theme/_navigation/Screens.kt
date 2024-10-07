package com.example.e_commerce_iti.ui.theme._navigation

import android.app.Activity
import android.content.Context

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalContext
import androidx.compose.runtime.internal.composableLambda
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
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
import com.example.e_commerce_iti.getCurrent
import com.example.e_commerce_iti.model.local.LocalDataSourceImp
import com.example.e_commerce_iti.model.local.LocalDataSourceImp.Companion.currentCurrency
import com.example.e_commerce_iti.model.local.LocalDataSourceImp.Companion.currentCurrency
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
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.Firebase
import com.google.firebase.app
import com.google.firebase.auth.auth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 *      sealed Class to manage navigation between Screens in the app
 */

sealed class Screens(val route: String) {
    object Setting : Screens(route = "setting")
    object Home : Screens(route = "home")
    object Category : Screens(route = "category")
    object Signup : Screens(route = "signUP")
    object Login :Screens(route="Login")
    object Cart : Screens(route = "cart")
    object Profile : Screens(route = "profile")
    object Favorite : Screens(route = "favorite")
    object ChangeUserData:Screens(route = "change_user_data")
    object Search : Screens(route = "search")
    object ProductSc : Screens(route = "product/{$VENDOR_NAME}"){
        fun createRoute(vendorName: String) = "product/$vendorName"
    }
    object ProductDetails : Screens(route = "product_details/{$PRODUCT_ID}") {
        fun createDetailRoute(productId: Long) = "product_details/$productId"
    }

}


@Composable
fun Navigation(networkObserver: NetworkObserver,context: Activity) {
    val navController= rememberNavController()
    val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(context.getString(R.string.web_api_key))
        .requestEmail()
        .build()

    val googleSignInClient = GoogleSignIn.getClient(context, googleSignInOptions)
    NavHost(navController = navController, startDestination = Screens.Login.route) {
    val repository: IReposiatory = ReposiatoryImpl(RemoteDataSourceImp(), LocalDataSourceImp(context.getSharedPreferences(
        LocalDataSourceImp.currentCurrency, Context.MODE_PRIVATE))
    )
    val curreneyFactory: CurrenciesViewModelFactory = CurrenciesViewModelFactory(repository)
    val cartFactory: CartViewModelFac = CartViewModelFac(repository)
    val homeFactory: HomeViewModelFactory = HomeViewModelFactory(repository)
    val couponFactory: CouponsViewModelFactory = CouponsViewModelFactory(repository)
    val cartViewModelFac= CartViewModelFac(repository)
    val changeUserDataFactory: ChangeUserDataViewModelFactory = ChangeUserDataViewModelFactory(repository)
    composable(route = Screens.Home.route) {
            val homeViewModel: HomeViewModel = viewModel(factory = homeFactory)
            val CopuonsViewModel: CouponViewModel = viewModel(factory = couponFactory)
            LaunchedEffect(Unit){
                if (Firebase.auth.currentUser?.email!=null) getCurrent(Firebase.auth.currentUser?.email!!,repository)
            }
            HomeScreen(CopuonsViewModel,homeViewModel, navController,networkObserver)
        }

        composable(route = Screens.Category.route) {
            val homeViewModel :HomeViewModel = viewModel(factory = homeFactory)
            CategoryScreen(homeViewModel,navController,networkObserver)
        }
        composable(route = Screens.Cart.route) {
            val cartViewModel: CartViewModel = viewModel(factory = cartFactory)
            CartScreen(cartViewModel,navController) }
        composable(route = Screens.Profile.route) {
            ProfileScreen(navController)
        }
        composable(route = Screens.ChangeUserData.route) {
            val changeUserDataViewModel: ChangeUserDataViewModel = viewModel(factory = changeUserDataFactory)
            ChangeUserDataScreen(viewModel = changeUserDataViewModel,navController = navController)
        }
        composable(route = Screens.Favorite.route) { FavoriteScreen(navController) }
        composable(route = Screens.Search.route) { SearchScreen(navController) }
        composable(route =Screens.Signup.route) {SignupScreen(navController,context)}
        composable(route = Screens.Login.route){LoginScreen(navController,context,googleSignInClient){
            navController.navigate(Screens.Home.route)
        } }

        // here im modifying the product route to Extract the product ID from the route
        composable(route = Screens.ProductSc.route) {
            // Create ViewModel using the factory
            val homeViewModel: HomeViewModel = viewModel(factory = homeFactory)
            val vendorName = it.arguments?.getString(VENDOR_NAME)
            if (vendorName != null) {
                ProductScreen(homeViewModel,navController, vendorName)
            }
        }
        composable(route = Screens.Setting.route) {
            val currencyViewModel: CurrencyViewModel = viewModel(factory = curreneyFactory)
            SettingScreen(currencyViewModel,navController)
        }

        composable(
            route = Screens.ProductDetails.route,
            arguments = listOf(navArgument(PRODUCT_ID) {
                type = NavType.LongType // take care of this it to mention that long will sent
            })
        ) {
            val productId = it.arguments?.getLong(PRODUCT_ID)
            if (productId != null) {
                ProductDetails(productId, navController)
            }
        }


    }

}
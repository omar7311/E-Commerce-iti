package com.example.e_commerce_iti.ui.theme._navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.e_commerce_iti.VENDOR_NAME
import com.example.e_commerce_iti.model.remote.RemoteDataSourceImp
import com.example.e_commerce_iti.model.reposiatory.IReposiatory
import com.example.e_commerce_iti.model.reposiatory.ReposiatoryImpl
import com.example.e_commerce_iti.ui.theme.cart.CartScreen
import com.example.e_commerce_iti.ui.theme.category.CategoryScreen
import com.example.e_commerce_iti.ui.theme.changeuserinfo.ChangeUserDataScreen
import com.example.e_commerce_iti.ui.theme.favorite.FavoriteScreen
import com.example.e_commerce_iti.ui.theme.home.HomeScreen
import com.example.e_commerce_iti.ui.theme.products.ProductScreen
import com.example.e_commerce_iti.ui.theme.profile.ProfileScreen
import com.example.e_commerce_iti.ui.theme.search.SearchScreen
import com.example.e_commerce_iti.ui.theme.viewmodels.cartviewmodel.CartViewModel
import com.example.e_commerce_iti.ui.theme.viewmodels.cartviewmodel.CartViewModelFac
import com.example.e_commerce_iti.ui.theme.viewmodels.changeuserdata.ChangeUserDataViewModel
import com.example.e_commerce_iti.ui.theme.viewmodels.changeuserdata.ChangeUserDataViewModelFactory
import com.example.e_commerce_iti.ui.theme.viewmodels.coupn_viewmodel.CouponViewModel
import com.example.e_commerce_iti.ui.theme.viewmodels.coupn_viewmodel.CouponsViewModelFactory
import com.example.e_commerce_iti.ui.theme.viewmodels.home_viewmodel.HomeViewModel
import com.example.e_commerce_iti.ui.theme.viewmodels.home_viewmodel.HomeViewModelFactory

/**
 *      sealed Class to manage navigation between Screens in the app
 */


val repository: IReposiatory = ReposiatoryImpl(RemoteDataSourceImp())
val cartFactory: CartViewModelFac = CartViewModelFac(repository)
val homeFactory: HomeViewModelFactory = HomeViewModelFactory(repository)
val couponFactory: CouponsViewModelFactory = CouponsViewModelFactory(repository)
val changeUserDataFactory: ChangeUserDataViewModelFactory = ChangeUserDataViewModelFactory(repository)
sealed class Screens(val route: String) {
    object Home : Screens(route = "home")
    object Category : Screens(route = "category")
    object Cart : Screens(route = "cart")
    object Profile : Screens(route = "profile")
    object ChangeUserData : Screens(route = "ChangeUserData")
    object Favorite : Screens(route = "favorite")
    object Search : Screens(route = "search")
    object ProductSc : Screens(route = "product/{$VENDOR_NAME}"){
        fun createRoute(vendorName: String) = "product/$vendorName"
    }

}


@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screens.Home.route) {

        composable(route = Screens.Home.route) {
            // Create ViewModel using the factory
            val homeViewModel: HomeViewModel = viewModel(factory = homeFactory)
            val CopuonsViewModel: CouponViewModel = viewModel(factory = couponFactory)

            HomeScreen(CopuonsViewModel,homeViewModel, navController)
        }

        composable(route = Screens.Category.route) {
            val homeViewModel :HomeViewModel = viewModel(factory = homeFactory)
            CategoryScreen(homeViewModel,navController)
        }
        composable(route = Screens.Cart.route) {
            val cartViewModel: CartViewModel = viewModel(factory = cartFactory)
            CartScreen(cartViewModel,navController) }
        composable(route = Screens.Profile.route) { ProfileScreen(navController) }
        composable(route = Screens.ChangeUserData.route) {
            val changeUserDataViewModel: ChangeUserDataViewModel = viewModel(factory = changeUserDataFactory)
            ChangeUserDataScreen(viewModel = changeUserDataViewModel,navController = navController)
        }
        composable(route = Screens.Favorite.route) { FavoriteScreen(navController) }
        composable(route = Screens.Search.route) { SearchScreen(navController) }

        // here im modifying the product route to Extract the product ID from the route
        composable(route = Screens.ProductSc.route) {
            // Create ViewModel using the factory
            val homeViewModel: HomeViewModel = viewModel(factory = homeFactory)
            val vendorName = it.arguments?.getString(VENDOR_NAME)
            if (vendorName != null) {
                ProductScreen(homeViewModel,navController, vendorName)
            }
        }

    }
}
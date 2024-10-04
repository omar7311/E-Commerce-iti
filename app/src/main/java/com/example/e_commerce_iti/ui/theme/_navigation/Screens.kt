package com.example.e_commerce_iti.ui.theme._navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.e_commerce_iti.model.remote.RemoteDataSourceImp
import com.example.e_commerce_iti.model.reposiatory.IReposiatory
import com.example.e_commerce_iti.model.reposiatory.ReposiatoryImpl
import com.example.e_commerce_iti.ui.theme.cart.CartScreen
import com.example.e_commerce_iti.ui.theme.category.CategoryScreen
import com.example.e_commerce_iti.ui.theme.favorite.FavoriteScreen
import com.example.e_commerce_iti.ui.theme.home.HomeScreen
import com.example.e_commerce_iti.ui.theme.products.ProductScreen
import com.example.e_commerce_iti.ui.theme.profile.ProfileScreen
import com.example.e_commerce_iti.ui.theme.search.SearchScreen
import com.example.e_commerce_iti.ui.theme.viewmodels.home_viewmodel.HomeViewModel
import com.example.e_commerce_iti.ui.theme.viewmodels.home_viewmodel.HomeViewModelFactory

/**
 *      sealed Class to manage navigation between Screens in the app
 */


val repository: IReposiatory = ReposiatoryImpl(RemoteDataSourceImp())
val homeFactory: HomeViewModelFactory = HomeViewModelFactory(repository)

sealed class Screens(val route: String) {
    object Home : Screens(route = "home")
    object Category : Screens(route = "category")
    object Cart : Screens(route = "cart")
    object Profile : Screens(route = "profile")
    object Favorite : Screens(route = "favorite")
    object Search : Screens(route = "search")
    object Product : Screens(route = "product/{brandId}"){
        fun createRoute(brandId: Int) = "product/$brandId"
    }

}


@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screens.Home.route) {
        composable(route = Screens.Home.route) {
            // Create ViewModel using the factory
            val homeViewModel: HomeViewModel = viewModel(factory = homeFactory)
            HomeScreen(homeViewModel, navController)
        }
        composable(route = Screens.Category.route) { CategoryScreen(navController) }
        composable(route = Screens.Cart.route) { CartScreen(navController) }
        composable(route = Screens.Profile.route) { ProfileScreen(navController) }
        composable(route = Screens.Favorite.route) { FavoriteScreen(navController) }
        composable(route = Screens.Search.route) { SearchScreen(navController) }

        // here im modifying the product route to Extract the product ID from the route
        composable(route = Screens.Product.route) {
            val productId = it.arguments?.getString("brandId")?.toIntOrNull()
            ProductScreen(navController, productId) }  // then pass it to the ProductScreen

    }
}
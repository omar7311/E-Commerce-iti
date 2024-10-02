package com.example.e_commerce_iti.ui.theme.home

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.e_commerce_iti.model.apistates.BrandsApiState
import com.example.e_commerce_iti.model.remote.RemoteDataSourceImp
import com.example.e_commerce_iti.model.reposiatory.IReposiatory
import com.example.e_commerce_iti.model.reposiatory.ReposiatoryImpl
import com.example.e_commerce_iti.ui.theme.viewmodels.HomeViewModel
import com.example.e_commerce_iti.ui.theme.viewmodels.HomeViewModelFactory

// Singleton object for repository to prevent re-instantiation
val repository: IReposiatory = ReposiatoryImpl(RemoteDataSourceImp())
val factory: HomeViewModelFactory = HomeViewModelFactory(repository)

@Composable
fun FetchingBrandData() {
    // Create ViewModel using the factory
    val homeViewModel: HomeViewModel = viewModel(factory = factory)

    // Fetch the brands from ViewModel when the Composable is launched
    LaunchedEffect(Unit) {
        homeViewModel.getBrands()
    }

    // Observe the state of the brands
    val brandsState by homeViewModel.brandStateFlow.collectAsState()

    // Handle different states of the brands
    when (brandsState) {
        is BrandsApiState.Loading -> {
            Log.i("Brands", "Loading brands data...")
        }

        is BrandsApiState.Success -> {
            Log.i("Brands", "Successfully loaded brands data.")
            val brands = (brandsState as BrandsApiState.Success).brands
            Log.i("Brands", "Brands: $brands")
            // You might want to display the brands in the UI here
        }

        is BrandsApiState.Failure -> {
            Log.e("Brands", "Failed to load brands data: ${(brandsState as BrandsApiState.Failure).msg}")
            // You might want to show an error message in the UI here
        }

        else -> {
            Log.w("Brands", "Unexpected state: $brandsState")
        }
    }
}

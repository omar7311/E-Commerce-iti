package com.example.e_commerce_iti.model.viewmodels.searchviewmodel

import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.e_commerce_iti.model.apistates.BrandsApiState
import com.example.e_commerce_iti.model.apistates.UiState
import com.example.e_commerce_iti.model.pojos.Product
import com.example.e_commerce_iti.model.remotes.dummydaya.dummyBrandData
import com.example.e_commerce_iti.model.remotes.dummydaya.products
import com.example.e_commerce_iti.model.viewmodels.FakeReposiatory
import com.example.e_commerce_iti.ui.theme.viewmodels.home_viewmodel.HomeViewModel
import com.example.e_commerce_iti.ui.theme.viewmodels.searchViewModel.SearchViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class SearchViewModelTest {

    lateinit var repo: FakeReposiatory
    lateinit var searchViewModel: SearchViewModel

    @Before
    fun setUp() {
        repo = FakeReposiatory()
        searchViewModel = SearchViewModel(repo)
    }

    @Test
    fun getProducts_assertEquality() = runTest {
        searchViewModel.getAllProduct()
        val result = searchViewModel.allProduct.value
        if (result is UiState.Success) {
            // then
            Assert.assertEquals(result.data, products)
        }
    }
    @Test
    fun getProducts_assertNotEquality() = runTest {
        searchViewModel.getAllProduct()
        val result = searchViewModel.allProduct.value
        if (result is UiState.Success) {
            // then
            Assert.assertNotEquals(result.data, listOf<Product>())
        }
    }
}
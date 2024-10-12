package com.example.e_commerce_iti.model.viewmodels.homeviewmodel

import com.example.e_commerce_iti.model.apistates.BrandsApiState
import com.example.e_commerce_iti.model.apistates.CustomCollectionStates
import com.example.e_commerce_iti.model.apistates.ProductsApiState
import com.example.e_commerce_iti.model.remotes.dummydaya.dummyBrandData
import com.example.e_commerce_iti.model.remotes.dummydaya.product1
import com.example.e_commerce_iti.model.remotes.dummydaya.product2
import com.example.e_commerce_iti.model.remotes.dummydaya.product3
import com.example.e_commerce_iti.model.remotes.dummydaya.product4
import com.example.e_commerce_iti.model.remotes.dummydaya.product5
import com.example.e_commerce_iti.model.remotes.dummydaya.product6
import com.example.e_commerce_iti.model.remotes.dummydaya.product7
import com.example.e_commerce_iti.model.remotes.dummydaya.product8
import com.example.e_commerce_iti.model.remotes.dummydaya.product9
import com.example.e_commerce_iti.model.reposiatory.IReposiatory
import com.example.e_commerce_iti.model.viewmodels.FakeReposiatory
import com.example.e_commerce_iti.ui.theme.viewmodels.home_viewmodel.HomeViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class HomeViewModelTest{


    lateinit var  repo: IReposiatory
    lateinit var  homeViewModel: HomeViewModel


    @Before
    fun setUp(){
        repo = FakeReposiatory()
        homeViewModel = HomeViewModel(repo)
    }


    // working  to test getBrands

    @Test
    fun getBrands_assertSize() = runTest{
        // when
        homeViewModel.getBrands()
        val result = homeViewModel.brandStateFlow.first()
       if(result is BrandsApiState.Success){

           // then
           assert(result.brands.size== dummyBrandData.size)

       }
    }

    @Test
    fun getBrands_assertData() = runTest{
        // when
        homeViewModel.getBrands()
        val result = homeViewModel.brandStateFlow.first()
        if(result is BrandsApiState.Success){
            Assert.assertEquals(result.brands,dummyBrandData)
        }

    }

    // test the  getProducts by vendor

    @Test
    fun getProductsByVendor_assertData() = runTest{  // assert on the data

        //when
        val testedProducts = listOf(product1, product2, product3, product4)
        homeViewModel.getProductsByVendor("Vendor1")
        val result = homeViewModel.productStateFlow.first()
        if(result is ProductsApiState.Success){
            Assert.assertEquals(result.products , testedProducts)
        }

    }

    @Test
    fun getProductsByVendor_assertSize() = runTest{  // assert on size

        //when
        val testedProducts = listOf(product5, product6, product7, product8, product9)
        homeViewModel.getProductsByVendor("Vendor2")
        val result = homeViewModel.productStateFlow.first()
        if(result is ProductsApiState.Success){
            Assert.assertEquals(result.products.size , testedProducts.size)
        }

    }


    // test getCustomCollections
    @Test
    fun getCustomCollections_assertData() = runTest{
        // when
        homeViewModel.getCustomCollections()
        val result = homeViewModel.customCollectionStateFlow.first()
        if(result is CustomCollectionStates.Success){
            // then
            Assert.assertEquals(result.customCollections , dummyBrandData)
        }
    }

}
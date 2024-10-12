package com.example.e_commerce_iti.ui.theme.viewmodels.cartviewmodel

import androidx.compose.runtime.collectAsState
import com.example.e_commerce_iti.model.apistates.UiState
import com.example.e_commerce_iti.model.reposiatory.IReposiatory
import com.example.e_commerce_iti.model.viewmodels.FakeReposiatory
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.*

import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class CartViewModelTest {
    lateinit var viewModel: CartViewModel
    @Before
    fun setUp() {
        val mockReposiatory = FakeReposiatory()
         viewModel = CartViewModel(mockReposiatory)
    }

    @Test
    fun getCartState() = runBlocking {
       launch {
               assert( viewModel.cartState.first() is UiState.Success)
           }
        viewModel.getCartDraftOrder(1001)
    }

    @Test
    fun getProduct() =runTest{
        viewModel.getCartDraftOrder(1001)
        launch {
            val carts=viewModel.cartState.first()
            assert(carts is UiState.Success)
            assert(viewModel.product.first() is UiState.Success)
            assert((viewModel.product.first() as UiState.Success).data.size!=0)
        }

    }

    @Test
    fun getTotalAmount() = runTest{
        viewModel.getCartDraftOrder(1001)
        launch {
            val carts=viewModel.cartState.first()
            assert(carts is UiState.Success)
            assert(viewModel.totalAmount.first()!=0.0)
        }
    }



    @Test
    fun getCurrentCurrency()= runTest {
        viewModel.getCurrency()
        launch {
            assert(viewModel.currentCurrency.first() is UiState.Success)
            assert((viewModel.currentCurrency.first() as UiState.Success).data.first=="EGP")
        }
    }


}
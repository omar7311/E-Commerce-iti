package com.example.e_commerce_iti.model.viewmodels.orderviewmodel

import com.example.e_commerce_iti.model.apistates.UiState
import com.example.e_commerce_iti.model.remotes.dummydaya.order2
import com.example.e_commerce_iti.model.remotes.dummydaya.order3
import com.example.e_commerce_iti.model.reposiatory.IReposiatory
import com.example.e_commerce_iti.model.viewmodels.FakeReposiatory
import com.example.e_commerce_iti.ui.theme.viewmodels.orders.OrdersViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class OrderViewModelTest {


    lateinit var repo: IReposiatory
    lateinit var orderViewModel: OrdersViewModel

    @Before
    fun setUp() {
        repo = FakeReposiatory()
        orderViewModel = OrdersViewModel(repo)
    }

    @Test
    fun getOrdersByCustomerId_assertNotEmpty() = runTest {
        // when
        orderViewModel.getOrdersByCustomerId(100002L)
        val result = orderViewModel.ordersFlowState.first()
        if (result is UiState.Success) {
            assert(result.data.isNotEmpty())
            assert(result.data.first().id == 100002L)
        }
    }

    @Test
    fun getOrdersByCustomerId_assertData() = runTest {
        // when
        val ordersToTest = listOf(order2, order3)
        orderViewModel.getOrdersByCustomerId(1000003L)
        val result = orderViewModel.ordersFlowState.first()
        if(result is UiState.Success){
            assert(result.data.size == 2)
            assert(result.data == ordersToTest)
        }
    }
}

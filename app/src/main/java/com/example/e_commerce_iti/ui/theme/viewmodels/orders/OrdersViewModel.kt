package com.example.e_commerce_iti.ui.theme.viewmodels.orders

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.e_commerce_iti.model.apistates.UiState
import com.example.e_commerce_iti.model.pojos.Order
import com.example.e_commerce_iti.model.pojos.Product
import com.example.e_commerce_iti.model.reposiatory.IReposiatory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class OrdersViewModel(val repository: IReposiatory) : ViewModel() {

    val _ordersFlowState = MutableStateFlow<UiState<List<Order>>>(UiState.Loading)
    val ordersFlowState = _ordersFlowState

    // this for single product
    val _singleProductFlow = MutableStateFlow<UiState<Product>>(UiState.Loading)
    val singleProductFlow = _singleProductFlow


    /**
     *      fun to get the orders by customer id
     */

     fun getOrdersByCustomerId(customerId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getOrdersByCustomerId(customerId)
                .collect { orders ->
                    Log.d("ViewMOdelOrders", "Orders: $orders")
                    _ordersFlowState.value = UiState.Success(orders)

                }
        }
    }

    fun getProductById(productId: Long) {
        viewModelScope.launch {
            repository.getProductById(productId)
                .catch { e ->
                    _singleProductFlow.value = UiState.Error(e.message ?: "Unknown Error")
                }
                .collect { product ->
                    _singleProductFlow.value = UiState.Success(product)

                }
        }
    }

    @SuppressLint("SuspiciousIndentation")
    suspend fun getTempProductById(productId:Long):Product{
        var product :Product?= null
          product =  repository.getTempProductById(productId)
            Log.i("ProductsFetched", "Fetched ViewModel: $product")
       return product
    }
}


class OrdersFactory(private val repository: IReposiatory) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OrdersViewModel::class.java)) {
            return OrdersViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown OrderViewModel class")
    }
}



package com.example.e_commerce_iti.ui.theme.viewmodels.orders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.e_commerce_iti.model.apistates.UiState
import com.example.e_commerce_iti.model.pojos.Order
import com.example.e_commerce_iti.model.reposiatory.IReposiatory
import com.example.e_commerce_iti.ui.theme.viewmodels.home_viewmodel.HomeViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class OrdersViewModel(val repository: IReposiatory) : ViewModel() {

    val _ordersFlowState = MutableStateFlow<UiState<List<Order>>>(UiState.Loading)
    val ordersFlowState = _ordersFlowState


    /**
     *      fun to get the orders by customer id
     */

     fun getOrdersByCustomerId(customerId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getOrdersByCustomerId(customerId)
                .collect { orders ->
                    _ordersFlowState.value = UiState.Success(orders)
                }
        }
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

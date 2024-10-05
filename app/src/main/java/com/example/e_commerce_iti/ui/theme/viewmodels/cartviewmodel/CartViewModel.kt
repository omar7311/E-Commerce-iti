package com.example.e_commerce_iti.ui.theme.viewmodels.cartviewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.e_commerce_iti.model.apistates.UiState
import com.example.e_commerce_iti.model.pojos.draftorder.DraftOrder
import com.example.e_commerce_iti.model.reposiatory.IReposiatory
import com.example.e_commerce_iti.ui.theme.viewmodels.coupn_viewmodel.CouponViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class CartViewModel(private val cartRepository: IReposiatory): ViewModel() {
    private val _cartState = MutableStateFlow<UiState<DraftOrder>>(UiState.Loading)
    val cartState: MutableStateFlow<UiState<DraftOrder>> = _cartState

    var job: Job? = null
    fun getCart() {
        job?.cancel()
        job=viewModelScope.launch(Dispatchers.IO) {
       //  val customer = cartRepository.getCustomer()
        }
    }
}

class CartViewModelFac(private val repo: IReposiatory) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(CartViewModelFac::class.java) -> {
                CartViewModelFac(repo) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}

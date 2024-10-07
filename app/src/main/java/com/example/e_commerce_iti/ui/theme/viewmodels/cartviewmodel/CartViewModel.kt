package com.example.e_commerce_iti.ui.theme.viewmodels.cartviewmodel

import android.support.v4.os.IResultReceiver._Parcel
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.e_commerce_iti.model.apistates.UiState
import com.example.e_commerce_iti.model.pojos.Product
import com.example.e_commerce_iti.model.pojos.draftorder.DraftOrder
import com.example.e_commerce_iti.model.reposiatory.IReposiatory
import com.example.e_commerce_iti.ui.theme.viewmodels.coupn_viewmodel.CouponViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class CartViewModel(private val cartRepository: IReposiatory): ViewModel() {
    private val _cartState = MutableStateFlow<UiState<DraftOrder>>(UiState.Loading)
    val cartState: StateFlow<UiState<DraftOrder>> = _cartState
    private val _product = MutableStateFlow<UiState<MutableList<Product>>>(UiState.Loading)
     val product :StateFlow<UiState<MutableList<Product>>> = _product

      fun getCartDraftOrder(id: Long){
     viewModelScope.launch {
         val cart = cartRepository.getCart(id).first()
         _cartState.value = UiState.Success(cart)
         _product.value = UiState.Loading
         val product = mutableListOf<Product>()
         for (i in cart.line_items) {
             if (i.product_id != null) {
                 product.add(cartRepository.getProductByID(i.product_id!!.toLong()).first())
             }
         }
         Log.i("CartViewModel", "Product: $product")
         _product.value = UiState.Success(product)
     }
    }

    private var _currentCurrency = MutableStateFlow<UiState<Pair<String, Float>>>(UiState.Loading)
    val currentCurrency: StateFlow<UiState<Pair<String, Float>>> = _currentCurrency

     fun updateCart(id: Long){
        val cart=_cartState.value as UiState.Success<DraftOrder>
         val dd=cart.data.line_items.find { it.id==id }
        val c= cart.data.line_items.filter {  id!=it.id }
         cart.data.line_items=c
        viewModelScope.launch {
            cartRepository.updateCart(cart.data)
            val data=(_product.value as UiState.Success<MutableList<Product>>).data.filter { it.id==dd!!.product_id }
            _product.value=UiState.Loading
             Log.i("CartViewModel", "Product: $data")
            _product.value=UiState.Success(data.toMutableList())
        }
    }

    fun getCurrency(){
        viewModelScope.launch {
            _currentCurrency.value=UiState.Success(cartRepository.getChoosedCurrency().first())
        }
    }
}

class CartViewModelFac(private val repo: IReposiatory) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(CartViewModel::class.java) -> {
                CartViewModel(repo) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}

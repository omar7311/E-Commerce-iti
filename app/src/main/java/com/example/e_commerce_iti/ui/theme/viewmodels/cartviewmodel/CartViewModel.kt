package com.example.e_commerce_iti.ui.theme.viewmodels.cartviewmodel

import android.support.v4.os.IResultReceiver._Parcel
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.e_commerce_iti.model.apistates.UiState
import com.example.e_commerce_iti.model.pojos.Product
import com.example.e_commerce_iti.model.pojos.draftorder.DraftOrder
import com.example.e_commerce_iti.model.pojos.draftorder.LineItems
import com.example.e_commerce_iti.model.reposiatory.IReposiatory
import com.example.e_commerce_iti.ui.theme.cart.roundToTwoDecimalPlaces
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
     private var _totalAmount = MutableStateFlow(0.0)
     val totalAmount: StateFlow<Double> = _totalAmount
    private var _navigateto:MutableStateFlow<Boolean> = MutableStateFlow(false)
    val navigateto:StateFlow<Boolean> = _navigateto
      fun getCartDraftOrder(id: Long){
          _cartState.value=UiState.Loading
     viewModelScope.launch {
         try {
             val f = "gg"
             val cart = cartRepository.getCart(id).first()
             _cartState.value = UiState.Success(cart)
             _product.value = UiState.Loading
             val product = mutableListOf<Product>()
             _totalAmount.value = 0.0
             for (i in cart.line_items) {
                 if (i.title != "Dummy") {
                     product.add(cartRepository.getProductByID(i.product_id!!.toLong()).first())
                     _totalAmount.value += (i.price!!.toDouble() * i.quantity!!).roundToTwoDecimalPlaces()
                 }
             }

             Log.i("CartViewModel", "Product: $product")
             _product.value = UiState.Success(product)
         } catch (e: Exception) {
            Log.i("CartViewModel", "Error fetching cart: ${e.message}")
         }
     }
    }
    fun endnav(){
        _navigateto.value=false
    }
    fun add(price:Double){
        _totalAmount.value+=price

    }
    fun gettotalValue(amount:Float,currency:String):String{
        Log.i("fffffffffffffffffffffffff", "${(_totalAmount.value * amount).roundToTwoDecimalPlaces()} ${currency}")
       return "${(_totalAmount.value * amount).roundToTwoDecimalPlaces()} ${currency}"
    }
    fun sub(price:Double){
        _totalAmount.value-=price
    }
    private var _currentCurrency = MutableStateFlow<UiState<Pair<String, Float>>>(UiState.Loading)
    val currentCurrency: StateFlow<UiState<Pair<String, Float>>> = _currentCurrency

    fun updateCart(productToRemove: Product, lineItem: LineItems) {
        viewModelScope.launch {
            val currentCartState = _cartState.value as? UiState.Success<DraftOrder>
            currentCartState?.let { cart ->
                Log.i("before update cart ", "Updated cart: beofre ${currentCartState.data.line_items.size}")

                _totalAmount.value-=(lineItem.price!!.toDouble() * lineItem.quantity!!)
                val updatedLineItems = cart.data.line_items.filterNot { it.id == lineItem.id }
                val updatedDraftOrder = cart.data.copy(line_items = updatedLineItems)
                for (i in updatedDraftOrder.line_items) {
                    if (i.quantity==0L){
                        i.quantity=1L
                    }
                }
                // Update the cart in the repository
               val t= cartRepository.updateCart(updatedDraftOrder)
                Log.i("before update cart ", "Updated cart: after ${t.first().line_items.size}")

                // Now update the product list
                val updatedProducts = (product.value as? UiState.Success<MutableList<Product>>)?.data?.toMutableList()
                updatedProducts?.remove(productToRemove)

                // Emit updated state for products
                _product.value = UiState.Success(updatedProducts ?: mutableListOf())
                _cartState.value = UiState.Success(t.first())
                Log.i("CartViewModel", "Updated product list: ${updatedProducts?.size}")
            }
        }
    }
    fun getCurrency(){
        viewModelScope.launch {
            _currentCurrency.value=UiState.Success(cartRepository.getChoosedCurrency().first())
        }
    }
    fun submit() {
        val currentCartState = _cartState.value as? UiState.Success<DraftOrder>
        viewModelScope.launch {
            cartRepository.updateCart(cart = currentCartState!!.data)
            _navigateto.value=true
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

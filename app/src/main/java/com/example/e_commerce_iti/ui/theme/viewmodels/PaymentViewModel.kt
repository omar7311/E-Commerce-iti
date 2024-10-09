package com.example.e_commerce_iti.ui.theme.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.e_commerce_iti.model.apistates.UiState
import com.example.e_commerce_iti.model.pojos.discountcode.DiscountCodeX
import com.example.e_commerce_iti.model.pojos.draftorder.AppliedDiscount
import com.example.e_commerce_iti.model.pojos.draftorder.DraftOrder
import com.example.e_commerce_iti.model.pojos.price_rules.PriceRule
import com.example.e_commerce_iti.model.pojos.price_rules.PriceRules
import com.example.e_commerce_iti.model.reposiatory.IReposiatory
import com.example.e_commerce_iti.ui.theme.cart.roundToTwoDecimalPlaces
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class PaymentViewModel(val repository: IReposiatory): ViewModel() {
    private var _discountCode = MutableStateFlow<UiState<DiscountCodeX>>(UiState.Loading)
    val discountCodeX: StateFlow<UiState<DiscountCodeX>> = _discountCode
    private var _priceRules = MutableStateFlow<UiState<PriceRule>>(UiState.Loading)
    val priceRules: StateFlow<UiState<PriceRule>> = _priceRules
    private var _cart = MutableStateFlow<UiState<DraftOrder>>(UiState.Loading)
    val cart: StateFlow<UiState<DraftOrder>> = _cart
    private var totalamount= MutableStateFlow<Double>(0.0)
    val total: StateFlow<Double> = totalamount
    val discount= MutableStateFlow<Double>(0.0)
    val price= MutableStateFlow<Double>(0.0)
    fun getCart(id:Long) {
        viewModelScope.launch {
            try {
            _cart.value = UiState.Loading
            _cart.value = UiState.Success(repository.getCart(id).first())
              for (i in (cart.value as UiState.Success<DraftOrder>).data.line_items){
                  price.value +=(i.price!!.toDouble() * i.quantity!!.toDouble()).roundToTwoDecimalPlaces()
              }
              totalamount.value=price.value
              }catch (e:Exception){
                _cart.value=UiState.Error(e.message.toString())
              }
        }
    }

    fun submitOrder(id:Long) {
        _discountCode.value=UiState.Loading
        _priceRules.value=UiState.Loading
        viewModelScope.launch {

        }
    }
    fun get_discount_details(id:String){
        viewModelScope.launch {
            try {
                _discountCode.value=UiState.Loading
                _discountCode.value=UiState.Success(repository.getDiscountCode(id).first())
                get_price_rules((_discountCode.value as UiState.Success<DiscountCodeX>).data.price_rule_id)
            }catch (e:Exception){
                _discountCode.value=UiState.Error(e.message.toString())
            }
        }
    }
    fun get_price_rules(discountCodeX: Long){
        viewModelScope.launch {
            try {
                _priceRules.value = UiState.Loading
                _priceRules.value = UiState.Success(repository.getPrice_rules(discountCodeX).first())
            }catch (e:Exception){
                _priceRules.value=UiState.Error(e.message.toString())
            }
        }
    }

    fun apply_discount(priceRule: PriceRule) {
        val cart = (cart.value as UiState.Success<DraftOrder>).data
        _cart.value = UiState.Loading
        viewModelScope.launch {
            try {

                if (priceRule.value_type == "fixed_amount") {
                 totalamount.value=(price.value-priceRule.value.toDouble()).roundToTwoDecimalPlaces()
                } else {
                    totalamount.value=(price.value*(100-priceRule.value.toDouble())/100).roundToTwoDecimalPlaces()
                }
                cart.applied_discount = AppliedDiscount(
                    amount = priceRule.value,
                    description = "Discount Code ${(_discountCode.value as UiState.Success<DiscountCodeX>).data.code.toString()}",
                    title = priceRule.title,
                    value = priceRule.value,
                    value_type = priceRule.value_type
                )
            } catch (e: Exception) {
                _priceRules.value = UiState.Error(e.message.toString())
            }
        }
    }
}
class PaymentViewModelFactory(val repository: IReposiatory): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PaymentViewModel(repository) as T
    }
}
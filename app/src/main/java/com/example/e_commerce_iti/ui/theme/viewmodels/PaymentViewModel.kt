package com.example.e_commerce_iti.ui.theme.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.e_commerce_iti.currentUser
import com.example.e_commerce_iti.model.apistates.UiState
import com.example.e_commerce_iti.model.pojos.discountcode.DiscountCodeX
import com.example.e_commerce_iti.model.pojos.draftorder.AppliedDiscount
import com.example.e_commerce_iti.model.pojos.draftorder.BillingAddress
import com.example.e_commerce_iti.model.pojos.draftorder.DraftOrder
import com.example.e_commerce_iti.model.pojos.draftorder.ShippingAddress
import com.example.e_commerce_iti.model.pojos.price_rules.PriceRule
import com.example.e_commerce_iti.model.pojos.price_rules.PriceRules
import com.example.e_commerce_iti.model.reposiatory.IReposiatory
import com.example.e_commerce_iti.ui.theme.cart.roundToTwoDecimalPlaces
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.util.Calendar

class PaymentViewModel(val repository: IReposiatory): ViewModel() {
    val currency= MutableStateFlow<Pair<String,Float>>(Pair("USD",1.0F))
    val address= MutableStateFlow<String>("")
    val _oderstate= MutableStateFlow<UiState<Int>>(UiState.Loading)
    val oderstate: StateFlow<UiState<Int>> = _oderstate
    private var _discountCode = MutableStateFlow<UiState<DiscountCodeX>>(UiState.Loading)
    val discountCodeX: StateFlow<UiState<DiscountCodeX>> = _discountCode
    private var _priceRules = MutableStateFlow<UiState<PriceRule>>(UiState.Loading)
    val priceRules: StateFlow<UiState<PriceRule>> = _priceRules
    private var _cart = MutableStateFlow<UiState<DraftOrder>>(UiState.Loading)
    val cart: StateFlow<UiState<DraftOrder>> = _cart
    private var totalamount= MutableStateFlow<Double>(0.0)
    val total: StateFlow<Double> = totalamount
    var tax= MutableStateFlow<Double>(0.0)
    var discountvale= MutableStateFlow<Double>(0.0)
    val discountCode= MutableStateFlow<String>("")
    val paymentMethod= MutableStateFlow("")

    val discount= MutableStateFlow<Double>(0.0)
    val price= MutableStateFlow<Double>(0.0)
    suspend fun getusercurrency(){
        viewModelScope.launch {
            try {
                currency.value = repository.getChoosedCurrency().first()
            } catch (e: Exception) {
                 currency.value=Pair("USD",1.0F)
            }
        }
    }
    fun getCart(id:Long) {
        viewModelScope.launch {
            try {
             _cart.value = UiState.Loading
             _cart.value = UiState.Success(repository.getCart(id).first())
             val ct=(_cart.value   as UiState.Success<DraftOrder>).data
             discount.value=ct.applied_discount?.value?.toDouble() ?:0.0
             ct.line_items.forEach {
                 if (it.title!="Dummy") {
                     it.tax_lines?.forEach {
                         tax.value += it.price?.toDouble() ?: 0.0
                     }
                 }
             }
             totalamount.value=ct.total_price?.toDouble() ?:0.0
              }catch (e:Exception){
                discount.value=0.0
                totalamount.value=(_cart.value as UiState.Success<DraftOrder>).data.total_price?.toDouble() ?:0.0
                _cart.value=UiState.Error(e.message.toString())
              }
        }
    }
    val cvv= MutableStateFlow<String>("")
    val cardNumber= MutableStateFlow<Int>(-1)
    val expiryMonth= MutableStateFlow<Int>(-1)
    val expiryYear= MutableStateFlow<String>("")

    fun submitOrder(id:Long) {
        _oderstate.value=UiState.Loading

        viewModelScope.launch {
            var message=""
            try {
               val e= (_cart.value as UiState.Success<DraftOrder>).data
                if (address.value.isBlank()||address.value=="N/A"){
                    message+="Address is required \n"
                }
                if (paymentMethod.value.isBlank()){
                    message+="Payment method is required \n"
                }
                if(paymentMethod.value=="paid"&&isValidCreditCard( CreditCard(cardNumber.value.toString(),expiryMonth.value,expiryYear.value.toInt(),cvv.value))){
                     message+= "Wrong Card Info"
                }

                if (message.isNotBlank()){
                    _oderstate.value=UiState.Error(message)
                    return@launch
                }
            e.email= currentUser?.email
            repository.compeleteDraftOrder(e)
            _oderstate.value=UiState.Success(1)
            }catch (e:Exception){
                Log.e("create odere eriirdssad",e.message.toString())
             _oderstate.value=UiState.Error(e.message.toString())
            }

        }
    }
    fun get_discount_details(id:String){
        viewModelScope.launch {
            try {
                _discountCode.value=UiState.Loading
                _discountCode.value=UiState.Success(repository.getDiscountCode(id).first())
                get_price_rules((_discountCode.value as UiState.Success<DiscountCodeX>).data.price_rule_id)

            }catch (e:Exception){
                discount.value=0.0
                totalamount.value=(_cart.value as UiState.Success<DraftOrder>).data.total_price?.toDouble() ?:0.0
                _discountCode.value=UiState.Error(e.message.toString())
            }
        }
    }
    fun get_price_rules(discountCodeX: Long){
        viewModelScope.launch {
            try {
                _priceRules.value = UiState.Loading
                _priceRules.value = UiState.Success(repository.getPrice_rules(discountCodeX).first())
                apply_discount((_priceRules.value as UiState.Success<PriceRule>).data)
            }catch (e:Exception){
                discount.value=0.0
                totalamount.value=(_cart.value as UiState.Success<DraftOrder>).data.total_price?.toDouble() ?:0.0
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
                 totalamount.value-=priceRule.value.toDouble().roundToTwoDecimalPlaces()
                } else {
                    val t=(100.0+priceRule.value.toDouble())/100.0
                    discount.value=(totalamount.value* (1.0-t))
                    totalamount.value = (totalamount.value*(t)).roundToTwoDecimalPlaces()
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
                discount.value=0.0
                totalamount.value=(_cart.value as UiState.Success<DraftOrder>).data.total_price?.toDouble() ?:0.0
            }
        }
    }
}
class PaymentViewModelFactory(val repository: IReposiatory): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PaymentViewModel(repository) as T
    }
}



fun isValidCreditCard(creditCard: CreditCard): Boolean {
    return isValidCardNumber(creditCard.cardNumber) &&
            isValidExpiryDate(creditCard.expiryMonth, creditCard.expiryYear) &&
            isValidCvv(creditCard.cvv)
}

fun isValidCardNumber(cardNumber: String): Boolean {
    // Check if the card number is all digits and valid by Luhn's algorithm
    if (!cardNumber.matches(Regex("\\d{13,19}"))) {
        return false
    }

    // Implement Luhn algorithm to check if the card number is valid
    return isLuhnValid(cardNumber)
}

fun isLuhnValid(cardNumber: String): Boolean {
    var sum = 0
    var alternate = false
    for (i in cardNumber.length - 1 downTo 0) {
        var n = Character.getNumericValue(cardNumber[i])
        if (alternate) {
            n *= 2
            if (n > 9) {
                n -= 9
            }
        }
        sum += n
        alternate = !alternate
    }
    return sum % 10 == 0
}

fun isValidExpiryDate(month: Int, year: Int): Boolean {
    // Check if month is between 1 and 12
    if (month !in 1..12) {
        return false
    }

    // Get current month and year
    val calendar = Calendar.getInstance()
    val currentYear = calendar.get(Calendar.YEAR)
    val currentMonth = calendar.get(Calendar.MONTH) + 1 // Calendar months are zero-based

    // Check if the card is not expired
    return if (year > currentYear || (year == currentYear && month >= currentMonth)) {
        true
    } else {
        false
    }
}

fun isValidCvv(cvv: String): Boolean {
    // CVV should be numeric and either 3 digits (Visa/MasterCard) or 4 digits (AmEx)
    return cvv.matches(Regex("\\d{3,4}"))
}
data class CreditCard(
    val cardNumber: String,
    val expiryMonth: Int,
    val expiryYear: Int,
    val cvv: String
)

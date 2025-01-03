package com.example.e_commerce_iti.ui.theme.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.e_commerce_iti.CurrentUser
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
import com.example.e_commerce_iti.ui.theme._navigation.shippingAddress
import com.example.e_commerce_iti.ui.theme.cart.roundToTwoDecimalPlaces
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.lang.Math.floor
import java.util.Calendar
import kotlin.math.abs
import kotlin.math.round

class PaymentViewModel(val repository: IReposiatory): ViewModel() {
    val currency= MutableStateFlow<Pair<String,Float>>(Pair("USD",1.0F))
    val address= MutableStateFlow<String>("")
    var _oderstate= MutableStateFlow<UiState<String>>(UiState.Loading)
    val oderstate: StateFlow<UiState<String>> = _oderstate
    private var _discountCode = MutableStateFlow<UiState<DiscountCodeX>>(UiState.Loading)
    val discountCode: StateFlow<UiState<DiscountCodeX>> = _discountCode
    private var _priceRules = MutableStateFlow<UiState<PriceRule>>(UiState.Non)
    val priceRules: StateFlow<UiState<PriceRule>> = _priceRules
    private var _cart = MutableStateFlow<UiState<DraftOrder>>(UiState.Loading)
    val cart: StateFlow<UiState<DraftOrder>> = _cart
    private var totalamount= MutableStateFlow<Double>(0.0)
    val total: StateFlow<Double> = totalamount
    var tax= MutableStateFlow<Double>(0.0)
    var appliedDiscount:AppliedDiscount?=null
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
        _cart.value = UiState.Loading
        viewModelScope.launch {
            try {
                _cart.value = UiState.Success(repository.getCart(id).first())
                val ct=(_cart.value   as UiState.Success<DraftOrder>).data
                ct.line_items.forEach {
                    if (it.title!="Dummy") {
                        it.tax_lines?.forEach {
                            tax.value += it.price?.toDouble() ?: 0.0
                        }
                    }
                }
                totalamount.value=ct.total_price?.toDouble() ?:0.0
                totalamount.value-=discount.value
            }catch (e:Exception){
                totalamount.value=(_cart.value as UiState.Success<DraftOrder>).data.total_price?.toDouble() ?:0.0
                _cart.value=UiState.Error(e.message.toString())
            }
        }
    }
    val cvv= MutableStateFlow<String>("")
    val cardNumber= MutableStateFlow("")
    val expiryMonth= MutableStateFlow("")
    val expiryYear= MutableStateFlow<String>("")
    suspend fun submitOrder(place:ShippingAddress) {
        var message = ""
        if(place.address1!=""){
            address.value=place.address1!!
        }
        if (address.value.isBlank() || address.value == "N/A") {
            throw Exception("Address is required")
        }
        if (paymentMethod.value.isBlank()) {
            throw Exception("Payment Method is required")
        }
        if (paymentMethod.value == "paid" &&cardNumber.value.length<16){
            throw Exception("card number error")
        }
        if (paymentMethod.value == "paid" &&cvv.value.length<3){
            throw Exception("cvv number error")
        }
        if (paymentMethod.value == "paid" && !isExpiryDateValid(expiryMonth.value,expiryYear.value)){
            throw Exception("card date expierd")
            }
            if (paymentMethod.value == "paid" && (isValidCreditCard(
                CreditCard(
                    cardNumber.value.toString(),
                    expiryMonth.value.toInt(), expiryYear.value.toInt(), cvv.value)
            ))
        ) {
            message += "Wrong Card Info"
        }
        if (totalamount.value>1000&&paymentMethod.value!="paid"){
            message+="this total amount cannot pay in delivery"
        }
        if (message.isNotBlank()) {
            throw Exception(message)
        }
        if (shippingAddress == null) {
            throw Exception("Shipping Address is required")
        }
        val e = (_cart.value as UiState.Success<DraftOrder>).data
        e.invoice_sent_at  = (currentUser.value!!.email)
        e.billing_address = BillingAddress(address.value, city = shippingAddress!!.address1)
        e.shipping_address = shippingAddress
        e.applied_discount=appliedDiscount
        repository.compeleteDraftOrder(e)
        shippingAddress = null
        _oderstate.value = UiState.Success("payment Sucessfully")

    }
    fun get_discount_details(id:String){
        viewModelScope.launch {
            try {
                _discountCode.value=UiState.Loading
                _discountCode.value=UiState.Success(repository.getDiscountCode(id).first())
                get_price_rules((_discountCode.value as UiState.Success<DiscountCodeX>).data.price_rule_id,id)

            }catch (e:Exception){

                totalamount.value=(_cart.value as UiState.Success<DraftOrder>).data.total_price?.toDouble() ?:0.0
                _discountCode.value=UiState.Error(e.message.toString())
            }
        }
    }
    fun get_price_rules(discountCodeX: Long,id:String){
        viewModelScope.launch {
            try {
                _priceRules.value = UiState.Loading
                _priceRules.value = UiState.Success(repository.getPrice_rules(discountCodeX).first())
                apply_discount((_priceRules.value as UiState.Success<PriceRule>).data,id)
            }catch (e:Exception){

                totalamount.value=(_cart.value as UiState.Success<DraftOrder>).data.total_price?.toDouble() ?:0.0
                _priceRules.value=UiState.Error(e.message.toString())
            }
        }
    }
    fun calculateDiscountAmount(orderSubtotal: Double, discountValue: Double, isPercentage: Boolean): Double {
        return if (isPercentage) {
            orderSubtotal * (discountValue / 100)  // Calculate percentage
        } else {
            discountValue  // Use the fixed amount directly
        }
    }
    fun apply_discount(priceRule: PriceRule,id:String) {
        val cart = (cart.value as UiState.Success<DraftOrder>).data
        _cart.value = UiState.Loading
        viewModelScope.launch {
            try {
                val discountAmount = floor(calculateDiscountAmount(cart.subtotal_price!!.toDouble(), priceRule.value.toDouble(), priceRule.value_type == "percentage"))

                if (priceRule.value_type == "fixed_amount") {
                    totalamount.value-=priceRule.value.toDouble().roundToTwoDecimalPlaces()
                    discount.value=discountAmount
                } else {
                    val t=(100.0+priceRule.value.toDouble())/100.0
                    discount.value=(totalamount.value* (1.0-t))
                    totalamount.value = (totalamount.value*(t)).roundToTwoDecimalPlaces()
                }
                appliedDiscount = AppliedDiscount(
                    amount = round(discountAmount),  // Make sure discountAmount is valid and properly converted
                    description = id,
                    value = abs(priceRule.value.toDouble()),
                    value_type = priceRule.value_type
                )
                cart.applied_discount=appliedDiscount
                _cart.value = UiState.Success(cart)
            } catch (e: Exception) {
                _priceRules.value = UiState.Error(e.message.toString())
                totalamount.value=(_cart.value as UiState.Success<DraftOrder>).data.total_price?.toDouble() ?:0.0
                (_cart.value as UiState.Success<DraftOrder>).data.applied_discount=null
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

fun isExpiryDateValid(month: String, year: String): Boolean {
    val currentYear = Calendar.getInstance().get(Calendar.YEAR) % 100
    val currentMonth = Calendar.getInstance().get(Calendar.MONTH) + 1

    val expiryMonth = month.toIntOrNull() ?: return false
    val expiryYear = year.toIntOrNull() ?: return false

    if (expiryYear < currentYear) return false
    if (expiryYear == currentYear && expiryMonth < currentMonth) return false
    if (expiryMonth !in 1..12) return false

    return true
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



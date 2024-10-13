package com.example.e_commerce_iti.ui.theme.viewmodels.currencyviewmodel

import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.e_commerce_iti.model.apistates.UiState
import com.example.e_commerce_iti.model.pojos.currenyex.CurrencyExc
import com.example.e_commerce_iti.model.pojos.customer.CustomerX
import com.example.e_commerce_iti.model.pojos.updatecustomer.UAddresse
import com.example.e_commerce_iti.model.pojos.updatecustomer.UCustomer
import com.example.e_commerce_iti.model.pojos.updatecustomer.UpdateCustomer
import com.example.e_commerce_iti.model.reposiatory.IReposiatory
import com.example.e_commerce_iti.ui.theme.viewmodels.coupn_viewmodel.CouponViewModel
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

class CurrencyViewModel(private val repository: IReposiatory) : ViewModel() {
    private val _userStateData= MutableStateFlow<UiState<CustomerX>>(UiState.Non)
    val userStateData: MutableStateFlow<UiState<CustomerX>> =_userStateData
    private val _currencyStateFlow = MutableStateFlow<UiState<Pair<String, Float>>>(UiState.Non)
    val currencyStateFlow: StateFlow<UiState<Pair<String, Float>>> = _currencyStateFlow
    fun changeCurrency(currency: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val data = repository.getCurrencyFromLocal(currency).firstOrNull()
            if (data == null) {
                _currencyStateFlow.value =UiState.Error("No data available")
            } else {
                _currencyStateFlow.value = UiState.Success(data)
            }
    }
    }
    fun getCurrency() {
        viewModelScope.launch(Dispatchers.IO) {
            val data = repository.getChoosedCurrency().firstOrNull()
            if (data == null) {
                _currencyStateFlow.value =UiState.Error("No data available")
            } else {
                _currencyStateFlow.value = UiState.Success(data)
            }
        }
    }
    var job: Job?=null
    fun getCustomerData(email:String){
        Log.e("sadsadadadsadsadsadsdasdasd","$email")
        job?.cancel()
       // userStateData.value=UiState.Loading
        job=viewModelScope.launch(Dispatchers.IO) {
            val customer= repository.getCustomer(email).first()
            _userStateData.value=UiState.Success(customer)
        }
    }
    fun updateCustomerData(currency: String){
        val customer=(userStateData.value as UiState.Success<CustomerX>).data
        userStateData.value=UiState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            val uCustomer= UCustomer(id = customer.id,email = customer.email,first_name = customer.first_name, last_name =customer.last_name, addresses = listOf(
                UAddresse(address1 = customer.addresses?.get(0)?.address1)
            ), phone = customer.phone)
            val gson= Gson().toJson(UpdateCustomer(uCustomer))
            Log.e("555555555555555555555555555555555",gson)
            val data=repository.updateCustomer(customer.id!!,gson).first()
            Log.e("555555555555555555555555555555555",data.customer.toString())
            _userStateData.value=UiState.Success(data.customer!!)
        }
    }
}
class CurrenciesViewModelFactory(private val repository: IReposiatory) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(CurrencyViewModel::class.java) -> {
                CurrencyViewModel(repository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}
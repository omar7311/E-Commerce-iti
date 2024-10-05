package com.example.e_commerce_iti.ui.theme.viewmodels.currencyviewmodel

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.e_commerce_iti.model.apistates.UiState
import com.example.e_commerce_iti.model.pojos.currenyex.CurrencyExc
import com.example.e_commerce_iti.model.pojos.customer.CustomerX
import com.example.e_commerce_iti.model.reposiatory.IReposiatory
import com.example.e_commerce_iti.ui.theme.viewmodels.coupn_viewmodel.CouponViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

class CurrencyViewModel(private val repository: IReposiatory) : ViewModel() {
    private val _userStateData= MutableStateFlow<UiState<CustomerX>>(UiState.Loading)
    val userStateData: MutableStateFlow<UiState<CustomerX>> =_userStateData
    private val _currencyStateFlow = MutableStateFlow<UiState<CurrencyExc>>(UiState.Loading)
    val currencyStateFlow: MutableStateFlow<UiState<CurrencyExc>> = _currencyStateFlow
     fun getCurrency(currency: String) {
         viewModelScope.launch(Dispatchers.IO) {
             val data = repository.getCurrency(currency).firstOrNull()
             if (data == null) {
                 _currencyStateFlow.value =UiState.Error("No data available")
             } else {
                 _currencyStateFlow.value = UiState.Success(data)
             }
         }
    }
    var job: Job?=null
    fun getCustomerData(email:String){
        job?.cancel()
        userStateData.value=UiState.Loading
        job=viewModelScope.launch(Dispatchers.IO) {
            val customer= repository.getCustomer(email).first()
            _userStateData.value=UiState.Success(customer)
        }
    }

}
class CurrenciesViewModelFac(private val repository: IReposiatory) : ViewModelProvider.Factory {
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
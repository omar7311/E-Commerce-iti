package com.example.e_commerce_iti.ui.theme.viewmodels.changeuserdata

import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.e_commerce_iti.model.apistates.UiState
import com.example.e_commerce_iti.model.pojos.customer.Addresse
import com.example.e_commerce_iti.model.pojos.customer.Customer
import com.example.e_commerce_iti.model.pojos.customer.CustomerX
import com.example.e_commerce_iti.model.pojos.updatecustomer.UAddresse
import com.example.e_commerce_iti.model.pojos.updatecustomer.UCustomer
import com.example.e_commerce_iti.model.pojos.updatecustomer.UpdateCustomer
import com.example.e_commerce_iti.model.reposiatory.IReposiatory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class ChangeUserDataViewModel(val repository: IReposiatory):ViewModel() {
    private val _userStateData= MutableStateFlow<UiState<CustomerX>>(UiState.Loading)
    val userStateData: MutableStateFlow<UiState<CustomerX>> =_userStateData
    var job: Job?=null
    fun getCustomerData(email:String){
        Log.d("555555555555555555555555555555", "getCustomerData: $email")
        job?.cancel()
        userStateData.value=UiState.Loading
        job=viewModelScope.launch(Dispatchers.IO) {
           val customer= repository.getCustomer(email).first()
            Log.d("asdasdasdasdsadasdsadsd", "getCustomerData: $customer")
           _userStateData.value=UiState.Success(customer)
        }
    }
    fun updateCustomerData(fname:String,lname:String,address:String,phone:String){
        val customer=(userStateData.value as UiState.Success<CustomerX>).data
        userStateData.value=UiState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            val uCustomer=UCustomer(email = customer.email,first_name = fname, last_name = lname, addresses = listOf(
                UAddresse(address1 = address)
            ), phone = phone)
            val data=repository.updateCustomer(customer.id!!,uCustomer).first()
            Log.d("99999999999999999999999", "updateCustomerData: $data")
            _userStateData.value=UiState.Success(data.customer!!)
        }
    }
}
class ChangeUserDataViewModelFactory(val repository: IReposiatory): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ChangeUserDataViewModel(repository) as T
    }
}
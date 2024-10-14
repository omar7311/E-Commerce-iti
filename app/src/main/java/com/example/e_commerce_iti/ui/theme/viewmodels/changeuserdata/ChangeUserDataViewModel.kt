package com.example.e_commerce_iti.ui.theme.viewmodels.changeuserdata

import android.util.Log
import android.view.View
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
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
import com.google.gson.Gson
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
        job?.cancel()
       // userStateData.value=UiState.Loading
        job=viewModelScope.launch(Dispatchers.IO) {
           val customer= repository.getCustomer(email).first()
            println(customer)
           _userStateData.value=UiState.Success(customer)
        }
    }
    fun updateCustomerData(fname:String,lname:String,address:String,phone:String){
        val customer=(userStateData.value as UiState.Success<CustomerX>).data
        userStateData.value=UiState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            val uCustomer=UCustomer(id = customer.id,email = customer.email,first_name = fname, last_name = lname, addresses = listOf(
                UAddresse(address1 = address)
            ), phone = phone)

            val gson= Gson().toJson(UpdateCustomer(uCustomer))
                        val data=repository.updateCustomer(customer.id!!,gson).first()
            _userStateData.value=UiState.Success(data.customer!!)
        }
    }
}
class ChangeUserDataViewModelFactory(val repository: IReposiatory): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ChangeUserDataViewModel(repository) as T
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownMenuExample() {
    // State to manage whether the dropdown is expanded or not
    var expanded by remember { mutableStateOf(false) }
    // State to track the selected item
    var selectedOption by remember { mutableStateOf("Select an option") }

    // List of options for the dropdown
    val options = listOf("Option 1", "Option 2", "Option 3")

    // ExposedDropdownMenuBox for creating the dropdown
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded } // Toggle dropdown visibility
    ) {
        // Text field or box that triggers the dropdown
        TextField(
            value = selectedOption,
            onValueChange = { },
            readOnly = true, // Making it read-only
            label = { Text("Dropdown") },
            modifier = Modifier
                .menuAnchor() // Necessary to anchor the dropdown
                .fillMaxWidth(),
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) // Dropdown icon
            }
        )

        // Dropdown menu to display options
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false } // Close the dropdown on dismiss
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        selectedOption = option // Update the selected option
                        expanded = false // Close the dropdown after selection
                    }
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewDropdownMenuExample() {
    DropdownMenuExample()
}
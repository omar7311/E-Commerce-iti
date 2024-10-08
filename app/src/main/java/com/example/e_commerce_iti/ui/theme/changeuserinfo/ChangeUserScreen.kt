package com.example.e_commerce_iti.ui.theme.changeuserinfo

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.e_commerce_iti.currentUser
import com.example.e_commerce_iti.model.apistates.UiState
import com.example.e_commerce_iti.model.local.LocalDataSourceImp
import com.example.e_commerce_iti.model.local.LocalDataSourceImp.Companion.currencies
import com.example.e_commerce_iti.model.pojos.customer.Customer
import com.example.e_commerce_iti.model.pojos.customer.CustomerX
import com.example.e_commerce_iti.model.remote.RemoteDataSourceImp
import com.example.e_commerce_iti.model.reposiatory.ReposiatoryImpl
import com.example.e_commerce_iti.ui.theme.ECommerceITITheme
import com.example.e_commerce_iti.ui.theme._navigation.Screens
import com.example.e_commerce_iti.ui.theme.cart.Carts
import com.example.e_commerce_iti.ui.theme.viewmodels.changeuserdata.ChangeUserDataViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

@Composable
fun ChangeUserDataScreen(viewModel: ChangeUserDataViewModel, navController: NavController) {
    viewModel.getCustomerData(Firebase.auth.currentUser!!.email!!)
    ChangeUserDataScreenContent(viewModel)
}

@Composable
fun ChangeUserDataScreenContent(viewModel: ChangeUserDataViewModel) {
    val state = viewModel.userStateData.collectAsState()
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.8f)
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceAround
    ) {
        when (val uiState = state.value) {
            is UiState.Success -> {
                val data = uiState.data
                currentUser!!.email=data.email!!
                currentUser!!.name=data.first_name!!
                currentUser!!.phone=data.phone!!
                currentUser!!.id=data.id!!
                val fname = rememberSaveable { mutableStateOf(data.first_name ?: "user") }
                val lname = rememberSaveable { mutableStateOf(data.last_name ?: "user") }
                val address = rememberSaveable {
                    mutableStateOf(data.addresses?.firstOrNull()?.address1 ?: "N/A")
                }
                val phone = rememberSaveable { mutableStateOf(data.phone ?: "N/A") }

                ChangeUserDataScreenItem("First Name", fname)
                ChangeUserDataScreenItem("Last Name", lname)
                ChangeUserDataScreenItem("Address", address)
                ChangeUserDataScreenItem("Phone", phone)

                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        if (isValidE164PhoneNumber(phone.value)) {
                            viewModel.updateCustomerData(
                                fname.value,
                                lname.value,
                                address.value,
                                phone.value
                            )
                        } else {
                            Toast.makeText(context, "Invalid Phone Number", Toast.LENGTH_SHORT).show()
                        }
                    }
                ) {
                    Text(text = "Save")
                }
            }
            is UiState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .align(Alignment.CenterHorizontally)
                ) {
                    CircularProgressIndicator()
                }
            }
            else -> {
                // Handle other states if necessary
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChangeUserDataScreenItem(label: String, state: MutableState<String>) {
    TextField(
        value = state.value,
        onValueChange = { newText -> state.value = newText },
        label = { Text(text = label) },
        modifier = Modifier.fillMaxWidth()
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun  ChangeUserData() {
    ECommerceITITheme {
        val context= LocalContext.current
        val navController= NavController(context)
        val viewModel= ChangeUserDataViewModel(repository = ReposiatoryImpl(RemoteDataSourceImp(),
            LocalDataSourceImp(context.getSharedPreferences(currencies, Context.MODE_PRIVATE))
        ))
        ChangeUserDataScreen(viewModel,navController)
    }
}
fun isValidE164PhoneNumber(phone: String): Boolean {
    // Define the regex for valid E.164 phone numbers
    val regex = Regex("^\\+\\d{1,15}$")
    return regex.matches(phone)
}

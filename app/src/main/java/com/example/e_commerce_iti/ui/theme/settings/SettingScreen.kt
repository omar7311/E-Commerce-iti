package com.example.e_commerce_iti.ui.theme.settings

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.ui.Alignment

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.e_commerce_iti.R
import com.example.e_commerce_iti.model.apistates.UiState
import com.example.e_commerce_iti.model.local.LocalDataSourceImp
import com.example.e_commerce_iti.model.local.LocalDataSourceImp.Companion.currentCurrency
import com.example.e_commerce_iti.model.pojos.customer.CustomerX
import com.example.e_commerce_iti.model.remote.RemoteDataSourceImp
import com.example.e_commerce_iti.model.reposiatory.ReposiatoryImpl
import com.example.e_commerce_iti.ui.theme.ECommerceITITheme
import com.example.e_commerce_iti.ui.theme.viewmodels.currencyviewmodel.CurrencyViewModel
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

@Composable
fun SettingScreen(viewModel: CurrencyViewModel) {
    viewModel.getCustomerData("amgedtamer123456789@gmail.com")
    val state=viewModel.userStateData.collectAsState()
    Column(modifier = Modifier.fillMaxWidth().fillMaxHeight(.8f).padding( 16.dp), verticalArrangement = Arrangement.SpaceAround){
       if (state.value is UiState.Success<CustomerX>) {
        Text(text = "Hello  ${(state.value as UiState.Success<CustomerX>).data.first_name}")
        ItemsSettingScreen("Address")
        Currencies(viewModel,(state.value as UiState.Success<CustomerX>).data)
        ItemsSettingScreen("Contact us")
        ItemsSettingScreen("About us")
    Button(onClick = {} , modifier = Modifier.fillMaxWidth()) { Text(text = "Logout") }
    }else{
        CircularProgressIndicator()
    }
    }
}
@Composable
fun ItemsSettingScreen(text:String){
    Card(modifier = Modifier.height(50.dp)) {
            Row(modifier = Modifier.fillMaxWidth().height(50.dp) , horizontalArrangement = Arrangement.SpaceBetween) {
                Column(modifier = Modifier.fillMaxHeight().padding(start = 10.dp),verticalArrangement = Arrangement.Center
                    , horizontalAlignment = Alignment.CenterHorizontally, ) {
                Text(text = text)
            }
                Column(modifier = Modifier.fillMaxHeight().padding(start = 10.dp),verticalArrangement = Arrangement.Center
                    , horizontalAlignment = Alignment.CenterHorizontally, ) {
                    Image(painter = painterResource(id = R.drawable.baseline_arrow_forward_ios_24), contentDescription = null, modifier = Modifier.padding(end = 10.dp).clickable {  },)
                }
        }
    }
}
@Composable
fun Currencies(viewModel: CurrencyViewModel,customerX: CustomerX){
    Card(modifier = Modifier.height(50.dp)) {
        Row(modifier = Modifier.fillMaxWidth().height(50.dp) , horizontalArrangement = Arrangement.SpaceBetween) {
                 CurrencyChips("USD",customerX.currency!!,{viewModel.getCurrency("USD")})
                 CurrencyChips("EUR",customerX.currency!!,{viewModel.getCurrency("EUR")})
                 CurrencyChips("EGP",customerX.currency!!,{viewModel.getCurrency("EGP")})
                 CurrencyChips("SAR",customerX.currency!!,{viewModel.getCurrency("SAR")})
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrencyChips(name: String,currency:String,state:()->Unit) {
    var isChecked by remember { mutableStateOf(currency==name) }
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(start = 10.dp, end = 10.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        FilterChip(
            selected = isChecked,
            onClick = {
                isChecked = !isChecked
                state()
            },
            label = { Text(name) }
        )
    }
}
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SettingScreenPreview() {
    ECommerceITITheme {
        val context= LocalContext.current
        SettingScreen(viewModel = CurrencyViewModel(repository = ReposiatoryImpl(RemoteDataSourceImp(), LocalDataSourceImp(context.getSharedPreferences(currentCurrency, Context.MODE_PRIVATE)))))
    }
}

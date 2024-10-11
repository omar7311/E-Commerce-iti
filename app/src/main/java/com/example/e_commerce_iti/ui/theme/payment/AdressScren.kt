package com.example.e_commerce_iti.ui.theme.payment

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.e_commerce_iti.model.pojos.draftorder.ShippingAddress
import com.example.e_commerce_iti.ui.theme._navigation.shippingAddress
import com.example.e_commerce_iti.ui.theme.viewmodels.PaymentViewModel

@Composable
fun AddressScreen(navController: NavController) {
    // Sample data for cities, governorates, and regions
    val list1= egyptGovernoratesAndAreas.map { it.key }
    val selectedOption = remember { mutableStateOf(egyptianCities[0]) }
    val selectedOption2=remember { mutableStateOf(egyptGovernoratesAndAreas[selectedOption.value]!![0]) }
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Row(Modifier.fillMaxWidth()) {
            Text(
                "Delivary Address",
                fontSize = MaterialTheme.typography.headlineMedium.fontSize
            )
        }
        Spacer(modifier = Modifier.height(50.dp))

        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {

            DropdownMenuExample(text = "City", list1, selectedOption)
            selectedOption2.value = egyptGovernoratesAndAreas[selectedOption.value]!![0]
            DropdownMenuExample(
                text = "Governorate",
                options = egyptGovernoratesAndAreas[selectedOption.value]!!,
                selectedOption2
            )

        }
        Spacer(modifier = Modifier.height(70.dp))

        val selectedOption3 = remember { mutableStateOf("") }
        Text(text = "Street", fontSize = MaterialTheme.typography.titleLarge.fontSize)
        Spacer(modifier = Modifier.height(15.dp))

        TextField(onValueChange = {selectedOption3.value=it},value = selectedOption3.value, label = { Text(text = "Street") })
        Spacer(modifier = Modifier.height(70.dp))
        Button(modifier = Modifier.align(Alignment.CenterHorizontally),onClick = {
            shippingAddress= ShippingAddress()
            shippingAddress!!.address1=selectedOption3.value
            shippingAddress!!.city="${selectedOption.value} ${selectedOption2.value}"
            shippingAddress!!.country="Egypt"
            Log.d("shippingAddress",shippingAddress.toString())
            navController.navigateUp()
        }) {
            Text(text = "Save Address")
        }
    }

}







@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownMenuExample(text:String ,options:List<String>,selectedOption:MutableState<String>) {
    var expanded by remember { mutableStateOf(false) }
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        // State to manage whether the dropdown is expanded or not
        Text(text = "$text : ", fontSize = MaterialTheme.typography.titleLarge.fontSize)
        Spacer(modifier = Modifier.height(15.dp))
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded } // Toggle dropdown visibility
        ) {
            // Text field or box that triggers the dropdown
            TextField(
                value = selectedOption.value,
                onValueChange = { },
                readOnly = true, // Making it read-only
                modifier = Modifier
                    .menuAnchor() // Necessary to anchor the dropdown
                    .width(130.dp),
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) // Dropdown icon
                }
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false } // Close the dropdown on dismiss
            ) {
                options.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option) },
                        onClick = {
                            selectedOption.value = option // Update the selected option
                            expanded = false // Close the dropdown after selection
                        }
                    )
                }
            }
        }
    }
}

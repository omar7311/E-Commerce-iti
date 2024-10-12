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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.e_commerce_iti.ingredientColor1
import com.example.e_commerce_iti.model.pojos.draftorder.ShippingAddress
import com.example.e_commerce_iti.ui.theme._navigation.shippingAddress
import com.example.e_commerce_iti.ui.theme.viewmodels.PaymentViewModel

@Composable
fun AddressScreen(navController: NavController) {
    val cities = egyptianCities
    val selectedCity = remember { mutableStateOf(cities[0]) }
    val selectedGovernorate = remember { mutableStateOf(egyptGovernoratesAndAreas[selectedCity.value]!![0]) }
    val streetAddress = remember { mutableStateOf("") }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            AddressHeader(navController)

            Spacer(modifier = Modifier.height(32.dp))

            AddressForm(
                selectedCity = selectedCity,
                selectedGovernorate = selectedGovernorate,
                streetAddress = streetAddress
            )

            Spacer(modifier = Modifier.weight(1f))

            SaveAddressButton(
                navController = navController,
                selectedCity = selectedCity.value,
                selectedGovernorate = selectedGovernorate.value,
                streetAddress = streetAddress.value
            )
        }
    }
}

@Composable
fun AddressHeader(navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = { navController.navigateUp() }) {
            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
        }
        Text(
            text = "Address Details",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}

@Composable
fun AddressForm(
    selectedCity: MutableState<String>,
    selectedGovernorate: MutableState<String>,
    streetAddress: MutableState<String>
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Location",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            DropdownMenuExample(
                text = "City",
                options = egyptianCities,
                selectedOption = selectedCity,
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(16.dp))
            DropdownMenuExample(
                text = "Governorate",
                options = egyptGovernoratesAndAreas[selectedCity.value] ?: emptyList(),
                selectedOption = selectedGovernorate,
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Street Address",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        OutlinedTextField(
            value = streetAddress.value,
            onValueChange = { streetAddress.value = it },
            label = { Text("Enter your street address") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp), // Rounded corners for the text field
            colors = TextFieldDefaults.outlinedTextFieldColors( // Correct colors for TextField
                textColor = Color.Black,  // Text color
                focusedBorderColor = ingredientColor1, // Color of the border when focused
                unfocusedBorderColor = Color.Gray // Color of the border when not focused
            )
        )
    }
}

@Composable
fun SaveAddressButton(
    navController: NavController,
    selectedCity: String,
    selectedGovernorate: String,
    streetAddress: String
) {
    Button(
        onClick = {
            shippingAddress = ShippingAddress().apply {
                address1 = streetAddress
                city = "$selectedCity $selectedGovernorate"
                country = "Egypt"
            }
            Log.d("shippingAddress", shippingAddress.toString())
            navController.navigateUp()
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = ingredientColor1,
            contentColor = Color.White
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Text("Save Address", fontSize = 18.sp)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownMenuExample(
    text: String,
    options: List<String>,
    selectedOption: MutableState<String>,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    Column(modifier = modifier) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            androidx.compose.material3.OutlinedTextField(
                value = selectedOption.value,
                onValueChange = { },
                readOnly = true,
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                },
                colors = ExposedDropdownMenuDefaults.textFieldColors(),
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                options.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option) },
                        onClick = {
                            selectedOption.value = option
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}
//package com.example.e_commerce_iti.ui.theme
//
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.material3.Button
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.OutlinedTextField
//import androidx.compose.material3.RadioButton
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.unit.dp
//import com.example.e_commerce_iti.ui.theme.viewmodels.PaymentViewModel
//import com.example.e_commerce_iti.ui.theme.viewmodels.coupn_viewmodel.CouponViewModel
//import androidx.compose.foundation.layout.*
//import androidx.compose.material.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.navigation.NavController
//
//@Composable
//fun PaymentScreen(navController: NavController, viewModel: PaymentViewModel) {
//    val selectedPaymentMethod = remember { mutableStateOf("Card") }
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp)
//    ) {
//        Text(
//            text = "Payment",
//            style = MaterialTheme.typography.titleLarge,
//            modifier = Modifier.padding(bottom = 16.dp)
//        )
//
//        // Payment Method Selection
//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            horizontalArrangement = Arrangement.SpaceEvenly
//        ) {
//            RadioButton(
//                selected = selectedPaymentMethod.value == "Card",
//                onClick = { selectedPaymentMethod.value  = "Card" }
//            )
//            Text(text = "Card")
//
//            RadioButton(
//                selected = selectedPaymentMethod.value  == "Pay on Receive",
//                onClick = { selectedPaymentMethod.value  = "Pay on Receive" }
//            )
//            Text(text = "Pay on Receive")
//        }
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        if (selectedPaymentMethod.value  == "Card") {
//            // Card Payment Fields
//            OutlinedTextField(
//                value = viewModel.cardNumber,
//                onValueChange = { viewModel.cardNumber = it },
//                label = { "Card Number"},
//                modifier = Modifier.fillMaxWidth()
//            )
//
//            Spacer(modifier = Modifier.height(8.dp))
//
//            OutlinedTextField(
//                value = viewModel.expiryDate,
//                onValueChange = { viewModel.expiryDate = it },
//                label = { "Expiry Date" },
//                modifier = Modifier.fillMaxWidth()
//            )
//
//            Spacer(modifier = Modifier.height(8.dp))
//
//            OutlinedTextField(
//                value = viewModel.cvc,
//                onValueChange = { viewModel.cvc = it },
//                label = { "CVC" },
//                modifier = Modifier.fillMaxWidth()
//            )
//        } else {
//            // Pay on Receive Information
//            Text(
//                text = "You have chosen to pay on receive. Please ensure you have the exact amount ready.",
//                modifier = Modifier.padding(16.dp)
//            )
//        }
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        Button(
//            onClick = { /* Handle payment */ },
//            modifier = Modifier.align(Alignment.CenterHorizontally)
//        ) {
//            Text("Proceed")
//        }
//    }
//}

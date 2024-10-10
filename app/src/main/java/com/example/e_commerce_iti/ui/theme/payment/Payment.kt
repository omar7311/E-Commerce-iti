import android.util.Log
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.e_commerce_iti.R
import com.example.e_commerce_iti.currentUser
import com.example.e_commerce_iti.metadata
import com.example.e_commerce_iti.model.apistates.UiState
import com.example.e_commerce_iti.ui.theme.cart.roundToTwoDecimalPlaces
import com.example.e_commerce_iti.ui.theme.viewmodels.PaymentViewModel
import com.example.e_commerce_iti.ui.theme.viewmodels.cartviewmodel.CartViewModel

@Composable
fun PaymentScreen(paymentViewModel: PaymentViewModel,navController: NavController) {
    var cop by remember { mutableStateOf("") }
    paymentViewModel.currency.collectAsState()
    LaunchedEffect(Unit) {
        paymentViewModel.getCart(currentUser!!.cart)
        paymentViewModel.getusercurrency()
    }
    if (paymentViewModel.oderstate.collectAsState().value is UiState.Error){
        Log.e("ttttttttttttttttttttttttttttttaaa", (paymentViewModel.oderstate.collectAsState().value as UiState.Error).message)
    }
    if (paymentViewModel.oderstate.collectAsState().value is UiState.Success){
        Toast.makeText(navController.context, "Order Placed Successfully", Toast.LENGTH_SHORT).show()
    }
    Column(Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp),
            verticalAlignment = Alignment.CenterVertically // Align items vertically to center
        ) {
            IconButton(modifier = Modifier.fillMaxHeight(), onClick = { //navController.navigateUp()
            }) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
            }
            Spacer(modifier = Modifier.width(8.dp)) // Add some spacing between the icon and the text
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Payment",
                style = MaterialTheme.typography.headlineMedium
            )
        }
        Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp),) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Payment Information",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineMedium
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "shipping Address : ",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineSmall
            )
            Spacer(modifier = Modifier.height(16.dp))
            ToggleableTextFieldDemo(paymentViewModel)
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Total Amount ",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineSmall
            )
            Spacer(modifier = Modifier.height(12.dp))
            Row(modifier = Modifier.fillMaxWidth(), Arrangement.SpaceBetween) {
                Text(
                    text = "Discount : ",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.headlineMedium
                )
                Text(
                    text = "${(paymentViewModel.discount.collectAsState().value* paymentViewModel.currency.collectAsState().value.second).roundToTwoDecimalPlaces()} ${paymentViewModel.currency.collectAsState().value.first}",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.headlineMedium
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            Row(modifier = Modifier.fillMaxWidth(), Arrangement.SpaceBetween) {
                Text(
                    text = "Tax : ",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.headlineMedium
                )
                Text(
                    text = "${(paymentViewModel.tax.collectAsState().value * paymentViewModel.currency.collectAsState().value.second).roundToTwoDecimalPlaces()} ${paymentViewModel.currency.collectAsState().value.first}",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.headlineMedium
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            Row(modifier = Modifier.fillMaxWidth(), Arrangement.SpaceBetween) {
                Text(
                    text = "Total : ",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.headlineMedium
                )
                Text(
                    text = "${(paymentViewModel.total.collectAsState().value* paymentViewModel.currency.collectAsState().value.second).roundToTwoDecimalPlaces()} ${paymentViewModel.currency.collectAsState().value.first}",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.headlineMedium
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(Modifier.fillMaxWidth(),) {
            Text(
                text = "Discount Code ",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineSmall
            )
            }
            Spacer(modifier = Modifier.height(6.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start // Align everything to the start
            ) {
                TextField(
                    modifier = Modifier
                        .wrapContentWidth() // Make sure TextField fits its content
                        .weight(1f), // Allow the text field to take up available space if needed
                    value = cop,
                    onValueChange = { cop = it },
                    label = { Text("Coupon") }
                )
                Spacer(modifier = Modifier.width(10.dp))
                if (paymentViewModel.priceRules.collectAsState().value!=UiState.Loading) {
                    Button(
                        modifier = Modifier.wrapContentWidth(), // Ensure button fits its content
                        onClick = {
                            if (cop.isNotBlank()) {
                                paymentViewModel.get_discount_details(cop)
                            } else {
                                Toast.makeText(
                                    navController.context,
                                    "Please Enter Coupon",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    ) {
                        Text(text = "Apply")
                    }
                }else{
                    CircularProgressIndicator()
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            CreditCardForm(paymentViewModel)
        }
    }
}
@Composable
fun ToggleableTextFieldDemo(viewModel: PaymentViewModel) {
    var isEditable by remember { mutableStateOf(false) } // Default state is disabled
     viewModel.address.collectAsState(currentUser?.address)

    OutlinedTextField(
        value = viewModel.address.collectAsState().value,
        onValueChange = { viewModel.address.value = it },
        modifier = Modifier.fillMaxWidth(),
        enabled = isEditable, // Enable or disable based on state
        trailingIcon = {
            IconButton(onClick = { isEditable = !isEditable }) {
                Icon(
                    painter = if (isEditable) {
                        painterResource(id = R.drawable.baseline_check_24) // Use the edit icon when editable
                    } else {
                        painterResource(id = R.drawable.baseline_mode_edit_24) // Use your drawable when not editable
                    },
                    contentDescription = if (isEditable) "Edit" else "Check", // Update content description accordingly
                    tint = Color.Gray // Optional: set icon color
                )
            }
        },
        placeholder = { Text("Enter text here") },
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Done // Change IME action if needed
        ),
        visualTransformation = VisualTransformation.None // Optional: apply visual transformation
    )
}

@Composable
fun CreditCardForm(paymentViewModel: PaymentViewModel) {
    var selectedPaymentMethod by remember { mutableStateOf("") }
    var showCreditCardFields by remember { mutableStateOf(false) }
    var showPayOnReceiveFields by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()

    ) {
        // Card for Credit Card Payment
        Card(
            modifier = Modifier
                .fillMaxWidth(),
            elevation = 8.dp // Fixed elevation issue
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Row(
                    verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    RadioButton(
                        selected = selectedPaymentMethod == "credit_card",
                        onClick = {
                            paymentViewModel.paymentMethod.value="paid"
                            selectedPaymentMethod = "credit_card"
                            showCreditCardFields = !showCreditCardFields
                            showPayOnReceiveFields = false
                        }
                    )
                    Text(
                        text = "Credit Card",
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }

                // Animated drop-down for Credit Card form
                AnimatedVisibility(
                    visible = showCreditCardFields,
                    enter = expandVertically(),
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp)
                    ) {
                        OutlinedTextField(
                            value = paymentViewModel.cardNumber.collectAsState().value.toString(),
                            onValueChange = {paymentViewModel.cardNumber.value=it},
                            label = { Text("Card Number") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            modifier = Modifier.fillMaxWidth()
                        )

                        OutlinedTextField(
                            value = paymentViewModel.expiryMonth.collectAsState().value.toString(),
                            onValueChange = {paymentViewModel.expiryMonth.value=it},
                            label = { Text("Expiry Date (MM/YY)") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp)
                        )

                        OutlinedTextField(
                            value = paymentViewModel.cvv.collectAsState().value,
                            onValueChange = {paymentViewModel.cvv.value=it},
                            label = { Text("CVV") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Card(
            modifier = Modifier
                .fillMaxWidth()
         ,
            elevation =8.dp // Fixed elevation issue
        ) {
            Column(

                modifier = Modifier.padding(16.dp)

            ) {
                Row(
                    verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    RadioButton(
                        selected = selectedPaymentMethod == "pay_on_receive",
                        onClick = {
                            paymentViewModel.paymentMethod.value="pending"
                            selectedPaymentMethod = "pay_on_receive"
                            showPayOnReceiveFields = !showPayOnReceiveFields
                            showCreditCardFields = false
                        }
                    )
                    Text(
                        text = "Pay on Receive",
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }

                // You can show any extra information here when Pay on Receive is selected
                AnimatedVisibility(
                    visible = showPayOnReceiveFields,
                    enter = expandVertically(),
                ) {
                    Text(
                        text = "You will pay when the package is delivered.",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp)
                    )
                }
            }
        }

        // Confirm Button
        Button(
            onClick = {
                paymentViewModel.submitOrder(currentUser!!.cart)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp)
        ) {
            Text(text = "Confirm Payment")
        }
    }
}

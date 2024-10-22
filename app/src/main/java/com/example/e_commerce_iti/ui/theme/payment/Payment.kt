import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.AlertDialog
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.TextButton
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.e_commerce_iti.currentUser
import com.example.e_commerce_iti.gradientBrush
import com.example.e_commerce_iti.ingredientColor1
import com.example.e_commerce_iti.model.apistates.UiState
import com.example.e_commerce_iti.model.pojos.draftorder.ShippingAddress
import com.example.e_commerce_iti.ui.theme._navigation.Screens
import com.example.e_commerce_iti.ui.theme._navigation.shippingAddress
import com.example.e_commerce_iti.ui.theme.cart.roundToTwoDecimalPlaces
import com.example.e_commerce_iti.ui.theme.viewmodels.PaymentViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PaymentScreen(paymentViewModel: PaymentViewModel, navController: NavController) {
    if (currentUser.observeAsState().value!=null){

        var cop by rememberSaveable { mutableStateOf("") }
        val place by remember { mutableStateOf(shippingAddress ?:ShippingAddress())}
        LaunchedEffect(Unit) {
            paymentViewModel.getCart(currentUser.value!!.cart)
            paymentViewModel.getusercurrency()
        }
        val cartState = paymentViewModel.cart.collectAsState().value
        Column(Modifier.fillMaxSize()) {
            // Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    modifier = Modifier.fillMaxHeight(),
                    onClick = { navController.navigateUp() }
                ) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Payment",
                    style = MaterialTheme.typography.headlineMedium
                )
            }

            when (cartState) {
                is UiState.Loading -> {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }

                is UiState.Success -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                            .padding(16.dp)
                    ) {
                        PaymentContent( palce = place,paymentViewModel, navController, cop) { cop = it }
                        Spacer(modifier = Modifier.height(16.dp))
                        CreditCardForm(paymentViewModel, place = place, navController)
                    }
                }

                else -> {
                    // Handle error state if needed
                    Text("Error loading cart data")
                }
            }
        }
    }else{
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }
}

@Composable
fun PaymentContent(
    palce:ShippingAddress
,
    paymentViewModel: PaymentViewModel,
    navController: NavController,
    cop: String,
    onCopChange: (String) -> Unit
) {
    Text(
        modifier = Modifier.fillMaxWidth(),
        text = "Payment Information",
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.headlineMedium.copy(
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp
        )
    )

    Spacer(modifier = Modifier.height(16.dp))
    Text(
        text = "Shipping Address:",
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.headlineSmall
    )
    Spacer(modifier = Modifier.height(16.dp))
    ToggleableTextFieldDemo(navController)
    Spacer(modifier = Modifier.height(16.dp))
    Text("address: ${palce.country} , ${palce.city} , ${palce.address1}")
    Spacer(modifier = Modifier.height(16.dp))
    Text(
        text = "Total Amount",
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.headlineSmall
    )
    Spacer(modifier = Modifier.height(12.dp))

    // Display Discount, Tax, and Total
    DisplayAmountRow("Tax", paymentViewModel.tax, paymentViewModel)
    DisplayAmountRow("Discount", paymentViewModel.discount, paymentViewModel)
    DisplayAmountRow("Total", paymentViewModel.total, paymentViewModel)

    Spacer(modifier = Modifier.height(16.dp))
    Text(
        text = "Discount Code",
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.headlineSmall
    )
    Spacer(modifier = Modifier.height(6.dp))
    DiscountCodeSection(paymentViewModel, navController, cop, onCopChange)
}

@Composable
fun DisplayAmountRow(label: String, amount: StateFlow<Double>, paymentViewModel: PaymentViewModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
        ,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Text(
            text = "$label:",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color.DarkGray
            )
        )
        Text(
            text = "${(amount.collectAsState().value * paymentViewModel.currency.collectAsState().value.second).roundToTwoDecimalPlaces()} ${paymentViewModel.currency.collectAsState().value.first}",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color.Blue
            )
        )
    }


    Spacer(modifier = Modifier.height(12.dp))
}
var e=false
@Composable
fun DiscountCodeSection(
    paymentViewModel: PaymentViewModel,
    navController: NavController,
    cop: String,
    onCopChange: (String) -> Unit
) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        androidx.compose.material.OutlinedTextField(
            modifier = Modifier
                .padding(5.dp)
                .weight(1f),
            value = cop,
              enabled =  paymentViewModel.priceRules.collectAsState().value !is UiState.Success ,
            onValueChange = onCopChange,
            label = { Text("Coupon") },
            shape = RoundedCornerShape(16.dp), // This makes the TextField rounded
                    colors = TextFieldDefaults.outlinedTextFieldColors( // Correct colors for TextField
                    textColor = Color.Black,  // Text color
            focusedBorderColor = ingredientColor1, // Color of the border when focused
            unfocusedBorderColor = Color.Gray // Color of the border when not focused
        )

        )

        Spacer(modifier = Modifier.width(10.dp))
        val priceRulesState = paymentViewModel.priceRules.collectAsState().value
        if (priceRulesState != UiState.Loading) {
            Button(
                modifier = Modifier
                    .wrapContentWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = ingredientColor1, // Custom green color
                    contentColor = Color.White // Text color
                ),
                onClick = {
                    if (cop.isNotBlank()) {
                        e=true
                        paymentViewModel.get_discount_details(cop)
                    } else {
                        Toast.makeText(
                            navController.context,
                            "Please Enter Coupon",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                },
                enabled = priceRulesState !is UiState.Success
            ) {
                Text(text = "Apply")
            }
            if (paymentViewModel.discountCode.collectAsState().value is UiState.Error&&e) {
                e=false
                Toast.makeText(LocalContext.current, "Invalid Coupon", Toast.LENGTH_SHORT).show()
            }
        } else {
            Spacer(Modifier.height(10.dp))
            CircularProgressIndicator()
        }
    }
}
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CreditCardForm(paymentViewModel: PaymentViewModel, place: ShippingAddress,navController: NavController) {
    var selectedPaymentMethod by remember { mutableStateOf("") }
    var showCreditCardFields by remember { mutableStateOf(false) }
    var showPayOnReceiveFields by remember { mutableStateOf(false) }
    var showSuccessDialog by remember { mutableStateOf(false) }
    var showErrorDialog by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var month by remember { mutableStateOf("") }
    var year by remember { mutableStateOf("") }
    var card by remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        // Card for Credit Card Payment
        Card(
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            elevation = 8.dp // Fixed elevation issue
        )  {
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
                            paymentViewModel.paymentMethod.value = "paid"
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
                            value =paymentViewModel.cardNumber.collectAsState().value, // Format the card number
                            onValueChange = { newValue ->

                                val digitsOnly = newValue.filter { it.isDigit() } // Keep only digits
                                if (digitsOnly.length <= 16) { // Limit the length to 16 digits
                                    paymentViewModel.cardNumber.value = newValue
                                }
                                card=paymentViewModel.cardNumber.value
                            },
                            label = { Text("Card Number") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            modifier = Modifier.fillMaxWidth()
                        )

                        Row(Modifier.fillMaxWidth()) {
                            OutlinedTextField(
                                value = paymentViewModel.expiryMonth.collectAsState().value,
                                onValueChange = { newValue ->
                                    if (newValue.length == 2 && newValue.all { it.isDigit() }) {
                                        // Check if the month is valid
                                        if (newValue.matches("(0[1-9]|1[0-2])".toRegex())) {
                                            paymentViewModel.expiryMonth.value = newValue
                                        } else if (newValue.isEmpty()) {
                                            // Allow clearing the field
                                            paymentViewModel.expiryMonth.value = ""
                                        }
                                    }
                                   else if (newValue.length == 1 && newValue.all { it.isDigit() }) {
                                        // Check if the month is valid
                                        if (newValue.matches("(0|1)".toRegex())) {
                                            paymentViewModel.expiryMonth.value = newValue
                                        } else if (newValue.isEmpty()) {
                                            // Allow clearing the field
                                            paymentViewModel.expiryMonth.value = ""
                                        }
                                    }else{
                                        paymentViewModel.expiryMonth.value = ""
                                    }

                                },
                                label = { Text("Expiry Date Month") },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                modifier = Modifier
                                    .weight(0.4F)
                                    .padding(top = 8.dp)
                            )

                            OutlinedTextField(
                                value = paymentViewModel.expiryYear.collectAsState().value,
                                onValueChange =  { newValue ->
                                   if (newValue.length==2&& newValue.all { it.isDigit() }) {
                                       paymentViewModel.expiryYear.value=newValue
                                   }else if (newValue.length == 1 && newValue.all { it.isDigit() }) {
                                       paymentViewModel.expiryYear.value=newValue
                                   }else if(newValue==""){
                                       paymentViewModel.expiryYear.value=""
                                   }

                                },
                                label = { Text("Expiry Date Year") },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                modifier = Modifier
                                    .weight(0.4F)
                                    .padding(top = 8.dp)
                            )
                        }




                        OutlinedTextField(
                            value = paymentViewModel.cvv.collectAsState().value,
                            onValueChange = { newValue ->
                                if (newValue.length <= 3 && newValue.all { it.isDigit() }) {
                                    paymentViewModel.cvv.value = newValue
                                }
                            },
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
                .fillMaxWidth(),
            shape = RoundedCornerShape(16.dp), // Adjust the dp value for more/less rounding
            elevation = 8.dp // Fixed elevation issue
        )  {
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
                            paymentViewModel.paymentMethod.value = "pending"
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


        if (isLoading) {
            Row(Modifier.fillMaxSize(), Arrangement.Center) {
                CircularProgressIndicator()
            }
        } else {
            Button(
                onClick = {
                    isLoading=true
                    CoroutineScope(Dispatchers.IO).launch {
                        try {
                            paymentViewModel.submitOrder(place)
                            withContext(Dispatchers.Main) {
                                showSuccessDialog = true
                                withContext(Dispatchers.Main) {
                                    isLoading = false
                                }
                            }
                        } catch (e: Exception) {
                            withContext(Dispatchers.Main) {
                                errorMessage = e.message ?: "An error occurred"
                                showErrorDialog = true
                                withContext(Dispatchers.Main) {
                                    isLoading = false
                                }
                            }
                        }
                    }
                },
                modifier = Modifier
                    .padding(30.dp)
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = ingredientColor1, // Custom green color
                    contentColor = Color.White // Text color
                )
            ) {
                Text(text = "Confirm Payment")
            }
        }
    }

    if (showSuccessDialog) {
        SuccessOrErrorDialog(
            showalert = remember { mutableStateOf(true) },
            messageText = "Payment processed successfully!",
            titleText = "Success",
            isError = false
        ) {
            showSuccessDialog = false
            navController.navigateUp()
        }
    }

    if (showErrorDialog) {
        SuccessOrErrorDialog(
            showalert = remember { mutableStateOf(true) },
            messageText = errorMessage,
            titleText = "Error",
            isError = true
        ) {
            showErrorDialog = false
        }
    }
}


@Composable
fun ToggleableTextFieldDemo(navController: NavController) {

    OutlinedButton(modifier = Modifier.fillMaxWidth(), onClick = { navController.navigate(Screens.Address.route) }) {
        Row(Modifier.fillMaxWidth(), Arrangement.SpaceBetween) {
        Row() {
        Icon(Icons.Default.Add, contentDescription = " " )
            Spacer(modifier = Modifier.width(10.dp))
            Text(text = "Add Shipping Address",style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                // No color used, we apply the gradient brush instead
                brush = gradientBrush // This is how you apply the gradient
            )
            )
            }
        Icon(Icons.Default.KeyboardArrowRight, contentDescription = " " )
        }
    }
}
@Composable
fun SuccessOrErrorDialog(showalert: MutableState<Boolean>, messageText:String, titleText:String, isError: Boolean, onDismiss: () -> Unit) {

    if (showalert.value)
           AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = titleText,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Text(text = messageText)
        },
        confirmButton = {
            TextButton(onClick = { showalert.value = false
            onDismiss()
            }) {
                Text(text = "OK")
            }
        }
    )
}


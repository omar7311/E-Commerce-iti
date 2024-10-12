package com.example.e_commerce_iti.ui.theme.changeuserinfo

import android.content.Context
import android.util.Log
import android.widget.Toast
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.e_commerce_iti.currentUser
import com.example.e_commerce_iti.model.apistates.UiState
import com.example.e_commerce_iti.model.local.LocalDataSourceImp
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
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import com.example.e_commerce_iti.R
import com.example.e_commerce_iti.ingredientColor1
import com.skydoves.landscapist.glide.GlideImage


@Composable

fun ChangeUserDataScreen(viewModel: ChangeUserDataViewModel, navController: NavController) {
    // Fetch user data from Firebase
        if (currentUser.observeAsState().value!=null) {
            viewModel.getCustomerData(Firebase.auth.currentUser!!.email!!)
            ChangeUserDataScreenContent(viewModel, navController)
        }else{
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
}
@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun ChangeUserDataScreenContent(viewModel: ChangeUserDataViewModel,navController: NavController) {
    val state = viewModel.userStateData.collectAsState()
    val context = LocalContext.current
    Column(Modifier.fillMaxWidth().padding(top = 11.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            verticalAlignment = Alignment.CenterVertically // Align items vertically to center
        ) {
            IconButton(onClick = { navController.navigateUp() }) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
            }
            Spacer(modifier = Modifier.width(8.dp)) // Add some spacing between the icon and the text
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Change User Data",
                style = MaterialTheme.typography.headlineMedium,
                color = ingredientColor1
            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {


            when (val uiState = state.value) {
                is UiState.Success -> {
                    val data = uiState.data
                    val fname = rememberSaveable { mutableStateOf(data.first_name ?: "user") }
                    val lname = rememberSaveable { mutableStateOf(data.last_name ?: "user") }
                    val address = rememberSaveable {
                        mutableStateOf(
                            data.addresses?.firstOrNull()?.address1 ?: "N/A"
                        )
                    }
                    val phone = rememberSaveable { mutableStateOf(data.phone ?: "N/A") }
                    Row(
                        modifier = Modifier.fillMaxWidth().wrapContentHeight().padding(20.dp),
                        Arrangement.Center
                    ) {
                        GlideImage(imageModel = R.drawable.avatar, Modifier.size(100.dp))
                    }
                    // TextFields for input with improved layout
                    UserDataInputField("First Name", fname)
                    UserDataInputField("Last Name", lname)
                    UserDataInputField("Address", address)
                    UserDataInputField(
                        "Phone",
                        phone,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
                    )

                    // Save button with enhanced visual design
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        onClick = {
                            if (isValidE164PhoneNumber(phone.value)) {
                                viewModel.updateCustomerData(
                                    fname.value,
                                    lname.value,
                                    address.value,
                                    phone.value
                                )
                                Toast.makeText(context, "Profile updated", Toast.LENGTH_SHORT)
                                    .show()
                            } else {
                                Toast.makeText(context, "Invalid Phone Number", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        },
                        colors = ButtonDefaults.buttonColors(ingredientColor1, Color.White)
                    ) {
                        Text(
                            text = "Save Changes",
                            fontSize = 18.sp,
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                }

                is UiState.Loading -> {
                    Column(Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                    }
                }

                else -> {
                    // Handle other states if necessary
                    Text(text = "Error loading data", color = MaterialTheme.colorScheme.error)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserDataInputField(label: String, state: MutableState<String>, keyboardOptions: KeyboardOptions = KeyboardOptions.Default) {
    OutlinedTextField(
        value = state.value,
        onValueChange = { state.value = it },
        label = { Text(text = label, color = ingredientColor1) },
        keyboardOptions = keyboardOptions,
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        shape = RoundedCornerShape(8.dp),
        colors = androidx.compose.material3.OutlinedTextFieldDefaults.colors(  focusedBorderColor = ingredientColor1,
            unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = ContentAlpha.disabled)
        ) // Removing specific Material 3 colors, fall back to defaults

    )
}


//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun ChangeUserDataPreview() {
//    ECommerceITITheme {
//        val context = LocalContext.current
//        val navController = rememberNavController()
//        val viewModel = ChangeUserDataViewModel(
//            repository = RepositoryImpl(RemoteDataSourceImpl(), LocalDataSourceImpl(context.getSharedPreferences("user_data", Context.MODE_PRIVATE)))
//        )
//        ChangeUserDataScreen(viewModel, navController)
//    }
//}

fun isValidE164PhoneNumber(phone: String): Boolean {
    val regex = Regex("^\\+\\d{1,15}$")
    return regex.matches(phone)
}

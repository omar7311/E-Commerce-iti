package com.example.e_commerce_iti

import android.app.Activity
import android.content.Context
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.DropdownMenu
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.IconButton
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.e_commerce_iti.model.remote.RemoteDataSourceImp

import com.example.e_commerce_iti.ui.theme.createCustomer

//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.TextButton
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Icon
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.e_commerce_iti.ui.theme._navigation.Screens
import com.example.e_commerce_iti.ui.theme.authentication.FirebaseAuthManager
import com.example.e_commerce_iti.ui.theme.authentication.FirebaseAuthManager.firebaseAuthWithGoogle
import com.example.e_commerce_iti.ui.theme.viewmodels.home_viewmodel.HomeViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ApiException
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay

import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterialApi::class, DelicateCoroutinesApi::class)
@Composable
fun SignupScreen(
    controller: NavController,
) {
    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var fullNameError by remember { mutableStateOf<String?>(null) }
    var emailError by remember { mutableStateOf<String?>(null) }
    var phoneError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }
    var confirmPasswordError by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(false) }
    var isPasswordVisible by remember { mutableStateOf(false) }
    var isConfirmPasswordVisible by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val flag= rememberSaveable{ mutableStateOf(false) }
    val emailRegex = remember { Regex("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}\$") }
    val passwordRegex = remember { Regex("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}\$") }
    val phoneRegex = remember { Regex("^\\+20[1][0125][0-9]{8}\$") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Header and other components can be added here...

        OutlinedTextField(
            value = fullName,
            onValueChange = {
                fullName = it
                fullNameError = if (it.isBlank()) "Full name is required" else null
            },
            label = { Text("Full Name") },
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
            shape = RoundedCornerShape(12.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFF6200EE),
                unfocusedBorderColor = Color.Gray.copy(alpha = 0.6f),
                errorBorderColor = Color.Red,
                focusedLabelColor = Color(0xFF6200EE),
                unfocusedLabelColor = Color.Gray,
                errorLabelColor = Color.Red,
                textColor = Color.Black,
                cursorColor = Color(0xFF6200EE),
                backgroundColor = Color(0xFFF5F5F5),
            ),
            isError = fullNameError != null,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
        )
        if (fullNameError != null) {
            Text(fullNameError!!, color = Color.Red, modifier = Modifier.align(Alignment.Start))
        }

        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
                emailError = if (!emailRegex.matches(it)) "Invalid email format" else null
            },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
            shape = RoundedCornerShape(12.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFF6200EE),
                unfocusedBorderColor = Color.Gray.copy(alpha = 0.6f),
                errorBorderColor = Color.Red,
                focusedLabelColor = Color(0xFF6200EE),
                unfocusedLabelColor = Color.Gray,
                errorLabelColor = Color.Red,
                textColor = Color.Black,
                cursorColor = Color(0xFF6200EE),
                backgroundColor = Color(0xFFF5F5F5),
            ),
            isError = emailError != null,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )
        if (emailError != null) {
            Text(emailError!!, color = Color.Red, modifier = Modifier.align(Alignment.Start))
        }

        OutlinedTextField(
            value = phoneNumber,
            onValueChange = {
                phoneNumber = it
                phoneError = if (!phoneRegex.matches(it)) "Invalid Egyptian phone number" else null
            },
            label = { Text("Phone Number") },
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
            shape = RoundedCornerShape(12.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFF6200EE),
                unfocusedBorderColor = Color.Gray.copy(alpha = 0.6f),
                errorBorderColor = Color.Red,
                focusedLabelColor = Color(0xFF6200EE),
                unfocusedLabelColor = Color.Gray,
                errorLabelColor = Color.Red,
                textColor = Color.Black,
                cursorColor = Color(0xFF6200EE),
                backgroundColor = Color(0xFFF5F5F5),
            ),
            isError = phoneError != null,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
        )
        if (phoneError != null) {
            Text(phoneError!!, color = Color.Red, modifier = Modifier.align(Alignment.Start))
        }

        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
                passwordError = if (!passwordRegex.matches(it)) "Password must be at least 8 characters long and include numbers, uppercase letters, lowercase letters, and special characters." else null
            },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
            shape = RoundedCornerShape(12.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFF6200EE),
                unfocusedBorderColor = Color.Gray.copy(alpha = 0.6f),
                errorBorderColor = Color.Red,
                focusedLabelColor = Color(0xFF6200EE),
                unfocusedLabelColor = Color.Gray,
                errorLabelColor = Color.Red,
                textColor = Color.Black,
                cursorColor = Color(0xFF6200EE),
                backgroundColor = Color(0xFFF5F5F5),
            ),
            isError = passwordError != null,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation()
        )
        if (passwordError != null) {
            Text(passwordError!!, color = Color.Red, modifier = Modifier.align(Alignment.Start))
        }

        OutlinedTextField(
            value = confirmPassword,
            onValueChange = {
                confirmPassword = it
                confirmPasswordError = if (it != password) "Passwords do not match" else null
            },
            label = { Text("Confirm Password") },
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
            shape = RoundedCornerShape(12.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFF6200EE),
                unfocusedBorderColor = Color.Gray.copy(alpha = 0.6f),
                errorBorderColor = Color.Red,
                focusedLabelColor = Color(0xFF6200EE),
                unfocusedLabelColor = Color.Gray,
                errorLabelColor = Color.Red,
                textColor = Color.Black,
                cursorColor = Color(0xFF6200EE),
                backgroundColor = Color(0xFFF5F5F5),
            ),
            isError = confirmPasswordError != null,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            visualTransformation = if (isConfirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation()
        )
        if (confirmPasswordError != null) {
            Text(confirmPasswordError!!, color = Color.Red, modifier = Modifier.align(Alignment.Start))
        }

        Button(
            onClick = {
                if (fullName.isBlank() || !emailRegex.matches(email) || !phoneRegex.matches(phoneNumber) ||
                    !passwordRegex.matches(password) || password != confirmPassword) {
                    errorMessage = "Please correct the errors in the form"
                } else {
                    isLoading = true
                    FirebaseAuthManager.signUp(email, password) { success, error ->
                        if (success) {

                            GlobalScope.launch(Dispatchers.IO){

                                    RemoteDataSourceImp().createCustomer(
                                        createCustomer(
                                            email,
                                            fullName,
                                            fullName,
                                            phoneNumber
                                        )
                                    )
                                isLoading = true
                                delay(7000) // 3 seconds delay
                            }

                            // Start a new coroutine for the delay
                            scope.launch {
                                isLoading = false
                                controller.navigate(Screens.Login.route)
                            }
                        } else {
                            isLoading = false
                            errorMessage = error
                        }
                    }
                }
            },
            modifier = Modifier.wrapContentSize(),
            colors = ButtonDefaults.buttonColors(
                containerColor = turquoise,
                contentColor = mediumVioletRed
            ),
            shape = RoundedCornerShape(8.dp)
        ) {
            if (isLoading) {
                LoadingIndicator()
            } else {
                Text("SIGN UP", color = Color(0xFF6200EE), fontWeight = FontWeight.Bold)
            }
        }

        // "Already have an account?" button can be added here...

        // Display error message if exists
        errorMessage?.let {
            Text(it, color = Color.Red, modifier = Modifier.align(Alignment.CenterHorizontally))
        }
    }

// ... (keep the existing "Already have an account?" button)

        AnimatedVisibility(visible = errorMessage != null) {
            errorMessage?.let {
                Text(text = it, color = Color.Red, modifier = Modifier.padding(top = 8.dp))
            }
        }
    }

/*@Composable
fun SignupAnimation(modifier: Modifier) {
    val lottieComposition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.signup))
    val isAnimationPlaying = lottieComposition != null

    if (isAnimationPlaying) {
        LottieAnimation(
            composition = lottieComposition,
            iterations = 1,
            modifier = modifier
        )
    }
}*/

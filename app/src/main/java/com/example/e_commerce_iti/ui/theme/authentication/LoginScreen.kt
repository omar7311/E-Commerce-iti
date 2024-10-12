package com.example.e_commerce_iti

import android.app.Activity
import android.content.Intent
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.e_commerce_iti.ui.theme._navigation.Screens
import com.example.e_commerce_iti.ui.theme.authentication.FirebaseAuthManager
import com.example.e_commerce_iti.ui.theme.authentication.FirebaseAuthManager.firebaseAuthWithGoogle
import com.example.e_commerce_iti.ui.theme.home.CustomText
import com.example.e_commerce_iti.ui.theme.home.MyLottieAnimation
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ApiException
import kotlinx.coroutines.launch

//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.OutlinedTextField
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.TextFieldDefaults
import androidx.compose.ui.text.style.TextOverflow
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

@Composable
fun LoginScreen(
    controller: NavController,
    context: Activity,
    googleSignInClient: GoogleSignInClient,
    onSignInSuccess: () -> Unit
) {

    /**
     *
     *          check if youser  is signed in
     */
    val firebaseAuth = FirebaseAuth.getInstance()
    val currentUser = firebaseAuth.currentUser

/*    if (currentUser != null) {
        LaunchedEffect(Unit) {
            controller.navigate(Screens.Home.route) {
                popUpTo(Screens.Login.route) {
                    inclusive = true
                } // Remove the login screen from back stack
            }
        }
    }*/
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(false) }
    var isPasswordVisible by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    val emailRegex = remember { Regex("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}\$") }
    val passwordRegex =
        remember { Regex("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}\$") }

    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) { result ->
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                firebaseAuthWithGoogle(
                    account.idToken!!,
                    onSignInSuccess,
                    { error -> errorMessage = error })
            } catch (e: ApiException) {
                errorMessage = "Google sign-in failed"
            }
        }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(5.dp))


        Box(
            modifier = Modifier
                .wrapContentSize()
                .clip(RoundedCornerShape(12.dp))
                .size(250.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            LoginAnimation(modifier = Modifier.size(250.dp))
        }

        CustomText(
            "Login",
            transparentBrush,
            textColor = Color.Black,
            fontSize = 28.sp,
            style = FontWeight.Bold
        )

        OutlinedTextField(
            value = email,
            onValueChange = { email = it; errorMessage = null },
            label = { Text("Email") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp), // Add padding for better spacing
            shape = RoundedCornerShape(12.dp), // Rounded corners
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFF6200EE), // Purple color for focus
                unfocusedBorderColor = Color.Gray.copy(alpha = 0.6f), // Light gray when not focused
                focusedLabelColor = Color(0xFF6200EE),
                unfocusedLabelColor = Color.Gray,
                textColor = Color.Black, // Text color
                cursorColor = Color(0xFF6200EE), // Cursor color matching focus
                backgroundColor = Color(0xFFF5F5F5), // Light background
            ),
            isError = !emailRegex.matches(email) && email.isNotEmpty(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it; errorMessage = null },
            label = { Text("Password") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            shape = RoundedCornerShape(12.dp),
            visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                    Icon(
                        painter = painterResource(id = if (isPasswordVisible) R.drawable.eyevisable_onn else R.drawable.eyevisable_off),
                        contentDescription = "Password Visibility",
                        tint = Color.Gray // Icon color
                    )
                }
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFF6200EE),
                unfocusedBorderColor = Color.Gray.copy(alpha = 0.6f),
                focusedLabelColor = Color(0xFF6200EE),
                unfocusedLabelColor = Color.Gray,
                textColor = Color.Black,
                cursorColor = Color(0xFF6200EE),
                backgroundColor = Color(0xFFF5F5F5),
            ),
            isError = !passwordRegex.matches(password) && password.isNotEmpty(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )

        Button(

            onClick  = {
                if (emailRegex.matches(email) && passwordRegex.matches(password)) {
                    CoroutineScope(Dispatchers.IO).launch {
                        isLoading = true
                        FirebaseAuthManager.login(email, password) { success, error ->

                                isLoading = false

                            if (success) {
                                controller.navigate(Screens.Home.route)
//                                if(currentUser?.isEmailVerified == true)
//
//                                else Toast.makeText(context,"Verify Your Mail",Toast.LENGTH_LONG).show()
                            } else {
                                errorMessage = error
                            }
                        }
                    }
                } else {
                    errorMessage = "Please enter a valid email and password"
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = ingredientColor1, // Custom green color
                contentColor = Color.White // Text color
            )
        ) {
            if (isLoading) {
                LoadingIndicator()
            } else {
                Text("Log In", fontSize = 18.sp)
            }
        }

            var isLoading by remember { mutableStateOf(false) }

          //

        Button(

            onClick  = {
                FirebaseAuthManager.loginAnonymously { success, error ->
                    if (success) {
                        controller.navigate(Screens.Home.route) {
                            popUpTo(controller.graph.startDestinationId) { inclusive = true }
                            launchSingleTop = true
                        }
                    } else {
                        Toast.makeText(context, error, Toast.LENGTH_LONG).show()
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = ingredientColor1, // Custom green color
                contentColor = Color.White // Text color
            )
        ) {
            if (isLoading) {
                LoadingIndicator()
            } else {
                Text("Go Guest", fontSize = 18.sp)
            }

        }

        // You can add more components here, if needed


        Row(
            modifier = Modifier
                .fillMaxWidth(), // Make the row fill the available width
            horizontalArrangement = Arrangement.Center // Center the button horizontally
        ) {
            TextButton(
                onClick = { controller.navigate(Screens.Signup.route) },
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(20.dp),
            ) {
                Text("New user? ", color = royalBlue, fontSize = 20.sp)
                Text("Register", color = ingredientColor1, fontSize = 20.sp)
                Text(" Now", color = royalBlue, fontSize = 20.sp)
            }

        }



        AnimatedVisibility(visible = errorMessage != null) {
            errorMessage?.let {
                Text(text = it, color = Color.Red, modifier = Modifier.padding(top = 8.dp))
            }
        }
    }
}


fun signInWithGoogle(
    googleSignInClient: GoogleSignInClient,
    launcher: ActivityResultLauncher<Intent>
) {
    val signInIntent = googleSignInClient.signInIntent
    launcher.launch(signInIntent)
}
@Composable
fun LoginAnimation(
    modifier: Modifier = Modifier
) {
    val lottieComposition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.loginperson))

    if (lottieComposition != null) {
        LottieAnimation(
            composition = lottieComposition,
            iterations = LottieConstants.IterateForever, // Set to repeat indefinitely
            modifier = modifier
        )
    }
}

/**
 *
 *     // Google Sign-In button
 *
 *                 Box(
 *                     modifier = Modifier
 *                         .size(50.dp),
 *                     contentAlignment = Alignment.Center
 *                 ) {
 *                     Image(
 *                         painter = painterResource(id = R.drawable.google),
 *                         contentDescription = "Sign in with Google",
 *                         modifier = Modifier
 *                             .size(100.dp)
 *                             .clickable {
 *                                 isLoading = true
 *                                 // Launch Google sign-in flow
 *                                 signInWithGoogle(googleSignInClient, launcher)
 *                                 isLoading = false
 *                             }
 *                     )
 *                 }
 *
 */
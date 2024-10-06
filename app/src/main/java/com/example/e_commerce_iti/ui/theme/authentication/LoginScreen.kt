package com.example.e_commerce_iti

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.e_commerce_iti.ui.theme._navigation.Screens
import com.example.e_commerce_iti.ui.theme.authentication.FirebaseAuthManager

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
   controller:NavController,
   context:Context
){
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Login", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        // Email input
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Password input
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Login button
        Button(
            onClick = { FirebaseAuthManager.login(email,password){ success,error->
                if(success){
                    controller.navigate(Screens.Home.route)
                }else{
                    Toast.makeText(context,error,Toast.LENGTH_LONG).show()
                }

            }},
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Login")
        }
        Spacer(modifier = Modifier.height(16.dp))
        //sign in with google
        Button(
            onClick = {

            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Login in with Google")
        }
        Spacer(modifier = Modifier.height(16.dp))

        // Anonymous Login Button
        Button(
            onClick = {
          FirebaseAuthManager.loginAnonymously{ success,error->
              if(success){
                  controller.navigate(Screens.Home.route)
              }else{
                  Toast.makeText(context,error,Toast.LENGTH_LONG).show()
              }

          }

            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Login Anonymously")
        }
        Spacer(modifier = Modifier.height(16.dp))

        // Sign up option
        TextButton(onClick = { controller.navigate(Screens.Signup.route) }) {
            Text("Don't have an account? Sign Up")
        }
    }
}


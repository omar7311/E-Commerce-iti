package com.example.e_commerce_iti

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
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

@Composable
fun LoginScreen(
    login:(String,String)->Unit,
    loginAnonymously:()->Unit,
    loginWithGoogle:()->Unit,
    toSignUp:()->Unit
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
            onClick = {login(email,password)},
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Login")
        }
        Spacer(modifier = Modifier.height(16.dp))
        //sign in with google
        Button(
            onClick = {
            loginWithGoogle()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Login in with Google")
        }
        Spacer(modifier = Modifier.height(16.dp))

        // Anonymous Login Button
        Button(
            onClick = {
             loginAnonymously()
                FirebaseAuthManager.loginAnonymously{ success,message->
                    if(success){

                    }else{

                    }

                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Login Anonymously")
        }
        Spacer(modifier = Modifier.height(16.dp))

        // Sign up option
        TextButton(onClick = { toSignUp() }) {
            Text("Don't have an account? Sign Up")
        }
    }
}
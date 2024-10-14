package com.example.e_commerce_iti.ui.theme.guest

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.example.e_commerce_iti.gradientBrush
import com.example.e_commerce_iti.ingredientColor1
import com.example.e_commerce_iti.ui.theme._navigation.Screens

@Composable
fun GuestScreen(controller: NavController?) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Welcome, Guest!",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Sign in to unlock all features and get the most out of our application!",
                fontSize = 16.sp,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(

                onClick = {
                    // Navigate to login screen
                    controller?.navigate(Screens.Login.route){
                        popUpTo(controller.graph.findStartDestination().id) {
                            inclusive = true
                        }
                        launchSingleTop = true  // when user press multiple time on it
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
                Text("Log In", fontSize = 18.sp)
            }

            Spacer(modifier = Modifier.height(16.dp))

            TextButton(
                onClick = {

                    controller?.navigate(Screens.Signup.route){
                        popUpTo(controller.graph.startDestinationId){
                            inclusive = true
                        }
                    }
                }
            ) {
                Text("Don't have an account? Sign Up", fontSize = 14.sp)
            }
        }
    }
}
package com.example.e_commerce_iti

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.e_commerce_iti.ui.theme.ECommerceITITheme
import com.example.e_commerce_iti.ui.theme._navigation.Navigation
import com.example.e_commerce_iti.ui.theme.home.FetchingBrandData
import com.example.e_commerce_iti.ui.theme.home.HomeScreen

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            /**
             *      here is the start of home and all screens in the below function
             */
            Navigation()
        }
    }
}


package com.example.e_commerce_iti

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.e_commerce_iti.ui.theme._navigation.Navigation

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


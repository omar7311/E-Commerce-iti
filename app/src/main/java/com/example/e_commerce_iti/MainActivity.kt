package com.example.e_commerce_iti

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.e_commerce_iti.network.NetworkObserver
import com.example.e_commerce_iti.ui.theme._navigation.Navigation

class MainActivity : ComponentActivity() {
    private lateinit var networkObserver: NetworkObserver
    override fun onCreate(savedInstanceState: Bundle?) {
        networkObserver = NetworkObserver(applicationContext)  // Initialize NetworkObserver
        networkObserver.register()  // Register the network callback
        super.onCreate(savedInstanceState)
        setContent {
            /**
             *      here is the start of home and all screens in the below function
             */
            Navigation(networkObserver)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        networkObserver.unRegister()  // Unregister the network callback
    }
}


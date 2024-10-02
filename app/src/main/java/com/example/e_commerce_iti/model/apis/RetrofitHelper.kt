package com.example.e_commerce_iti.model.apis

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitHelper {
    private val client = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    private val BASE_URL = "https://android-sv24-r3team1.myshopify.com/admin/api/2024-07/"

    private val retrofitInstance: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client) // Pass the OkHttpClient instance here
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // Lazily initialized service instance
    val service: EcommerceApi by lazy {
        retrofitInstance.create(EcommerceApi::class.java)
    }
}

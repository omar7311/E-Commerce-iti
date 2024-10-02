package com.example.e_commerce_iti.model.apis

import android.os.Build
import androidx.annotation.RequiresApi
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Base64
import java.util.concurrent.TimeUnit

// Define the AuthInterceptor
class AuthInterceptor : Interceptor {
    private val credentials = "9d796e0fcb9dc5aa8819e5de090726e6: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"

    @RequiresApi(Build.VERSION_CODES.O)
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val requestBuilder = originalRequest.newBuilder()

        // Add the Basic Authorization header
        val encodedCredentials = Base64.getEncoder().encodeToString(credentials.toByteArray())
        requestBuilder.header("Authorization", "Basic $encodedCredentials")

        val newRequest = requestBuilder.build()
        return chain.proceed(newRequest)
    }
}

object RetrofitHelper {
    // Create the AuthInterceptor instance
    private val authInterceptor = AuthInterceptor()

    private val client = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .addInterceptor(authInterceptor) // Add the interceptor here
        .build()

    // Update the BASE_URL to exclude credentials
    private val BASE_URL = "https://android-sv24-r3team1.myshopify.com/admin/api/2024-07/"

    private val retrofitInstance: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client) // Pass the OkHttpClient instance with interceptor
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // Lazily initialized service instance
    val service: EcommerceApi by lazy {
        retrofitInstance.create(EcommerceApi::class.java)
    }
}

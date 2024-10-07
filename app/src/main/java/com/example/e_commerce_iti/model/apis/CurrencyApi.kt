package com.example.e_commerce_iti.model.apis

import com.example.e_commerce_iti.model.pojos.currenyex.CurrencyExc
import retrofit2.http.GET
import retrofit2.http.Path

interface CurrencyApi {
    @GET("USD")
    suspend fun getCurrencies(): CurrencyExc
}
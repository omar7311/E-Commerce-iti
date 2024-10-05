package com.example.e_commerce_iti.model.apis

import com.example.e_commerce_iti.model.pojos.currenyex.CurrencyExc
import retrofit2.http.GET

interface CurrencyApi {
    @GET("{currency}")
    suspend fun getCurrencies(currency: String): CurrencyExc
}
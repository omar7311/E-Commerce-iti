package com.example.e_commerce_iti.model.local

import com.example.e_commerce_iti.model.pojos.currenyex.CurrencyExc
import kotlinx.coroutines.flow.Flow

interface IlocalDataSource {
    fun getCurrency(currency: String): Flow<CurrencyExc?>
    fun insertCurrency(currency: CurrencyExc)
    fun getChoosedCurrency():Flow<Pair<String,Float>>
    fun setChoosedCurrency(currency: String):Flow<Pair<String,Float>>

}
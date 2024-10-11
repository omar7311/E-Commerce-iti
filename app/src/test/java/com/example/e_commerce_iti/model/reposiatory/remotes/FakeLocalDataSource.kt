package com.example.e_commerce_iti.model.reposiatory.remotes

import com.example.e_commerce_iti.model.local.IlocalDataSource
import com.example.e_commerce_iti.model.pojos.currenyex.CurrencyExc
import kotlinx.coroutines.flow.Flow

class FakeLocalDataSource: IlocalDataSource {
    override fun getCurrency(currency: String): Flow<CurrencyExc?> {
        TODO("Not yet implemented")
    }

    override fun insertCurrency(currency: CurrencyExc) {
        TODO("Not yet implemented")
    }

    override fun getChoosedCurrency(): Flow<Pair<String, Float>> {
        TODO("Not yet implemented")
    }

    override fun setChoosedCurrency(currency: String): Flow<Pair<String, Float>> {
        TODO("Not yet implemented")
    }
}
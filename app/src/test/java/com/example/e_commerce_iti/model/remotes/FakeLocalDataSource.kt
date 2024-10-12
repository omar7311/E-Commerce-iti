package com.example.e_commerce_iti.model.remotes

import android.content.SharedPreferences
import com.example.e_commerce_iti.model.local.IlocalDataSource
import com.example.e_commerce_iti.model.pojos.currenyex.CurrencyExc
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class FakeLocalDataSource(val sharedPreferences: SharedPreferences): IlocalDataSource {
    companion object{
        const val getChoosedCurrency="getChoosedCurrency"
        const val currentCurrency="currentCurrency"
        const val currencies="currencies"
    }
    override fun getCurrency(currency: String): Flow<CurrencyExc?> {
        val data = sharedPreferences.getString(currencies, null) ?: return flowOf(null)
        val gson = Gson().fromJson(data, CurrencyExc::class.java)
        val currentTime = Calendar.getInstance().timeInMillis
        return if (compare(currentTime, gson.time_next_update_utc!!)) {
            sharedPreferences.edit().clear().apply()
            flowOf(null)
        } else {
            flowOf(gson)
        }
    }
    override fun insertCurrency(currency: CurrencyExc) {
        sharedPreferences.edit().putString(currencies, Gson().toJson(currency)).apply()
    }

    override fun getChoosedCurrency(): Flow<Pair<String, Float>> {
        val name=sharedPreferences.getString("cname","EGP")
        val rate=sharedPreferences.getFloat("crate",1.0f)
        return flowOf(Pair(name!!,rate))
    }

    override fun setChoosedCurrency(adddd: String):Flow<Pair<String, Float>> {
        val currenceis=sharedPreferences.getString(currencies,null)
        val gson= Gson().fromJson(currenceis,CurrencyExc::class.java)
        return when(adddd){
            "USD"->{
                sharedPreferences.edit().putString("cname","USD").putFloat("crate",gson.conversion_rates!!.USD.toFloat()).apply()
                flowOf(Pair(adddd,gson.conversion_rates!!.USD.toFloat()))
            }
            "EGP"->{
                sharedPreferences.edit().putString("cname","EGP").putFloat("crate",gson.conversion_rates!!.EGP.toFloat()).apply()
                flowOf(Pair(adddd,gson.conversion_rates!!.EGP.toFloat()))
            }
            "SAR"->{
                sharedPreferences.edit().putString("cname","SAR").putFloat("crate",gson.conversion_rates!!.SAR.toFloat()).apply()
                flowOf(Pair(adddd,gson.conversion_rates!!.SAR.toFloat()))
            }
            "EUR"->{
                sharedPreferences.edit().putString("cname","EUR").putFloat("crate",gson.conversion_rates!!.EUR.toFloat()).apply()
                flowOf(Pair(adddd,gson.conversion_rates!!.EUR.toFloat()))
            }

            else -> {
                flowOf(Pair("EGP",1.0f))
            }
        }
    }

    private fun convertTime(time: String):Long{
        val format = SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH)
        val date = format.parse(time)
        val milliseconds = date?.time ?: 0
        return milliseconds
    }
    fun compare(currentTime:Long,endTime:String)=currentTime>convertTime(endTime)
}
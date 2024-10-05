package com.example.e_commerce_iti.model.local

import android.content.SharedPreferences
import android.provider.CalendarContract.Calendars
import com.example.e_commerce_iti.model.pojos.currenyex.CurrencyExc
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class LocalDataSourceImp(val sharedPreferences: SharedPreferences): IlocalDataSource {
    companion object{
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
       sharedPreferences.edit().putString(currencies,Gson().toJson(currency)).apply()
    }
    private fun convertTime(time: String):Long{
        val format = SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH)
        val date = format.parse(time)
        val milliseconds = date?.time ?: 0
        return milliseconds
    }
    fun compare(currentTime:Long,endTime:String)=currentTime>convertTime(endTime)
}
package com.example.e_commerce_iti.model.local

import android.content.SharedPreferences
import android.provider.CalendarContract.Calendars
import com.example.e_commerce_iti.currentUser
import com.example.e_commerce_iti.model.pojos.currenyex.CurrencyExc
import com.google.gson.Gson
import com.google.gson.JsonObject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class LocalDataSourceImp(private val sharedPreferences: SharedPreferences) : IlocalDataSource {
    companion object {
        const val CURRENT_CURRENCY = "currentCurrency"
        const val CURRENCIES_KEY = "currencies"
        var EMAIL_CURRENCY_PREFIX = currentUser.value?.email
    }

    override fun getCurrency(email: String): Flow<CurrencyExc?> {
        val key = getEmailKey(email)
        val data = sharedPreferences.getString(key, null) ?: return flowOf(null)
        val gson = Gson()
        val currency = gson.fromJson(data, CurrencyExc::class.java)
        val currentTime = Calendar.getInstance().timeInMillis
        return if (compare(currentTime, currency.time_next_update_utc!!)) {
            sharedPreferences.edit().remove(key).apply()
            flowOf(null)
        } else {
            flowOf(currency)
        }
    }

    override fun insertCurrency( currency: CurrencyExc) {
        val key = getEmailKey(currentUser.value?.email ?: "null")
        val gson = Gson()
        sharedPreferences.edit().putString(key, gson.toJson(currency)).apply()
    }

    override fun getChoosedCurrency(): Flow<Pair<String, Float>> {
        val key = getEmailKey(currentUser.value?.email ?: "null")
        val name = sharedPreferences.getString("${key}_name", "EGP")
        val rate = sharedPreferences.getFloat("${key}_rate", 1.0f)
        return flowOf(Pair(name!!, rate))
    }

    override fun setChoosedCurrency(currency: String): Flow<Pair<String, Float>> {
        val key = getEmailKey(currentUser.value?.email ?: "null")
        val currencyData = sharedPreferences.getString(key, null)
        if (currencyData == null) return flowOf(Pair("EGP", 1.0f))

        val gson = Gson()
        val currencyExc = gson.fromJson(currencyData, CurrencyExc::class.java)

        val (name, rate) = when (currency) {
            "USD" -> Pair("USD", currencyExc.conversion_rates?.USD)
            "EGP" -> Pair("EGP", currencyExc.conversion_rates?.EGP)
            "SAR" -> Pair("SAR", currencyExc.conversion_rates?.SAR)
            "EUR" -> Pair("EUR", currencyExc.conversion_rates?.EUR)
            else -> Pair("EGP", 1.0)
        }

        sharedPreferences.edit()
            .putString("${key}_name", name)
            .putFloat("${key}_rate", rate?.toFloat() ?: 1.0f)
            .apply()

        return flowOf(Pair(name, rate?.toFloat() ?: 1.0f))
    }

    private fun getEmailKey(email: String): String {
        return "$EMAIL_CURRENCY_PREFIX${email.hashCode()}"
    }

    private fun convertTime(time: String): Long {
        val format = SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH)
        val date = format.parse(time)
        return date?.time ?: 0
    }

    private fun compare(currentTime: Long, endTime: String) = currentTime > convertTime(endTime)
}
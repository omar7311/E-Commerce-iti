package com.example.e_commerce_iti.model.pojos.currenyex

data class CurrencyExc(
    var conversion_rates: ConversionRates?,
    var time_next_update_unix: Int?,
    var time_next_update_utc: String?
)
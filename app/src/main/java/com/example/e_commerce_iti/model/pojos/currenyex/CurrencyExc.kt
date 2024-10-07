package com.example.e_commerce_iti.model.pojos.currenyex

data class CurrencyExc(
    var base_code: String?,
    var conversion_rates: ConversionRates?,
    var documentation: String?,
    var result: String?,
    var terms_of_use: String?,
    var time_last_update_unix: Int?,
    var time_last_update_utc: String?,
    var time_next_update_unix: Int?,
    var time_next_update_utc: String?
)
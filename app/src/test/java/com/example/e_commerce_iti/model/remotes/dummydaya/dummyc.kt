package com.example.e_commerce_iti.model.remotes.dummydaya

import com.example.e_commerce_iti.model.pojos.currenyex.ConversionRates
import com.example.e_commerce_iti.model.pojos.currenyex.CurrencyExc

var dummycurrency=   CurrencyExc(
    conversion_rates = ConversionRates(
        EUR = 1.16,
        USD = 1.37,
        SAR = 5.14,
        EGP = 42.11
    ),

    time_next_update_unix = 1633111200,
    time_next_update_utc = "2024-10-12T12:00:00Z"
)
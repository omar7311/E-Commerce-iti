package com.example.e_commerce_iti.model.pojos.price_rules.requestedorder

data class TaxLineX(
    var channel_liable: Boolean?,
    var price: String?,
    var price_set: com.example.e_commerce_iti.model.pojos.price_rules.requestedorder.PriceSet?,
    var rate: Double?,
    var title: String?
)
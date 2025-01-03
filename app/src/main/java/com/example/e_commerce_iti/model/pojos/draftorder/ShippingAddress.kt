package com.example.e_commerce_iti.model.pojos.draftorder

import androidx.compose.runtime.saveable.Saver

data class ShippingAddress(
    var address1: String?= "",
    var address2: String?="",
    var city: String?="",
    var company: Any?="",
    var country: String?="",
    var country_code: String?="",
    var first_name: String?="",
    var last_name: String?="",
    var latitude: String?="",
    var longitude: String?="",
    var name: String?="",
    var phone: String?="",
    var province: String?="",
    var province_code: String?="",
    var zip: String?=""
)
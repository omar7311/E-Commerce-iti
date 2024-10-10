package com.example.e_commerce_iti.model.pojos.responsedorder

data class ShippingAddress(
    var address1: String? = null,
    var address2: String? = null,
    var city: String? = null,
    var company: Any? = null,
    var country: String? = null,
    var country_code: String? = null,
    var first_name: String? = null,
    var last_name: String? = null,
    var latitude: Double? = null,
    var longitude: Double? = null,
    var name: String? = null,
    var phone: String? = null,
    var province: String? = null,
    var province_code: String? = null,
    var zip: String? = null
)
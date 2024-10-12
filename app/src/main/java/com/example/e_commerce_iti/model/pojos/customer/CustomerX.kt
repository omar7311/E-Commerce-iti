package com.example.e_commerce_iti.model.pojos.customer
data class CustomerX(
    var addresses: List<Addresse?>? = null,
    var email: String? = null,
    var first_name: String? = null,
    var id: Long? = null,
    var last_name: String? = null,
    var phone: String? = null,
    var tax_exempt: Boolean? = null,
    var tax_exemptions: List<Any?>? = null,
    var total_spent: String? = null,
    var verified_email: Boolean? = null
)
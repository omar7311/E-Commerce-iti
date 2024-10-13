package com.example.e_commerce_iti.model.pojos.draftorder

data class Customer(
    var email: String?=null,
    var first_name: String?=null,
    var id: Long?=null,
    var last_name: String?=null,
    var last_order_id: Long?=null,
    var last_order_name: String?=null,
    var phone: String?=null,
    var tax_exempt: Boolean?=null,
    var tax_exemptions: List<TaxExemptions>?=null,
    var total_spent: String?=null,
    var updated_at: String?=null,
)
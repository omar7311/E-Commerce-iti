package com.example.e_commerce_iti.model.pojos.draftorder

data class Customer(
    var accepts_marketing: Boolean?=null,
    var addresses: Addresses?=null,
    var admin_graphql_api_id: String?=null,
    var created_at: String?=null,
    var currency: String?=null,
    var default_address: DefaultAddress?=null,
    var email: String?=null,
    var first_name: String?=null,
    var id: Long?=null,
    var last_name: String?=null,
    var last_order_id: Long?=null,
    var last_order_name: String?=null,
    var multipass_identifier: Any?=null,
    var note: Any?=null,
    var orders_count: String?=null,
    var phone: String?=null,
    var state: String?=null,
    var tags: String?=null,
    var tax_exempt: Boolean?=null,
    var tax_exemptions: List<TaxExemptions>?=null,
    var total_spent: String?=null,
    var updated_at: String?=null,
    var verified_email: Boolean?=null
)
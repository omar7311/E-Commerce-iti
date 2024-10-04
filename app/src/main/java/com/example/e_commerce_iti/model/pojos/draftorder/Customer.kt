package com.example.e_commerce_iti.model.pojos.draftorder

data class Customer(
    var accepts_marketing: Boolean?,
    var addresses: Addresses?,
    var admin_graphql_api_id: String?,
    var created_at: String?,
    var currency: String?,
    var default_address: DefaultAddress?,
    var email: String?,
    var first_name: String?,
    var id: Int?,
    var last_name: String?,
    var last_order_id: Int?,
    var last_order_name: String?,
    var multipass_identifier: Any?,
    var note: Any?,
    var orders_count: String?,
    var phone: String?,
    var state: String?,
    var tags: String?,
    var tax_exempt: Boolean?,
    var tax_exemptions: TaxExemptions?,
    var total_spent: String?,
    var updated_at: String?,
    var verified_email: Boolean?
)
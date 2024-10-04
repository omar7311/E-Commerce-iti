package com.example.e_commerce_iti.model.pojos.customer

data class CustomerX(
    var addresses: List<Addresse?>?,
    var admin_graphql_api_id: String?,
    var created_at: String?,
    var currency: String?,
    var default_address: DefaultAddress?,
    var email: String?,
    var email_marketing_consent: EmailMarketingConsent?,
    var first_name: String?,
    var id: Int?,
    var last_name: String?,
    var last_order_id: Any?,
    var last_order_name: Any?,
    var multipass_identifier: Any?,
    var note: Any?,
    var orders_count: Int?,
    var phone: String?,
    var sms_marketing_consent: SmsMarketingConsent?,
    var state: String?,
    var tags: String?,
    var tax_exempt: Boolean?,
    var tax_exemptions: List<Any?>?,
    var total_spent: String?,
    var updated_at: String?,
    var verified_email: Boolean?
)
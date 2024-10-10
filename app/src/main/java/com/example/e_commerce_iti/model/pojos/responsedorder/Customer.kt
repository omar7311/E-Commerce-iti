package com.example.e_commerce_iti.model.pojos.responsedorder

data class Customer(
    var admin_graphql_api_id: String? = null,
    var created_at: String? = null,
    var currency: String? = null,
    var default_address: DefaultAddress? = null,
    var email: String? = null,
    var email_marketing_consent: EmailMarketingConsent? = null,
    var first_name: String? = null,
    var id: Int? = null,
    var last_name: String? = null,
    var last_order_id: Int? = null,
    var last_order_name: String? = null,
    var multipass_identifier: Any? = null,
    var note: Any? = null,
    var orders_count: Int? = null,
    var phone: String? = null,
    var sms_marketing_consent: SmsMarketingConsent? = null,
    var state: String? = null,
    var tags: String? = null,
    var tax_exempt: Boolean? = null,
    var tax_exemptions: List<Any?>? = null,
    var total_spent: String? = null,
    var updated_at: String? = null,
    var verified_email: Boolean? = null
)
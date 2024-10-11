package com.example.e_commerce_iti.model.pojos.customer

data class SmsMarketingConsent(
    var consent_collected_from: String?=null,
    var consent_updated_at: Any?=null,
    var opt_in_level: String?=null,
    var state: String?=null
)
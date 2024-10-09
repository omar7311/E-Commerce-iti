package com.example.e_commerce_iti.model.pojos.invoice

data class DraftOrderInvoice(
    var bcc: List<String?>? = null,
    var custom_message: String? = null,
    var from: String? = null,
    var subject: String? = null,
    var to: String? = null
)
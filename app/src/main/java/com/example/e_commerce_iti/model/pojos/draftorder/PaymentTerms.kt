package com.example.e_commerce_iti.model.pojos.draftorder

data class PaymentTerms(
    var amount: Int?,
    var currency: String?,
    var due_in_days: Int?,
    var payment_schedules: List<PaymentSchedule?>?,
    var payment_terms_name: String?,
    var payment_terms_type: String?
)
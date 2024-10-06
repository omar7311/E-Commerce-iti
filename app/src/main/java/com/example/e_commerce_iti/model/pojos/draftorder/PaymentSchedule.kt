package com.example.e_commerce_iti.model.pojos.draftorder

data class PaymentSchedule(
    var amount: Int?,
    var completed_at: String?,
    var currency: String?,
    var due_at: String?,
    var expected_payment_method: String?,
    var issued_at: String?
)
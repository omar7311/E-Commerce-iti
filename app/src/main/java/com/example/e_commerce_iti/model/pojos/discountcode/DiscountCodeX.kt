package com.example.e_commerce_iti.model.pojos.discountcode

data class DiscountCodeX(
    val code: String,
    val created_at: String,
    val id: Long,
    val price_rule_id: Long,
    val updated_at: String,
    val usage_count: Int
)
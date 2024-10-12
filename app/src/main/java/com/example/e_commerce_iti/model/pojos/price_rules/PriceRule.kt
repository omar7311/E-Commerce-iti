package com.example.e_commerce_iti.model.pojos.price_rules

data class PriceRule(
    val admin_graphql_api_id: String,
    val allocation_method: String,
    val ends_at: String,
    val id: Long,
    val target_type: String,
    val title: String,
    val value: String,
    val value_type: String
)
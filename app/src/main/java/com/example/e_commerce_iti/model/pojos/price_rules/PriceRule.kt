package com.example.e_commerce_iti.model.pojos.price_rules

data class PriceRule(
    val admin_graphql_api_id: String,
    val allocation_limit: Any?=null,
    val allocation_method: String,
    val created_at: String,
    val customer_segment_prerequisite_ids: List<Any>,
    val customer_selection: String,
    val ends_at: String,
    val entitled_collection_ids: List<Any>,
    val entitled_country_ids: List<Any>,
    val entitled_product_ids: List<Any>,
    val entitled_variant_ids: List<Any>,
    val id: Long,
    val once_per_customer: Boolean,
    val prerequisite_collection_ids: List<Any>,
    val prerequisite_customer_ids: List<Any>,
    val prerequisite_product_ids: List<Any>,
    val prerequisite_quantity_range: Any?,
    val prerequisite_shipping_price_range: Any?,
    val prerequisite_subtotal_range: PrerequisiteSubtotalRange,
    val prerequisite_to_entitlement_purchase: PrerequisiteToEntitlementPurchase,
    val prerequisite_to_entitlement_quantity_ratio: PrerequisiteToEntitlementQuantityRatio,
    val prerequisite_variant_ids: List<Any>,
    val starts_at: String,
    val target_selection: String,
    val target_type: String,
    val title: String,
    val updated_at: String,
    val usage_limit: Int,
    val value: String,
    val value_type: String
)
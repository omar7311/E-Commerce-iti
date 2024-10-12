package com.example.e_commerce_iti.model.remotes.dummydaya

import com.example.e_commerce_iti.model.pojos.price_rules.PriceRule

val list = listOf(
    PriceRule(
    admin_graphql_api_id = "gid://shopify/PriceRule/123456789",
    allocation_method = "across",
    created_at = "2024-09-01T08:00:00Z",
    customer_segment_prerequisite_ids = listOf(),
    customer_selection = "all",
    ends_at = "2024-12-31T23:59:59Z",
    entitled_collection_ids = listOf(),
    entitled_country_ids = listOf(),
    entitled_product_ids = listOf(),
    entitled_variant_ids = listOf(),
    id = 987654321L,
    once_per_customer = true,
    prerequisite_collection_ids = listOf(),
    prerequisite_customer_ids = listOf(),
    prerequisite_product_ids = listOf(),
    prerequisite_quantity_range = null,
    prerequisite_shipping_price_range = null,
    prerequisite_subtotal_range = PrerequisiteSubtotalRange(greater_than_or_equal_to = "50.00"),
    prerequisite_to_entitlement_purchase = PrerequisiteToEntitlementPurchase(prerequisite_amount = 1),
    prerequisite_to_entitlement_quantity_ratio = PrerequisiteToEntitlementQuantityRatio(
        prerequisite_quantity = 1,
        entitled_quantity = 1
    ),
    prerequisite_variant_ids = listOf(),
    starts_at = "2024-09-01T08:00:00Z",
    target_selection = "all",
    target_type = "line_item",
    title = "10% Off Orders Over $50",
    updated_at = "2024-09-15T12:00:00Z",
    usage_limit = 100,
    value = "10.0",
    value_type = "percentage"
),
    PriceRule(
        admin_graphql_api_id = "gid://shopify/PriceRule/33323",
        allocation_method = "across",
        created_at = "2024-09-01T08:00:00Z",
        customer_segment_prerequisite_ids = listOf(),
        customer_selection = "all",
        ends_at = "2024-12-31T23:59:59Z",
        entitled_collection_ids = listOf(),
        entitled_country_ids = listOf(),
        entitled_product_ids = listOf(),
        entitled_variant_ids = listOf(),
        id = 987654322L,
        once_per_customer = true,
        prerequisite_collection_ids = listOf(),
        prerequisite_customer_ids = listOf(),
        prerequisite_product_ids = listOf(),
        prerequisite_quantity_range = null,
        prerequisite_shipping_price_range = null,
        prerequisite_subtotal_range = PrerequisiteSubtotalRange(greater_than_or_equal_to = "70.00"),
        prerequisite_to_entitlement_purchase = PrerequisiteToEntitlementPurchase(prerequisite_amount = 1),
        prerequisite_to_entitlement_quantity_ratio = PrerequisiteToEntitlementQuantityRatio(
            prerequisite_quantity = 1,
            entitled_quantity = 1
        ),
        prerequisite_variant_ids = listOf(),
        starts_at = "2024-09-01T08:00:00Z",
        target_selection = "all",
        target_type = "line_item",
        title = "20% Off Orders Over $50",
        updated_at = "2024-09-15T12:00:00Z",
        usage_limit = 200,
        value = "40.0",
        value_type = "percentage"
    ),
)
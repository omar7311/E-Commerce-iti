package com.example.e_commerce_iti.model.remotes.dummydaya

import com.example.e_commerce_iti.model.pojos.price_rules.PriceRule

val list = listOf(
    PriceRule(
    admin_graphql_api_id = "gid://shopify/PriceRule/123456789",
    allocation_method = "across",

    ends_at = "2024-12-31T23:59:59Z",

    id = 987654321L,

    target_type = "line_item",
    title = "10% Off Orders Over $50",

    value = "10.0",
    value_type = "percentage"
),
    PriceRule(
        admin_graphql_api_id = "gid://shopify/PriceRule/33323",
        allocation_method = "across",

        ends_at = "2024-12-31T23:59:59Z",

        id = 987654322L,

        target_type = "line_item",
        title = "20% Off Orders Over $50",

        value = "40.0",
        value_type = "percentage"
    ),
)
package com.example.e_commerce_iti.model.reposiatory.remotes.dummydaya

import com.example.e_commerce_iti.model.pojos.discountcode.DiscountCodeX

val dummyDiscountCodes = listOf(
    DiscountCodeX(
        code = "SAVE10",
        created_at = "2024-01-05T10:30:00Z",
        id = 301L,
        price_rule_id = 1001L,
        updated_at = "2024-02-10T12:00:00Z",
        usage_count = 15
    ),
    DiscountCodeX(
        code = "WELCOME15",
        created_at = "2024-02-15T09:15:00Z",
        id = 302L,
        price_rule_id = 1002L,
        updated_at = "2024-03-01T14:00:00Z",
        usage_count = 8
    ),
    DiscountCodeX(
        code = "FREESHIP",
        created_at = "2024-03-20T16:45:00Z",
        id = 303L,
        price_rule_id = 1003L,
        updated_at = "2024-03-25T18:30:00Z",
        usage_count = 25
    ),
    DiscountCodeX(
        code = "SPRING20",
        created_at = "2024-04-10T11:00:00Z",
        id = 304L,
        price_rule_id = 1004L,
        updated_at = "2024-04-15T13:45:00Z",
        usage_count = 12
    ),
    DiscountCodeX(
        code = "SUMMER25",
        created_at = "2024-05-01T08:00:00Z",
        id = 305L,
        price_rule_id = 1005L,
        updated_at = "2024-05-10T17:00:00Z",
        usage_count = 5
    )
)
package com.example.e_commerce_iti.model.pojos.discountcode

data class DiscountCode(
    val discount_codes: List<DiscountCodeX>
)
data class FoundedDiscountCode(
    val discount_code: DiscountCodeX
)
package com.example.e_commerce_iti.model.pojos.draftorder

data class LineItems(
    var applied_discount: AppliedDiscount?,
    var custom: Boolean?,
    var fulfillable_quantity: Int?,
    var fulfillment_service: String?,
    var gift_card: Boolean?,
    var grams: Int?,
    var id: Int?,
    var name: String?,
    var price: String?,
    var product_id: Int?,
    var properties: List<Property>?,
    var quantity: Int?,
    var requires_shipping: Boolean?,
    var sku: String?,
    var tax_lines: List<TaxLine>?,
    var taxable: Boolean?,
    var title: String?,
    var variant_id: Int?,
    var variant_title: String?,
    var vendor: String?
)
package com.example.e_commerce_iti.model.pojos.price_rules.requestedorder

data class LineItem(
    var admin_graphql_api_id: String?,
    var attributed_staffs: List<Any?>?,
    var current_quantity: Int?,
    var discount_allocations: List<Any?>?,
    var duties: List<Any?>?,
    var fulfillable_quantity: Int?,
    var fulfillment_service: String?,
    var fulfillment_status: Any?,
    var gift_card: Boolean?,
    var grams: Int?,
    var id: Int?,
    var name: String?,
    var price: String?,
    var price_set: com.example.e_commerce_iti.model.pojos.price_rules.requestedorder.PriceSet?,
    var product_exists: Boolean?,
    var product_id: Any?,
    var properties: List<Any?>?,
    var quantity: Int?,
    var requires_shipping: Boolean?,
    var sku: Any?,
    var tax_lines: List<com.example.e_commerce_iti.model.pojos.price_rules.requestedorder.TaxLineX>?,
    var taxable: Boolean?,
    var title: String?,
    var total_discount: String?,
    var total_discount_set: com.example.e_commerce_iti.model.pojos.price_rules.requestedorder.TotalDiscountSet?,
    var variant_id: Any?,
    var variant_inventory_management: Any?,
    var variant_title: Any?,
    var vendor: Any?
)
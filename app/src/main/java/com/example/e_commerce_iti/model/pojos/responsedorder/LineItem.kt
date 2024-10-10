package com.example.e_commerce_iti.model.pojos.responsedorder

data class LineItem(
    var admin_graphql_api_id: String? = null,
    var applied_discount: Any? = null,
    var custom: Boolean? = null,
    var fulfillment_service: String? = null,
    var gift_card: Boolean? = null,
    var grams: Int? = null,
    var id: Int? = null,
    var name: String? = null,
    var price: String? = null,
    var product_id: Int? = null,
    var properties: List<Any?>? = null,
    var quantity: Int? = null,
    var requires_shipping: Boolean? = null,
    var sku: String? = null,
    var tax_lines: List<Any?>? = null,
    var taxable: Boolean? = null,
    var title: String? = null,
    var variant_id: Int? = null,
    var variant_title: String? = null,
    var vendor: Any? = null
)
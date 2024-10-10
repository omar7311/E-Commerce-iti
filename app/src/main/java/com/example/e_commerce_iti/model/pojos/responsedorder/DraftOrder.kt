package com.example.e_commerce_iti.model.pojos.responsedorder

data class DraftOrder(
    var admin_graphql_api_id: String? = "",
    var applied_discount: AppliedDiscount? = AppliedDiscount(),
    var billing_address: BillingAddress? = BillingAddress(),
    var completed_at: String? = "",
    var created_at: String? = "",
    var currency: String? = "",
    var customer: Customer? = Customer(),
    var email: String? = "",
    var id: Int? = 0,
    var invoice_sent_at: Any? = Any(),
    var invoice_url: String? = "",
    var line_items: List<LineItem>? = listOf(),
    var name: String? = "",
    var note: String? = "",
    var note_attributes: List<Any?>? = listOf(),
    var order_id: Int? = 0,
    var payment_terms: Any? = Any(),
    var presentment_currency: String? = "",
    var shipping_address: ShippingAddress? = ShippingAddress(),
    var shipping_line: ShippingLine? = ShippingLine(),
    var status: String? = "",
    var subtotal_price: String? = "",
    var subtotal_price_set: SubtotalPriceSet? = SubtotalPriceSet(),
    var tags: String? = "",
    var tax_exempt: Boolean? = false,
    var tax_lines: List<Any?>? = listOf(),
    var taxes_included: Boolean? = false,
    var total_additional_fees_set: Any? = Any(),
    var total_discounts_set: TotalDiscountsSet? = TotalDiscountsSet(),
    var total_duties_set: Any? = Any(),
    var total_line_items_price_set: TotalLineItemsPriceSet? = TotalLineItemsPriceSet(),
    var total_price: String? = "",
    var total_price_set: TotalPriceSet? = TotalPriceSet(),
    var total_shipping_price_set: TotalShippingPriceSet? = TotalShippingPriceSet(),
    var total_tax: String? = "",
    var total_tax_set: TotalTaxSet? = TotalTaxSet(),
    var updated_at: String? = ""
)
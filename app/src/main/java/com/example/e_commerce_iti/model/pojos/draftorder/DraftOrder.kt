package com.example.e_commerce_iti.model.pojos.draftorder

data class DraftOrder(
    var applied_discount: AppliedDiscount?=null,
    var billing_address: BillingAddress?=null,
    var customer: Customer?=null,
    var email: String?=null,
    var id: Long?=null,
    var invoice_sent_at: String?=null,
    var line_items: List<LineItems> = emptyList(),
    var name: String?=null,
    var order_id: OrderId?=null,
    var shipping_address: ShippingAddress?=null,
    var subtotal_price: String?=null,
    var tax_exempt: Boolean?=null,
    var total_price: String?=null,
    var total_tax: String?=null,
)
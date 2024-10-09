package com.example.e_commerce_iti.model.pojos.price_rules.requestedorder

data class Order(
    var admin_graphql_api_id: String?,
    var app_id: Int?,
    var billing_address: Any?,
    var browser_ip: Any?,
    var buyer_accepts_marketing: Boolean?,
    var cancel_reason: Any?,
    var cancelled_at: Any?,
    var cart_token: Any?,
    var checkout_id: Any?,
    var checkout_token: Any?,
    var client_details: Any?,
    var closed_at: Any?,
    var confirmation_number: String?,
    var confirmed: Boolean?,
    var contact_email: Any?,
    var created_at: String?,
    var currency: String?,
    var current_subtotal_price: String?,
    var current_subtotal_price_set: com.example.e_commerce_iti.model.pojos.price_rules.requestedorder.CurrentSubtotalPriceSet?,
    var current_total_additional_fees_set: Any?,
    var current_total_discounts: String?,
    var current_total_discounts_set: com.example.e_commerce_iti.model.pojos.price_rules.requestedorder.CurrentTotalDiscountsSet?,
    var current_total_duties_set: Any?,
    var current_total_price: String?,
    var current_total_price_set: com.example.e_commerce_iti.model.pojos.price_rules.requestedorder.CurrentTotalPriceSet?,
    var current_total_tax: String?,
    var current_total_tax_set: com.example.e_commerce_iti.model.pojos.price_rules.requestedorder.CurrentTotalTaxSet?,
    var customer: Any?,
    var customer_locale: Any?,
    var device_id: Any?,
    var discount_applications: List<Any?>?,
    var discount_codes: List<Any?>?,
    var duties_included: Boolean?,
    var email: String?,
    var estimated_taxes: Boolean?,
    var financial_status: String?,
    var fulfillment_status: Any?,
    var fulfillments: List<Any?>?,
    var id: Int?,
    var landing_site: Any?,
    var landing_site_ref: Any?,
    var line_items: List<com.example.e_commerce_iti.model.pojos.price_rules.requestedorder.LineItem>?,
    var location_id: Any?,
    var merchant_business_entity_id: String?,
    var merchant_of_record_app_id: Any?,
    var name: String?,
    var note: Any?,
    var note_attributes: List<Any?>?,
    var number: Int?,
    var order_number: Int?,
    var order_status_url: String?,
    var original_total_additional_fees_set: Any?,
    var original_total_duties_set: Any?,
    var payment_gateway_names: List<String>?,
    var payment_terms: Any?,
    var phone: Any?,
    var po_number: Any?,
    var presentment_currency: String?,
    var processed_at: String?,
    var reference: Any?,
    var referring_site: Any?,
    var refunds: List<Any?>?,
    var shipping_address: Any?,
    var shipping_lines: List<Any?>?,
    var source_identifier: Any?,
    var source_name: String?,
    var source_url: Any?,
    var subtotal_price: String?,
    var subtotal_price_set: com.example.e_commerce_iti.model.pojos.price_rules.requestedorder.SubtotalPriceSet?,
    var tags: String?,
    var tax_exempt: Boolean?,
    var tax_lines: List<com.example.e_commerce_iti.model.pojos.price_rules.requestedorder.TaxLineX>?,
    var taxes_included: Boolean?,
    var test: Boolean?,
    var token: String?,
    var total_cash_rounding_payment_adjustment_set: com.example.e_commerce_iti.model.pojos.price_rules.requestedorder.TotalCashRoundingPaymentAdjustmentSet?,
    var total_cash_rounding_refund_adjustment_set: com.example.e_commerce_iti.model.pojos.price_rules.requestedorder.TotalCashRoundingRefundAdjustmentSet?,
    var total_discounts: String?,
    var total_discounts_set: com.example.e_commerce_iti.model.pojos.price_rules.requestedorder.TotalDiscountsSet?,
    var total_line_items_price: String?,
    var total_line_items_price_set: com.example.e_commerce_iti.model.pojos.price_rules.requestedorder.TotalLineItemsPriceSet?,
    var total_outstanding: String?,
    var total_price: String?,
    var total_price_set: com.example.e_commerce_iti.model.pojos.price_rules.requestedorder.TotalPriceSet?,
    var total_shipping_price_set: com.example.e_commerce_iti.model.pojos.price_rules.requestedorder.TotalShippingPriceSet?,
    var total_tax: String?,
    var total_tax_set: com.example.e_commerce_iti.model.pojos.price_rules.requestedorder.TotalTaxSet?,
    var total_tip_received: String?,
    var total_weight: Int?,
    var updated_at: String?,
    var user_id: Any?
)
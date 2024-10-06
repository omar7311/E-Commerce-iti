package com.example.e_commerce_iti.model.pojos.draftorder

data class DraftOrder(
    var applied_discount: AppliedDiscount?,
    var billing_address: BillingAddress?,
    var completed_at: String?,
    var created_at: String?,
    var currency: String?,
    var customer: Customer?,
    var email: String?,
    var id: Int?,
    var invoice_sent_at: String?,
    var invoice_url: String?,
    var line_items: LineItems?,
    var name: String?,
    var note: Any?,
    var note_attributes: List<NoteAttribute>?,
    var order_id: OrderId?,
    var payment_terms: PaymentTerms?,
    var shipping_address: ShippingAddress?,
    var shipping_line: ShippingLine?,
    var source_name: String?,
    var status: String?,
    var subtotal_price: String?,
    var tags: String?,
    var tax_exempt: Boolean?,
    var tax_exemptions: List<String>?,
    var tax_lines: List<TaxLineX>?,
    var taxes_included: Boolean?,
    var total_price: String?,
    var total_tax: String?,
    var updated_at: String?
)
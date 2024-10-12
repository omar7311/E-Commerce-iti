package com.example.e_commerce_iti.model.pojos.draftorder

data class LineItems(
    var applied_discount: AppliedDiscount?=null,
    var id: Long?=null,
    var name: String?=null,
    var price: String?=null,
    var product_id: Long?=null,
    var quantity: Long?=null,
    var taxable: Boolean?=null,
    var title: String?=null,
    var variant_id: Long?=null,
    var variant_title: String?=null,
    var vendor: String?=null,
    var tax_lines: List<TaxLine>?=null,

)
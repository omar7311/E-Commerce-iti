package com.example.e_commerce_iti.model.pojos

data class OrderResponse( val orders: List<Order>)

data class Order(
    val id: Long,
    val confirmationNumber: String?,
    val contactEmail: String?,
    val subtotal: String,
    val total: String,
    val totalCost: String,
    val createdAt: String,
    val currency: String,
    val discount: String?,
    val orderStatus: String,
    val customerId: Long,
    val shippingAddress: ShippingAddress,
    val lineItems: List<LineItem>
)


data class LineItem(
    val id: Long,
    val product: Product,
    val quantity: Int,
    val totalDiscount: String?,
    val fulfillmentStatus: String?,
    val requiresShipping: Boolean,
    val taxable: Boolean
)

data class ShippingAddress(
    val firstName: String,
    val lastName: String,
    val address1: String,
    val address2: String?,
    val city: String,
    val province: String?,
    val country: String,
    val zip: String,
    val phone: String?
)

data class Customer(
    val id: Long,
    val email: String,
    val firstName: String,
    val lastName: String,
    val phone: String?,
    val verifiedEmail: Boolean
)

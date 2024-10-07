package com.example.e_commerce_iti.model.pojos

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class OrdersResponse(
    val orders: List<Order>
)

data class Order(
    val id: Long,
    @SerializedName("created_at")
    val createdAt: String,
    val name: String,
    @SerializedName("order_number")
    val orderNumber: Int,
    @SerializedName("current_total_price")
    val currentTotalPrice: String,
    @SerializedName("current_subtotal_price")
    val currentSubtotalPrice: String,
    @SerializedName("current_total_tax")
    val currentTotalTax: String,
    @SerializedName("total_discounts")
    val totalDiscounts: String,
    @SerializedName("total_shipping_price_set")
    val totalShippingPriceSet: PriceSet,
    val customer: Customer,
    @SerializedName("shipping_address")
    val shippingAddress: Address,
    @SerializedName("line_items")
    val lineItems: List<LineItem>,
    val currency: String,
    @SerializedName("financial_status")
    val financialStatus: String,
    @SerializedName("fulfillment_status")
    val fulfillmentStatus: String?,
    @SerializedName("order_status")
    val orderStatus: String? = null
) {
    fun getOverallStatus(): String {
        return orderStatus ?: when {
            fulfillmentStatus == "fulfilled" && financialStatus == "paid" -> "Completed"
            fulfillmentStatus == "fulfilled" -> "Shipped"
            financialStatus == "paid" -> "Paid"
            financialStatus == "pending" -> "Pending Payment"
            else -> "Processing"
        }
    }
}

data class Customer(
    @SerializedName("first_name")
    val firstName: String,
    @SerializedName("last_name")
    val lastName: String,
    val email: String,
    val phone: String?
)

data class Address(
    @SerializedName("first_name")
    val firstName: String,
    @SerializedName("last_name")
    val lastName: String,
    val address1: String,
    val city: String,
    val country: String,
    val zip: String?,
    val phone: String?
)

data class PriceSet(
    @SerializedName("shop_money")
    val shopMoney: Money,
    @SerializedName("presentment_money")
    val presentmentMoney: Money
)

data class Money(
    val amount: String,
    @SerializedName("currency_code")
    val currencyCode: String
)

data class LineItem(
    val id: Long,
    @SerializedName("product_id")
    val productId: Long,
    val name: String,
    val price: String,
    val quantity: Int,
    val sku: String?,
    val vendor: String?,
    @SerializedName("total_discount")
    val totalDiscount: String
)

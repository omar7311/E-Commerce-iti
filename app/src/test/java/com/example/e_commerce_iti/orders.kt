package com.example.e_commerce_iti

import java.time.LocalDateTime

data class ShopifyOrder(
    val id: Long,
    val email: String,
    val createdAt: LocalDateTime,
    val totalPrice: Double,
    val subtotalPrice: Double,
    val totalTax: Double,
    val currency: String,
    val financialStatus: String,
    val fulfillmentStatus: String?,
    val orderNumber: Int,
    val orderStatusUrl: String,
    val lineItems: List<LineItem>,
    val shippingAddress: Address,
    val billingAddress: Address
)

data class LineItem(
    val id: Long,
    val productId: Long,
    val variantId: Long,
    val title: String,
    val variantTitle: String,
    val sku: String,
    val quantity: Int,
    val price: Double
)

data class Address(
    val firstName: String,
    val lastName: String,
    val address1: String,
    val address2: String?,
    val city: String,
    val province: String,
    val country: String,
    val zip: String,
    val phone: String
)

val fakeOrders = listOf(
    ShopifyOrder(
        id = 1001,
        email = "john.doe@example.com",
        createdAt = LocalDateTime.of(2024, 10, 10, 14, 30),
        totalPrice = 89.97,
        subtotalPrice = 84.99,
        totalTax = 4.98,
        currency = "USD",
        financialStatus = "paid",
        fulfillmentStatus = "fulfilled",
        orderNumber = 1001,
        orderStatusUrl = "https://fakeshopify.com/orders/1001/authenticate?key=abcdef123456",
        lineItems = listOf(
            LineItem(
                id = 10001,
                productId = 1,
                variantId = 101,
                title = "Eco-Friendly Bamboo Toothbrush",
                variantTitle = "Adult Size",
                sku = "BAMB-BRUSH-A",
                quantity = 2,
                price = 4.99
            ),
            LineItem(
                id = 10002,
                productId = 2,
                variantId = 201,
                title = "Smart Home Security Camera",
                variantTitle = "White",
                sku = "SEC-CAM-W",
                quantity = 1,
                price = 79.99
            )
        ),
        shippingAddress = Address(
            firstName = "John",
            lastName = "Doe",
            address1 = "123 Main St",
            address2 = "Apt 4B",
            city = "Anytown",
            province = "CA",
            country = "USA",
            zip = "12345",
            phone = "555-123-4567"
        ),
        billingAddress = Address(
            firstName = "John",
            lastName = "Doe",
            address1 = "123 Main St",
            address2 = "Apt 4B",
            city = "Anytown",
            province = "CA",
            country = "USA",
            zip = "12345",
            phone = "555-123-4567"
        )
    ),
    ShopifyOrder(
        id = 1002,
        email = "jane.smith@example.com",
        createdAt = LocalDateTime.of(2024, 10, 11, 9, 15),
        totalPrice = 164.98,
        subtotalPrice = 154.98,
        totalTax = 10.00,
        currency = "USD",
        financialStatus = "paid",
        fulfillmentStatus = "unfulfilled",
        orderNumber = 1002,
        orderStatusUrl = "https://fakeshopify.com/orders/1002/authenticate?key=ghijkl789012",
        lineItems = listOf(
            LineItem(
                id = 10003,
                productId = 3,
                variantId = 301,
                title = "Vintage Leather Messenger Bag",
                variantTitle = "Brown",
                sku = "VINT-BAG-BR",
                quantity = 1,
                price = 129.99
            ),
            LineItem(
                id = 10004,
                productId = 4,
                variantId = 401,
                title = "Organic Green Tea Matcha Powder",
                variantTitle = "30g Tin",
                sku = "MATCHA-30",
                quantity = 1,
                price = 24.99
            )
        ),
        shippingAddress = Address(
            firstName = "Jane",
            lastName = "Smith",
            address1 = "456 Elm St",
            address2 = null,
            city = "Other City",
            province = "NY",
            country = "USA",
            zip = "67890",
            phone = "555-987-6543"
        ),
        billingAddress = Address(
            firstName = "Jane",
            lastName = "Smith",
            address1 = "456 Elm St",
            address2 = null,
            city = "Other City",
            province = "NY",
            country = "USA",
            zip = "67890",
            phone = "555-987-6543"
        )
    )
    // Add more orders here as needed
)
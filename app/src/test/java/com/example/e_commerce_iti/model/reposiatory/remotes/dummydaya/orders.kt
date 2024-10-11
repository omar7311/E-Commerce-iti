package com.example.e_commerce_iti.model.reposiatory.remotes.dummydaya

import com.example.e_commerce_iti.model.pojos.Address
import com.example.e_commerce_iti.model.pojos.Customer
import com.example.e_commerce_iti.model.pojos.LineItem
import com.example.e_commerce_iti.model.pojos.Money
import com.example.e_commerce_iti.model.pojos.Order
import com.example.e_commerce_iti.model.pojos.OrdersResponse
import com.example.e_commerce_iti.model.pojos.PriceSet
    val order1 =  Order(
        id = 100002L,
        createdAt = "2024-10-02T12:00:00Z",
        name = "#1002",
        orderNumber = 1002,
        currentTotalPrice = "75.00",
        currentSubtotalPrice = "70.00",
        currentTotalTax = "5.00",
        totalDiscounts = "0.00",
        totalShippingPriceSet = PriceSet(
            shopMoney = Money(amount = "10.00", currencyCode = "USD"),
            presentmentMoney = Money(amount = "10.00", currencyCode = "USD")
        ),
        customer = Customer(
            id = 1000001L,
            firstName = "Bob",
            lastName = "Johnson",
            email = "bob.johnson@example.com",
            phone = "+987654321"
        ),
        shippingAddress = Address(
            firstName = "Bob",
            lastName = "Johnson",
            address1 = "456 Oak Avenue",
            city = "Hometown",
            country = "USA",
            zip = "54321",
            phone = "+987654321"
        ),
        lineItems = listOf(
            LineItem(
                id = 200002L,
                productId = 300002L,
                name = "Reusable Cotton Shopping Bag",
                price = "15.00",
                quantity = 2,
                sku = "BAG-002",
                vendor = "EcoBag",
                totalDiscount = "0.00"
            )
        ),
        currency = "USD",
        financialStatus = "paid",
        fulfillmentStatus = "fulfilled",
        orderStatus = null
    )
    val order2 =  Order(
        id = 100003L,
        createdAt = "2024-10-03T14:30:00Z",
        name = "#1003",
        orderNumber = 1003,
        currentTotalPrice = "220.00",
        currentSubtotalPrice = "200.00",
        currentTotalTax = "20.00",
        totalDiscounts = "30.00",
        totalShippingPriceSet = PriceSet(
            shopMoney = Money(amount = "15.00", currencyCode = "USD"),
            presentmentMoney = Money(amount = "15.00", currencyCode = "USD")
        ),
        customer = Customer(
            id = 1000003L,
            firstName = "Charlie",
            lastName = "Williams",
            email = "charlie.williams@example.com",
            phone = "+654321789"
        ),
        shippingAddress = Address(
            firstName = "Charlie",
            lastName = "Williams",
            address1 = "789 Pine Lane",
            city = "Capital City",
            country = "USA",
            zip = "56789",
            phone = "+654321789"
        ),
        lineItems = listOf(
            LineItem(
                id = 200003L,
                productId = 300003L,
                name = "Recycled Aluminum Water Bottle",
                price = "25.00",
                quantity = 4,
                sku = "BOTTLE-003",
                vendor = "EcoDrink",
                totalDiscount = "10.00"
            )
        ),
        currency = "USD",
        financialStatus = "paid",
        fulfillmentStatus = "shipped",
        orderStatus = null
    )
    val order3  =   Order(
        id = 100004L,
        createdAt = "2024-10-04T16:45:00Z",
        name = "#1004",
        orderNumber = 1004,
        currentTotalPrice = "90.00",
        currentSubtotalPrice = "80.00",
        currentTotalTax = "10.00",
        totalDiscounts = "5.00",
        totalShippingPriceSet = PriceSet(
            shopMoney = Money(amount = "7.00", currencyCode = "USD"),
            presentmentMoney = Money(amount = "7.00", currencyCode = "USD")
        ),
        customer = Customer(
            id = 1000003L,
            firstName = "Daisy",
            lastName = "Clark",
            email = "daisy.clark@example.com",
            phone = "+321789654"
        ),
        shippingAddress = Address(
            firstName = "Daisy",
            lastName = "Clark",
            address1 = "101 Birch Road",
            city = "Riverside",
            country = "USA",
            zip = "13579",
            phone = "+321789654"
        ),
        lineItems = listOf(
            LineItem(
                id = 200004L,
                productId = 300004L,
                name = "Biodegradable Plant Pots",
                price = "10.00",
                quantity = 6,
                sku = "POTS-004",
                vendor = "GreenHome",
                totalDiscount = "5.00"
            )
        ),
        currency = "USD",
        financialStatus = "pending",
        fulfillmentStatus = null,
        orderStatus = "Processing"
    )

    val order4 =  Order(
        id = 100005L,
        createdAt = "2024-10-05T18:15:00Z",
        name = "#1005",
        orderNumber = 1005,
        currentTotalPrice = "50.00",
        currentSubtotalPrice = "45.00",
        currentTotalTax = "5.00",
        totalDiscounts = "0.00",
        totalShippingPriceSet = PriceSet(
            shopMoney = Money(amount = "3.00", currencyCode = "USD"),
            presentmentMoney = Money(amount = "3.00", currencyCode = "USD")
        ),
        customer = Customer(
            id = 1000001L,
            firstName = "Ethan",
            lastName = "Wright",
            email = "ethan.wright@example.com",
            phone = "+789123456"
        ),
        shippingAddress = Address(
            firstName = "Ethan",
            lastName = "Wright",
            address1 = "202 Cedar Boulevard",
            city = "Lakeside",
            country = "USA",
            zip = "24680",
            phone = "+789123456"
        ),
        lineItems = listOf(
            LineItem(
                id = 200005L,
                productId = 300005L,
                name = "Reusable Metal Straws",
                price = "5.00",
                quantity = 9,
                sku = "STRAW-005",
                vendor = "EcoStraw",
                totalDiscount = "0.00"
            )
        ),
        currency = "USD",
        financialStatus = "paid",
        fulfillmentStatus = null,
        orderStatus = "Processing"
    )
val dummyOrders = OrdersResponse(

    orders = listOf(
       order1,
      order2,
      order3,
       order4
    )
)
package com.example.e_commerce_iti.model.reposiatory.remotes

import com.example.e_commerce_iti.model.pojos.draftorder.Customer
import com.example.e_commerce_iti.model.pojos.draftorder.DraftOrder
import com.example.e_commerce_iti.model.pojos.draftorder.LineItems

// Example of a fake draft order:
val draftOrders = listOf<DraftOrder>(DraftOrder(
    id = 1001,
    customer = Customer(
        id = 501,
        first_name = "John",
        last_name = "Doe",
        email = "john.doe@example.com"
    ),
    line_items = listOf(
        LineItems(product_id = 1, variant_id = 101, quantity = 2, price = "4.99"),
        LineItems(product_id = 2, variant_id = 201, quantity = 1, price = "3.99"),
        LineItems(product_id = 3, variant_id = 301, quantity = 3, price = "7.99")
    ),
    total_price = "42.93",
    currency = "USD",
    status = "open"
    ),
  DraftOrder(
        id = 1002,
        customer = Customer(
            id = 502,
            first_name = "John",
            last_name = "Doe",
            email = "ahmeddali@example.com"
        ),
        line_items = listOf(
            LineItems(product_id = 1, variant_id = 101, quantity = 2, price = "4.99"),
            LineItems(product_id = 2, variant_id = 201, quantity = 1, price = "3.99"),
            LineItems(product_id = 3, variant_id = 301, quantity = 3, price = "7.99")
        ),
        total_price = "42.93",
        currency = "USD",
        status = "open"
    )
)
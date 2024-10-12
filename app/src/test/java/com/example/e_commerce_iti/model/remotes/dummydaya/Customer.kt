package com.example.e_commerce_iti.model.remotes.dummydaya

import com.example.e_commerce_iti.model.pojos.customer.Addresse
import com.example.e_commerce_iti.model.pojos.customer.CustomerX
import com.example.e_commerce_iti.model.pojos.customer.DefaultAddress

val dummyCustomers = arrayListOf(
    CustomerX(
        addresses = listOf(
            Addresse(
                address1 = "123 Maple Street",
                city = "Springfield",
                country = "USA",
                zip = "12345",
                phone = "+123456789"
            )
        ),
        admin_graphql_api_id = "gid://shopify/Customer/123456789",
        created_at = "2024-01-01T10:00:00Z",
        currency = "USD",
        default_address = DefaultAddress(
            address1 = "123 Maple Street",
            city = "Springfield",
            country = "USA",
            zip = "12345",
            phone = "+123456789"
        ),
        email = "alice.smith@example.com",

        first_name = "Alice",
        id = 100001L,
        last_name = "Smith",
        orders_count = 5L,
        phone = "+123456789",

        state = "active",
        tags = "VIP, email_subscriber",
        tax_exempt = false,
        total_spent = "500.00",
        updated_at = "2024-09-10T08:00:00Z",
        verified_email = true
    ),
    CustomerX(
        addresses = listOf(
            Addresse(
                address1 = "456 Oak Avenue",
                city = "Hometown",
                country = "USA",
                zip = "54321",
                phone = "+987654321"
            )
        ),
        admin_graphql_api_id = "gid://shopify/Customer/987654321",
        created_at = "2024-02-15T15:30:00Z",
        currency = "USD",
        default_address = DefaultAddress(
            address1 = "456 Oak Avenue",
            city = "Hometown",
            country = "USA",
            zip = "54321",
            phone = "+987654321"
        ),
        email = "bob.johnson@example.com",

        first_name = "Bob",
        id = 100002L,
        last_name = "Johnson",
        orders_count = 3L,
        phone = "+987654321",

        state = "active",
        tags = "loyal_customer",
        tax_exempt = false,
        total_spent = "300.00",
        updated_at = "2024-09-12T10:30:00Z",
        verified_email = true
    ),
    CustomerX(
        addresses = listOf(
            Addresse(
                address1 = "789 Pine Lane",
                city = "Capital City",
                country = "USA",
                zip = "56789",
                phone = "+654321789"
            )
        ),
        admin_graphql_api_id = "gid://shopify/Customer/654321789",
        created_at = "2024-03-20T09:00:00Z",
        currency = "USD",
        default_address = DefaultAddress(
            address1 = "789 Pine Lane",
            city = "Capital City",
            country = "USA",
            zip = "56789",
            phone = "+654321789"
        ),
        email = "charlie.williams@example.com",

        first_name = "Charlie",
        id = 100003L,
        last_name = "Williams",
        orders_count = 10L,
        phone = "+654321789",

        state = "active",
        tags = "VIP",
        tax_exempt = false,
        total_spent = "1000.00",
        updated_at = "2024-09-15T13:00:00Z",
        verified_email = true
    ),
    CustomerX(
        addresses = listOf(
            Addresse(
                address1 = "101 Birch Road",
                city = "Riverside",
                country = "USA",
                zip = "13579",
                phone = "+321789654"
            )
        ),
        admin_graphql_api_id = "gid://shopify/Customer/321789654",
        created_at = "2024-04-10T11:45:00Z",
        currency = "USD",
        default_address = DefaultAddress(
            address1 = "101 Birch Road",
            city = "Riverside",
            country = "USA",
            zip = "13579",
            phone = "+321789654"
        ),
        email = "daisy.clark@example.com",

        first_name = "Daisy",
        id = 100004L,
        last_name = "Clark",
        orders_count = 8L,
        phone = "+321789654",

        state = "active",
        tags = "email_subscriber",
        tax_exempt = false,
        total_spent = "800.00",
        updated_at = "2024-09-18T15:00:00Z",
        verified_email = true
    ),
    CustomerX(
        addresses = listOf(
            Addresse(
                address1 = "202 Cedar Boulevard",
                city = "Lakeside",
                country = "USA",
                zip = "24680",
                phone = "+789123456"
            )
        ),
        admin_graphql_api_id = "gid://shopify/Customer/789123456",
        created_at = "2024-05-05T17:15:00Z",
        currency = "USD",
        default_address = DefaultAddress(
            address1 = "202 Cedar Boulevard",
            city = "Lakeside",
            country = "USA",
            zip = "24680",
            phone = "+789123456"
        ),
        email = "ethan.wright@example.com",

        first_name = "Ethan",
        id = 100005L,
        last_name = "Wright",
        orders_count = 4L,
        phone = "+789123456",

        state = "active",
        tags = "email_subscriber",
        tax_exempt = false,
        total_spent = "400.00",
        updated_at = "2024-09-20T09:30:00Z",
        verified_email = true
    )
)
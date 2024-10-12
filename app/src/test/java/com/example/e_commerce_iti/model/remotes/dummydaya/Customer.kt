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

        email = "alice.smith@example.com",

        first_name = "Alice",
        id = 100001L,
        last_name = "Smith",
        phone = "+123456789",

        tax_exempt = false,
        total_spent = "500.00",
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

        email = "bob.johnson@example.com",

        first_name = "Bob",
        id = 100002L,
        last_name = "Johnson",
        phone = "+987654321",


        tax_exempt = false,
        total_spent = "300.00",
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

        email = "charlie.williams@example.com",

        first_name = "Charlie",
        id = 100003L,
        last_name = "Williams",
        phone = "+654321789",

        tax_exempt = false,
        total_spent = "1000.00",
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

        email = "daisy.clark@example.com",

        first_name = "Daisy",
        id = 100004L,
        last_name = "Clark",
        phone = "+321789654",


        tax_exempt = false,
        total_spent = "800.00",
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

        email = "ethan.wright@example.com",

        first_name = "Ethan",
        id = 100005L,
        last_name = "Wright",
        phone = "+789123456",

        tax_exempt = false,
        total_spent = "400.00",
        verified_email = true
    )
)
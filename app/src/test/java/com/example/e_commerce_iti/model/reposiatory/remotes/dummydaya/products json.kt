package com.example.e_commerce_iti.model.reposiatory.remotes.dummydaya

import com.example.e_commerce_iti.model.pojos.Image
import com.example.e_commerce_iti.model.pojos.Product
import com.example.e_commerce_iti.model.pojos.Variant

val product1 = Product(
    id = 1,
    title = "Recycled Aluminum Water Bottle",
    body_html = "Durable and eco-friendly aluminum bottle, perfect for hydration on the go.",
    vendor = "Vendor1",
    product_type = "Personal Care",
    tags = "eco-friendly, hydration, reusable, bottle",
    status = "active",
    variants = listOf(
        Variant(id = 101, title = "500ml", price = "9.99", sku = "ALU-BOTTLE-500", inventory_quantity = 150),
        Variant(id = 102, title = "750ml", price = "12.99", sku = "ALU-BOTTLE-750", inventory_quantity = 80)
    ),
    options = listOf(),
    images = listOf(
        Image(id = 1001, src = "https://fakeshopify.com/images/aluminum-water-bottle.jpg", width = 100, height = 100)
    )
)

val product2 = Product(
    id = 2,
    title = "Biodegradable Plant Pots",
    body_html = "Eco-friendly plant pots made from natural materials, great for home gardening.",
    vendor = "Vendor1",
    product_type = "Gardening",
    tags = "eco-friendly, biodegradable, plant pots, gardening",
    status = "active",
    variants = listOf(
        Variant(id = 201, title = "Small", price = "3.99", sku = "BIO-POT-S", inventory_quantity = 300),
        Variant(id = 202, title = "Medium", price = "5.99", sku = "BIO-POT-M", inventory_quantity = 200)
    ),
    options = listOf(),
    images = listOf(
        Image(id = 2001, src = "https://fakeshopify.com/images/plant-pots.jpg", width = 100, height = 100)
    )
)

val product3 = Product(
    id = 3,
    title = "Reusable Cotton Shopping Bag",
    body_html = "Strong, durable cotton shopping bag for everyday use.",
    vendor = "Vendor1",
    product_type = "Lifestyle",
    tags = "eco-friendly, reusable, shopping bag, cotton",
    status = "active",
    variants = listOf(
        Variant(id = 301, title = "Large", price = "7.99", sku = "COT-BAG-L", inventory_quantity = 400),
        Variant(id = 302, title = "Medium", price = "5.99", sku = "COT-BAG-M", inventory_quantity = 150)
    ),
    options = listOf(),
    images = listOf(
        Image(id = 3001, src = "https://fakeshopify.com/images/cotton-shopping-bag.jpg", width = 100, height = 100)
    )
)

val product4 = Product(
    id = 4,
    title = "Organic Beeswax Food Wraps",
    body_html = "Sustainable alternative to plastic wrap made from organic beeswax.",
    vendor = "Vendor1",
    product_type = "Kitchen",
    tags = "eco-friendly, beeswax, food wraps, sustainable",
    status = "active",
    variants = listOf(
        Variant(id = 401, title = "Small Pack", price = "9.99", sku = "WRAP-BEE-S", inventory_quantity = 200),
        Variant(id = 402, title = "Large Pack", price = "14.99", sku = "WRAP-BEE-L", inventory_quantity = 100)
    ),
    options = listOf(),
    images = listOf(
        Image(id = 4001, src = "https://fakeshopify.com/images/beeswax-wraps.jpg", width = 100, height = 100)
    )
)

val product5 = Product(
    id = 5,
    title = "Compostable Coffee Cups",
    body_html = "Environmentally friendly compostable coffee cups for everyday use.",
    vendor = "Vendor2",
    product_type = "Kitchen",
    tags = "eco-friendly, compostable, coffee cups, disposable",
    status = "active",
    variants = listOf(
        Variant(id = 501, title = "12 oz", price = "6.99", sku = "CUP-COMP-12", inventory_quantity = 500),
        Variant(id = 502, title = "16 oz", price = "8.99", sku = "CUP-COMP-16", inventory_quantity = 300)
    ),
    options = listOf(),
    images = listOf(
        Image(id = 5001, src = "https://fakeshopify.com/images/coffee-cups.jpg", width = 100, height = 100)
    )
)

val product6 = Product(
    id = 6,
    title = "Bamboo Cutlery Set",
    body_html = "Sustainable and reusable bamboo cutlery set, perfect for meals on the go.",
    vendor = "Vendor2",
    product_type = "Kitchen",
    tags = "eco-friendly, bamboo, cutlery, reusable",
    status = "active",
    variants = listOf(
        Variant(id = 601, title = "Set of 4", price = "11.99", sku = "CUT-BAMBOO-4", inventory_quantity = 150),
        Variant(id = 602, title = "Set of 6", price = "14.99", sku = "CUT-BAMBOO-6", inventory_quantity = 80)
    ),
    options = listOf(),
    images = listOf(
        Image(id = 6001, src = "https://fakeshopify.com/images/bamboo-cutlery.jpg", width = 100, height = 100)
    )
)

val product7 = Product(
    id = 7,
    title = "Recycled Paper Notebook",
    body_html = "Notebook made from 100% recycled paper, great for students and professionals.",
    vendor = "Vendor2",
    product_type = "Stationery",
    tags = "eco-friendly, recycled paper, notebook, stationery",
    status = "active",
    variants = listOf(
        Variant(id = 701, title = "A5", price = "4.99", sku = "NOTE-RP-A5", inventory_quantity = 400),
        Variant(id = 702, title = "A4", price = "6.99", sku = "NOTE-RP-A4", inventory_quantity = 250)
    ),
    options = listOf(),
    images = listOf(
        Image(id = 7001, src = "https://fakeshopify.com/images/recycled-notebook.jpg", width = 100, height = 100)
    )
)

val product8 = Product(
    id = 8,
    title = "Solar-Powered LED Lantern",
    body_html = "Eco-friendly lantern powered by solar energy, perfect for outdoor adventures.",
    vendor = "Vendor2",
    product_type = "Outdoor",
    tags = "eco-friendly, solar-powered, LED, lantern",
    status = "active",
    variants = listOf(
        Variant(id = 801, title = "Standard", price = "19.99", sku = "SOLAR-LANTERN-S", inventory_quantity = 100),
        Variant(id = 802, title = "Deluxe", price = "24.99", sku = "SOLAR-LANTERN-D", inventory_quantity = 50)
    ),
    options = listOf(),
    images = listOf(
        Image(id = 8001, src = "https://fakeshopify.com/images/solar-lantern.jpg", width = 100, height = 100)
    )
)

val product9 = Product(
    id = 9,
    title = "Eco-Friendly Glass Straw Set",
    body_html = "Durable glass straws, a sustainable alternative to plastic straws.",
    vendor = "Vendor2",
    product_type = "Kitchen",
    tags = "eco-friendly, glass, straws, reusable",
    status = "active",
    variants = listOf(
        Variant(id = 901, title = "Set of 4", price = "9.99", sku = "STRAW-GLASS-4", inventory_quantity = 150),
        Variant(id = 902, title = "Set of 6", price = "12.99", sku = "STRAW-GLASS-6", inventory_quantity = 100)
    ),
    options = listOf(),
    images = listOf(
        Image(id = 9001, src = "https://fakeshopify.com/images/glass-straw.jpg", width = 100, height = 100)
    )
)

// Create a list of all products
val products = listOf(
    product1,
    product2,
    product3,
    product4,
    product5,
    product6,
    product7,
    product8,
    product9
)

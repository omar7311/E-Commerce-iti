package com.example.e_commerce_iti.model.remotes.dummydaya

import com.example.e_commerce_iti.model.pojos.CollectionImage
import com.example.e_commerce_iti.model.pojos.CustomCollection

val customCollection1 = CustomCollection(
    id = 201L,
    handle = "eco-friendly-products",
    title = "Eco-Friendly Products",
    body_html = "A curated selection of environmentally friendly products for your home and lifestyle.",
    image = CollectionImage(
        width = 500,
        height = 500,
        src = "https://fakeshopify.com/images/eco-friendly-products.jpg"
    )
)

val customCollection2 = CustomCollection(
    id = 202L,
    handle = "sustainable-living",
    title = "Sustainable Living",
    body_html = "Products that promote sustainability and eco-conscious living.",
    image = CollectionImage(
        width = 450,
        height = 450,
        src = "https://fakeshopify.com/images/sustainable-living.jpg"
    )
)

val customCollection3 = CustomCollection(
    id = 203L,
    handle = "natural-beauty",
    title = "Natural Beauty",
    body_html = "Natural and organic beauty products that are kind to your skin and the environment.",
    image = CollectionImage(
        width = 600,
        height = 600,
        src = "https://fakeshopify.com/images/natural-beauty.jpg"
    )
)

val customCollection4 = CustomCollection(
    id = 204L,
    handle = "zero-waste-essentials",
    title = "Zero Waste Essentials",
    body_html = "Essentials for a zero-waste lifestyle, reducing your carbon footprint.",
    image = CollectionImage(
        width = 550,
        height = 550,
        src = "https://fakeshopify.com/images/zero-waste-essentials.jpg"
    )
)

val customCollection5 = CustomCollection(
    id = 205L,
    handle = "organic-living",
    title = "Organic Living",
    body_html = "Organic products for healthy, sustainable living.",
    image = CollectionImage(
        width = 480,
        height = 480,
        src = "https://fakeshopify.com/images/organic-living.jpg"
    )
)

// Create a list of all custom collections
val dummyCustomCollections = listOf(
    customCollection1,
    customCollection2,
    customCollection3,
    customCollection4,
    customCollection5
)



package com.example.e_commerce_iti.model.reposiatory.remotes.dummydaya

import com.example.e_commerce_iti.model.pojos.CollectionImage
import com.example.e_commerce_iti.model.pojos.CustomCollection
import com.example.e_commerce_iti.model.pojos.CustomCollectionsResponse

val dummyCustomCollections = listOf(
    CustomCollection(
        id = 201L,
        handle = "eco-friendly-products",
        title = "Eco-Friendly Products",
        body_html = "A curated selection of environmentally friendly products for your home and lifestyle.",
        image = CollectionImage(
            width = 500,
            height = 500,
            src = "https://fakeshopify.com/images/eco-friendly-products.jpg"
        )
    ),
    CustomCollection(
        id = 202L,
        handle = "sustainable-living",
        title = "Sustainable Living",
        body_html = "Products that promote sustainability and eco-conscious living.",
        image = CollectionImage(
            width = 450,
            height = 450,
            src = "https://fakeshopify.com/images/sustainable-living.jpg"
        )
    ),
    CustomCollection(
        id = 203L,
        handle = "natural-beauty",
        title = "Natural Beauty",
        body_html = "Natural and organic beauty products that are kind to your skin and the environment.",
        image = CollectionImage(
            width = 600,
            height = 600,
            src = "https://fakeshopify.com/images/natural-beauty.jpg"
        )
    ),
    CustomCollection(
        id = 204L,
        handle = "zero-waste-essentials",
        title = "Zero Waste Essentials",
        body_html = "Essentials for a zero-waste lifestyle, reducing your carbon footprint.",
        image = CollectionImage(
            width = 550,
            height = 550,
            src = "https://fakeshopify.com/images/zero-waste-essentials.jpg"
        )
    ),
    CustomCollection(
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
)

// Wrapping the dummy data in a response object
val dummyCustomCollectionsResponse = CustomCollectionsResponse(
    custom_collections = dummyCustomCollections
)
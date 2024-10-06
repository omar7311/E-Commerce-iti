package com.example.e_commerce_iti.model.pojos



data class CustomCollection(
    val id: Long,
    val handle: String,
    val title: String,
    val body_html: String,
    val image: CollectionImage
)


/**
 *        actually this is used to retrive the custom Collections
 */
data class CustomCollectionsResponse(
    val custom_collections: List<CustomCollection> // List of collections
)
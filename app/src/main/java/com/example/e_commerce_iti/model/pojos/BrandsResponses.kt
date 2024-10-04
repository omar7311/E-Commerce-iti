package com.example.e_commerce_iti.model.pojos

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class SmartCollectionResponse(
    @SerializedName("smart_collections") val collections: List<SmartCollection>
)

data class SmartCollection(
    val id: Long,
    val title: String,
    val image: CollectionImage?
)

data class CollectionImage(
    val width: Int,
    val height: Int,
    val src: String
)

/**
 *          actual data we need from brands
 */
data class BrandData(
    val id: Long,
    val title: String,
    val imageSrc: String?,
    val imageWidth: Int?,
    val imageHeight: Int?
): Serializable
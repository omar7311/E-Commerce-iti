package com.example.e_commerce_iti.model.apis

import com.example.e_commerce_iti.model.pojos.CustomCollectionsResponse
import com.example.e_commerce_iti.model.pojos.ProductResponse
import com.example.e_commerce_iti.model.pojos.SmartCollectionResponse
import retrofit2.http.GET
import retrofit2.http.Query

/**
 *      frist start to create a brand get function
 *      2 - getting products by brand Title like "Adidas"
 */
interface EcommerceApi {

    @GET("smart_collections.json")
    suspend fun getSmartCollections(): SmartCollectionResponse

    @GET("products.json")
    suspend fun getProductsByVendorID(@Query("vendor") vendorName: String): ProductResponse

    // get the custom collections
    @GET("custom_collections.json")
    suspend fun getCustomCollections(): CustomCollectionsResponse

    // get the products by custom collection
    @GET("products.json")
    suspend fun getProductsByCustomCollection(@Query("collection_id") collectionId: Long): ProductResponse
}
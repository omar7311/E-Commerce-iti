package com.example.e_commerce_iti.model.apis

import com.example.e_commerce_iti.model.pojos.SmartCollectionResponse
import retrofit2.http.GET

/**
 *      frist start to create a brand get function
 */
interface EcommerceApi {

    @GET("smart_collections.json")
    suspend fun getSmartCollections(): SmartCollectionResponse
    @GET("price_rules.json")
    suspend fun getPrice_rules()
}
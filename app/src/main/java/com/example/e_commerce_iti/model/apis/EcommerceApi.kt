package com.example.e_commerce_iti.model.apis
import com.example.e_commerce_iti.model.pojos.ProductResponse

import com.example.e_commerce_iti.model.pojos.SmartCollectionResponse
import com.example.e_commerce_iti.model.pojos.discountcode.DiscountCode
import com.example.e_commerce_iti.model.pojos.price_rules.PriceRules
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Path



/**
 *      frist start to create a brand get function
 */
interface EcommerceApi {

    @GET("smart_collections.json")
    suspend fun getSmartCollections(): SmartCollectionResponse
    @GET("price_rules.json")
    suspend fun getPriceRules(): PriceRules
    @GET("price_rules/{priceId}/discount_codes.json")
    suspend fun getCopuons(@Path("priceId") priceId: Long) : DiscountCode
    @GET("products.json")
    suspend fun getProductsByVendorID(@Query("vendor") vendorName: String): ProductResponse

}
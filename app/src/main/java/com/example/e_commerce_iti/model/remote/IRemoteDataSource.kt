package com.example.e_commerce_iti.model.remote

import com.example.e_commerce_iti.model.pojos.BrandData
import com.example.e_commerce_iti.model.pojos.Product
import com.example.e_commerce_iti.model.pojos.ProductResponse
import com.example.e_commerce_iti.model.pojos.discountcode.DiscountCode
import com.example.e_commerce_iti.model.pojos.price_rules.PriceRules
import kotlinx.coroutines.flow.Flow

/**
 *      here start to create the functin that will get brands
 */
interface IRemoteDataSource {
    suspend fun getPriceRules() : Flow<PriceRules>
    suspend fun getCopuons(priceId: Long) : Flow<DiscountCode>
    suspend fun getCustomCollections(): Flow<List<CustomCollection>>
    suspend fun getProductsByCustomCollection(collectionId: Long): Flow<List<Product>>

    suspend fun getBrands() : Flow<List<BrandData>>
    suspend fun getProductsByVendor(vendorName: String) : Flow<List<Product>>
}
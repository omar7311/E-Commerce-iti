package com.example.e_commerce_iti.model.remote

import com.example.e_commerce_iti.model.pojos.BrandData
import kotlinx.coroutines.flow.Flow
import com.example.e_commerce_iti.model.pojos.discountcode.DiscountCode
import com.example.e_commerce_iti.model.pojos.price_rules.PriceRules
import com.example.e_commerce_iti.model.pojos.Product
import com.example.e_commerce_iti.model.pojos.ProductResponse

/**
 *      here start to create the functin that will get brands
 */
interface IRemoteDataSource {

    suspend fun getBrands() : Flow<List<BrandData>>
    suspend fun getProductsByVendor(vendorName: String) : Flow<List<Product>>
    suspend fun getPriceRules() : Flow<PriceRules>
    suspend fun getCopuons(priceId: Long) : Flow<DiscountCode>

}
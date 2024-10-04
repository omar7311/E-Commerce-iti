package com.example.e_commerce_iti.model.reposiatory
import com.example.e_commerce_iti.model.pojos.Product

import com.example.e_commerce_iti.model.pojos.BrandData
import kotlinx.coroutines.flow.Flow
import com.example.e_commerce_iti.model.pojos.discountcode.DiscountCode
import com.example.e_commerce_iti.model.pojos.discountcode.DiscountCodeX
import com.example.e_commerce_iti.model.pojos.price_rules.PriceRule
import com.example.e_commerce_iti.model.pojos.price_rules.PriceRules

interface IReposiatory {
    suspend fun getProductsByVendor(vendorName: String) : Flow<List<Product>>
    suspend fun getBrands(): Flow<List<BrandData>>
    suspend fun getPriceRules(): Flow<PriceRules>
    suspend fun getCopuons(priceId: Long): Flow<DiscountCode>
}
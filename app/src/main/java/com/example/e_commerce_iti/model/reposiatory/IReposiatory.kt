package com.example.e_commerce_iti.model.reposiatory

import com.example.e_commerce_iti.model.pojos.BrandData
import com.example.e_commerce_iti.model.pojos.discountcode.DiscountCode
import com.example.e_commerce_iti.model.pojos.discountcode.DiscountCodeX
import com.example.e_commerce_iti.model.pojos.price_rules.PriceRule
import com.example.e_commerce_iti.model.pojos.price_rules.PriceRules
import kotlinx.coroutines.flow.Flow

interface IReposiatory {

     suspend fun getBrands(): Flow<List<BrandData>>
     suspend fun getPriceRules(): Flow<PriceRules>
     suspend fun getCopuons(priceId: Long): Flow<DiscountCode>
}
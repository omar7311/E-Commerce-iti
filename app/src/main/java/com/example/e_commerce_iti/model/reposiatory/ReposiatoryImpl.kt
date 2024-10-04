package com.example.e_commerce_iti.model.reposiatory

import android.util.Log
import com.example.e_commerce_iti.model.apis.RetrofitHelper
import com.example.e_commerce_iti.model.pojos.BrandData
import com.example.e_commerce_iti.model.pojos.discountcode.DiscountCode
import com.example.e_commerce_iti.model.pojos.discountcode.DiscountCodeX
import com.example.e_commerce_iti.model.pojos.price_rules.PriceRule
import com.example.e_commerce_iti.model.pojos.Product
import com.example.e_commerce_iti.model.pojos.price_rules.PriceRules

import com.example.e_commerce_iti.model.remote.IRemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 *      here the repo get the brands form the remote and return it
 */
class ReposiatoryImpl(val remote:IRemoteDataSource) :IReposiatory {
    override suspend fun getProductsByVendor(vendorName: String): Flow<List<Product>> {
        return remote.getProductsByVendor(vendorName)
    }
    override suspend fun getPriceRules()= remote.getPriceRules()

    override suspend fun getCopuons(priceId: Long)=remote.getCopuons(priceId)

    override suspend fun getBrands(): Flow<List<BrandData>>  = remote.getBrands()
}
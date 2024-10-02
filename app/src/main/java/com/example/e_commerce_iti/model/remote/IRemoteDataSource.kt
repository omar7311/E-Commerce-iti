package com.example.e_commerce_iti.model.remote

import com.example.e_commerce_iti.model.pojos.BrandData
import kotlinx.coroutines.flow.Flow

/**
 *      here start to create the functin that will get brands
 */
interface IRemoteDataSource {

    suspend fun getBrands() : Flow<List<BrandData>>
}
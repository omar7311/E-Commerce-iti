package com.example.e_commerce_iti.model.reposiatory

import com.example.e_commerce_iti.model.pojos.BrandData
import com.example.e_commerce_iti.model.pojos.CustomCollection
import com.example.e_commerce_iti.model.pojos.Product
import kotlinx.coroutines.flow.Flow

interface IReposiatory {

    suspend fun getBrands(): Flow<List<BrandData>>
    suspend fun getProductsByVendor(vendorName: String) : Flow<List<Product>>
    suspend fun getCustomCollections(): Flow<List<CustomCollection>>
    suspend fun getProductsByCustomCollection(collectionId: Long): Flow<List<Product>>

}
package com.example.e_commerce_iti.model.reposiatory

import com.example.e_commerce_iti.model.pojos.BrandData
import com.example.e_commerce_iti.model.pojos.CustomCollection
import com.example.e_commerce_iti.model.pojos.Product
import com.example.e_commerce_iti.model.remote.IRemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 *      here the repo get the brands form the remote and return it
 */
class ReposiatoryImpl(val remote: IRemoteDataSource) : IReposiatory {

    override suspend fun getBrands(): Flow<List<BrandData>> = remote.getBrands()
    override suspend fun getProductsByVendor(vendorName: String): Flow<List<Product>> {
        return remote.getProductsByVendor(vendorName)
    }

    override suspend fun getCustomCollections(): Flow<List<CustomCollection>> {
        return remote.getCustomCollections()
    }

    override suspend fun getProductsByCustomCollection(collectionId: Long): Flow<List<Product>> {
        return remote.getProductsByCustomCollection(collectionId)
    }

}
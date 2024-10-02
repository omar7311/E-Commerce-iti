package com.example.e_commerce_iti.model.reposiatory

import com.example.e_commerce_iti.model.pojos.BrandData
import com.example.e_commerce_iti.model.remote.IRemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 *      here the repo get the brands form the remote and return it
 */
class ReposiatoryImpl(val remote:IRemoteDataSource) :IReposiatory {

    override suspend fun getBrands(): Flow<List<BrandData>>  = flow{
        remote.getBrands()
    }
}
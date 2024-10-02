package com.example.e_commerce_iti.model.remote

import com.example.e_commerce_iti.model.apis.RetrofitHelper
import com.example.e_commerce_iti.model.pojos.BrandData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf

class RemoteDataSourceImp : IRemoteDataSource {
    override suspend fun getBrands(): Flow<List<BrandData>> = flow{

        val response = RetrofitHelper.service.getSmartCollections()
        val brands = response.collections.map {
            BrandData(
                id = it.id,
                title = it.title,
                imageSrc = it.image?.src,
                imageWidth = it.image?.width,
                imageHeight = it.image?.height
            )


        }
        emit(brands)

    }
}
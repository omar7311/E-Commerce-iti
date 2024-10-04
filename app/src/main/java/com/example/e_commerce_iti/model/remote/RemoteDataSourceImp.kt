package com.example.e_commerce_iti.model.remote

import android.util.Log
import com.example.e_commerce_iti.model.apis.RetrofitHelper
import com.example.e_commerce_iti.model.pojos.BrandData
import com.example.e_commerce_iti.model.pojos.discountcode.DiscountCode
import com.example.e_commerce_iti.model.pojos.price_rules.PriceRules
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RemoteDataSourceImp : IRemoteDataSource {
    override suspend fun getBrands(): Flow<List<BrandData>> {
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
       return flow {
            emit(brands)
        }
    }

    override suspend fun getPriceRules(): Flow<PriceRules> {
        return flow {emit(RetrofitHelper.service.getPriceRules())}
    }

    override suspend fun getCopuons(priceId: Long): Flow<DiscountCode> {
        Log.d("TAG", "getCopuons: $priceId")
        val data= RetrofitHelper.service.getCopuons(priceId)
        Log.d("TAG", "getCopuons: $data")
       return flow {emit(data)}
    }
}


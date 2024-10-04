package com.example.e_commerce_iti.model.remote

import android.util.Log
import com.example.e_commerce_iti.model.apis.RetrofitHelper
import com.example.e_commerce_iti.model.pojos.BrandData
import com.example.e_commerce_iti.model.pojos.CustomCollection
import com.example.e_commerce_iti.model.pojos.CustomCollectionsResponse
import com.example.e_commerce_iti.model.pojos.Product
import com.example.e_commerce_iti.model.pojos.ProductResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException

class RemoteDataSourceImp : IRemoteDataSource {
    override suspend fun getBrands(): Flow<List<BrandData>> {

        val response = RetrofitHelper.service.getSmartCollections()
        val brands = response.collections.map {
            BrandData(
                id = it.id,
                title = it.title,
                imageSrc = it.image?.src,
                imageWidth = it.image?.width,
                imageHeight = it.image?.height,
            )

        }
        return flow {
            emit(brands)
        }
    }

    /**
     *      get Products by vendor name
     */
    override suspend fun getProductsByVendor(vendorName: String): Flow<List<Product>> {
        val response = RetrofitHelper.service.getProductsByVendorID(vendorName)
        return flow {
            try {
                val response = RetrofitHelper.service.getProductsByVendorID(vendorName)
                emit(response.products)
            } catch (e: HttpException) {
                Log.e("API_ERROR", "Error fetching products by collection: ${e.message()}")
                emit(emptyList())
            }
        }
    }


    // to get the custom collections
    override suspend fun getCustomCollections(): Flow<List<CustomCollection>> {
        val response = RetrofitHelper.service.getCustomCollections()
        return flow {
            emit(response.custom_collections)
        }
    }

    // get Products by custom collection id
    override suspend fun getProductsByCustomCollection(collectionId: Long): Flow<List<Product>> {

        val respone = RetrofitHelper.service.getProductsByCustomCollection(collectionId)
        return flow {
            emit(respone.products)
        }
    }
}


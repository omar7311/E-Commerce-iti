package com.example.e_commerce_iti.model.remote

import android.util.Log
import com.example.e_commerce_iti.model.apis.RetrofitHelper
import com.example.e_commerce_iti.model.pojos.BrandData
import com.example.e_commerce_iti.model.pojos.CustomCollection
import com.example.e_commerce_iti.model.pojos.Product
import com.example.e_commerce_iti.model.pojos.ProductResponse
import com.example.e_commerce_iti.model.pojos.currenyex.CurrencyExc
import com.example.e_commerce_iti.model.pojos.customer.Customer
import com.example.e_commerce_iti.model.pojos.customer.CustomerX
import com.example.e_commerce_iti.model.pojos.discountcode.DiscountCode
import com.example.e_commerce_iti.model.pojos.metadata.MetaData
import com.example.e_commerce_iti.model.pojos.price_rules.PriceRules
import com.example.e_commerce_iti.model.pojos.updatecustomer.UCustomer
import com.example.e_commerce_iti.model.pojos.updatecustomer.UpdateCustomer
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.json.JSONObject
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

    override suspend fun getCustomer(email: String): Flow<CustomerX> {

        val data=RetrofitHelper.service.searchCustomerByEmail("email:${email}")
       return flow { emit(data.customers!!.get(0)) }
    }

    override suspend fun createCustomer(customer: Customer): Flow<Customer> {
        val response = RetrofitHelper.service.createCustomer(customer)
        println(" messsadsadsadas asdsadsa    ${response.message()}")
        return flow { emit(response.body()!!) }
    }


    override suspend fun createCustomerMeta(customer: Customer,metafields: MetaData): Flow<MetaData> {
        return flow { emit(RetrofitHelper.service.updateCustomerMetafields(customer.customer!!.id!!,metafields)) }
    }

    override suspend fun updateCustomer(id:Long,customer: String): Flow<Customer> {
        val ucustomer= Gson().fromJson(customer, UpdateCustomer::class.java)
        val req=RetrofitHelper.service.updateCustomer(id, ucustomer)
        Log.e("12312321312313213",  "${req.errorBody()?.string()}")
       return flow { emit(Customer(req.body()?.customer)) }
    }

    override suspend fun getCurrency(currency: String)=flow { emit(RetrofitHelper.currencyService.getCurrencies()) }

    // to get the custom collections
    override suspend fun getCustomCollections(): Flow<List<CustomCollection>> = flow {
        try {
            val response = RetrofitHelper.service.getCustomCollections()
            emit(response.custom_collections)
        } catch (e: Exception) {
            // Handle the exception (e.g., log it, show a message)
            Log.e("API_ERROR", "Failed to fetch custom collections: ${e.message}")
            emit(emptyList()) // Emit an empty list or you can also emit a specific error state
        }
    }

    // get Products by custom collection id
    override suspend fun getProductsByCustomCollection(collectionId: Long): Flow<List<Product>> = flow {
        try {
            val response = RetrofitHelper.service.getProductsByCustomCollection(collectionId)
            emit(response.products)
        } catch (e: Exception) {
            // Handle the exception (e.g., log it, show a message)
            Log.e("API_ERROR", "Failed to fetch products for collection ID $collectionId: ${e.message}")
            emit(emptyList()) // Emit an empty list or handle error state accordingly
        }
    }

    override suspend fun getPriceRules(): Flow<PriceRules> {
        return flow {emit(RetrofitHelper.service.getPriceRules())}
    }

    override suspend fun getCopuons(priceId: Long): Flow<DiscountCode> {
        val data= RetrofitHelper.service.getCopuons(priceId)
        return flow {emit(data)}
    }
}


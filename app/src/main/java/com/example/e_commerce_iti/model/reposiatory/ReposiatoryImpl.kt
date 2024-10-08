package com.example.e_commerce_iti.model.reposiatory

import com.example.e_commerce_iti.model.local.IlocalDataSource
import com.example.e_commerce_iti.model.pojos.BrandData
import com.example.e_commerce_iti.model.pojos.CustomCollection
import com.example.e_commerce_iti.model.pojos.Product
import com.example.e_commerce_iti.model.pojos.currenyex.CurrencyExc
import com.example.e_commerce_iti.model.pojos.customer.Customer
import com.example.e_commerce_iti.model.pojos.customer.CustomerX
import com.example.e_commerce_iti.model.pojos.draftorder.DraftOrder
import com.example.e_commerce_iti.model.pojos.metadata.MetaData
import com.example.e_commerce_iti.model.pojos.price_rules.PriceRules
import com.example.e_commerce_iti.model.pojos.updatecustomer.UCustomer
import com.example.e_commerce_iti.model.remote.IRemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf

/**
 *      here the repo get the brands form the remote and return it
 */
class ReposiatoryImpl(val remote:IRemoteDataSource,val local: IlocalDataSource) :IReposiatory {

    override suspend fun getBrands(): Flow<List<BrandData>>  = remote.getBrands()
    override suspend fun getProductsByVendor(vendorName: String): Flow<List<Product>> {
        return remote.getProductsByVendor(vendorName)
    }

    override suspend fun getCustomer(email:String): Flow<CustomerX> {
        return remote.getCustomer(email)
    }

    override suspend fun updateCustomer(id:Long,customer: String)=remote.updateCustomer(id,customer)
    override suspend fun getCurrency(currency: String)=remote.getCurrency(currency)
    override suspend fun getCurrencyFromLocal(currency: String): Flow<Pair<String, Float>> {
        val data=local.getCurrency(currency).firstOrNull()
        if (data==null){
            val response=getCurrency(currency).firstOrNull()
            if (response!=null){
                insertCurrency(response)
                local.insertCurrency(response)
                return local.setChoosedCurrency(currency)
            }
        }
        return local.setChoosedCurrency(currency)
    }

    override suspend fun insertCurrency(currency: CurrencyExc) {
        local.insertCurrency(currency)
    }

    override suspend fun getChoosedCurrency():Flow<Pair<String, Float>> {
        return local.getChoosedCurrency()
    }

    override suspend fun updateCurrency(currency: String): Flow<Pair<String, Float>> {
        return local.setChoosedCurrency(local.getChoosedCurrency().firstOrNull()?.first!!)
    }

    override suspend fun updateCart(cart: DraftOrder): Flow<DraftOrder> {
        return remote.updateCart(cart)
    }

    override suspend fun createCustomer(customer: Customer)=remote.createCustomer(customer)
    override suspend fun getMetaFields(customerId: Long): Flow<MetaData> {
        return remote.getMetaFields(customerId)
    }



    override suspend fun getCart(id: Long): Flow<DraftOrder> {
        return remote.getCart(id)
    }

    override suspend fun getProductByID(id: Long): Flow<Product> {
        return remote.getProductByID(id)
    }
    override suspend fun getCustomCollections(): Flow<List<CustomCollection>> {
        return remote.getCustomCollections()
    }

    override suspend fun getProductsByCustomCollection(collectionId: Long): Flow<List<Product>> {
        return remote.getProductsByCustomCollection(collectionId)
    }

    override suspend fun getPriceRules(): Flow<PriceRules> =remote.getPriceRules()

    override suspend fun getCopuons(priceId: Long)=remote.getCopuons(priceId)



}
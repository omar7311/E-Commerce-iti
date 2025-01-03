package com.example.e_commerce_iti.model.reposiatory

import android.util.Log
import com.example.e_commerce_iti.model.local.IlocalDataSource
import com.example.e_commerce_iti.model.pojos.AllProduct
import com.example.e_commerce_iti.model.pojos.BrandData
import com.example.e_commerce_iti.model.pojos.CustomCollection
import com.example.e_commerce_iti.model.pojos.Order
import com.example.e_commerce_iti.model.pojos.Product
import com.example.e_commerce_iti.model.pojos.currenyex.CurrencyExc
import com.example.e_commerce_iti.model.pojos.customer.Customer
import com.example.e_commerce_iti.model.pojos.customer.CustomerX
import com.example.e_commerce_iti.model.pojos.discountcode.DiscountCodeX
import com.example.e_commerce_iti.model.pojos.draftorder.DraftOrder
import com.example.e_commerce_iti.model.pojos.metadata.MetaData
import com.example.e_commerce_iti.model.pojos.price_rules.PriceRule
import com.example.e_commerce_iti.model.pojos.price_rules.PriceRules
import com.example.e_commerce_iti.model.pojos.repsonemetadata.FullMeatDataResponse
import com.example.e_commerce_iti.model.pojos.repsonemetadata.ResponseMetaData
import com.example.e_commerce_iti.model.pojos.updatecustomer.UCustomer
import com.example.e_commerce_iti.model.remote.IRemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow


/**
 *      here the repo get the brands form the remote and return it
 */
class ReposiatoryImpl(val remote: IRemoteDataSource, val local: IlocalDataSource) : IReposiatory {

    override suspend fun getBrands(): Flow<List<BrandData>> = remote.getBrands()
    override suspend fun getProductsByVendor(vendorName: String): Flow<List<Product>> {
        return remote.getProductsByVendor(vendorName)
    }

    override suspend fun getCustomer(email: String): Flow<CustomerX> {
        return remote.getCustomer(email)
    }

    override suspend fun updateCustomer(id: Long, customer: String) =
        remote.updateCustomer(id, customer)

    override suspend fun getCurrency(currency: String) = remote.getCurrency(currency)
    override suspend fun getCurrencyFromLocal(currency: String): Flow<Pair<String, Float>> {
        val data = local.getCurrency(currency).firstOrNull()
        if (data == null) {
            val response = getCurrency(currency).firstOrNull()
            if (response != null) {
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

    override suspend fun getChoosedCurrency(): Flow<Pair<String, Float>> {
        return local.getChoosedCurrency()
    }

    override suspend fun updateCurrency(currency: String): Flow<Pair<String, Float>> {
        return local.setChoosedCurrency(local.getChoosedCurrency().firstOrNull()?.first!!)
    }

    override suspend fun updateCart(cart: DraftOrder): Flow<DraftOrder> {
        return remote.updateCart(cart)
    }

    override suspend fun getMetaFields(customerId: Long): Flow<FullMeatDataResponse> {
        return remote.getMetaFields(customerId)
    }



    override suspend fun getCart(id: Long): Flow<DraftOrder> {
        return remote.getCart(id)
    }

    override suspend fun getProductByID(id: Long): Flow<Product> {
        return remote.getProductByID(id)
    }

    override suspend fun getAllDrafts(): Flow<List<DraftOrder>> {
        return remote.getAllDrafts()
    }

    override suspend fun getTempProductById(ProductId:Long): Product {
        val product = remote.getTempProductById(ProductId)
        
        return remote.getTempProductById(ProductId)
    }


    override fun getAllProduct(): Flow<AllProduct> {
        return remote.getAllProduct()
    }

    override suspend fun compeleteDraftOrder(draftOrder: DraftOrder): Flow<Boolean> {
        return remote.compeleteDraftOrder(draftOrder)
    }

    override suspend fun getCustomCollections(): Flow<List<CustomCollection>> {
        return remote.getCustomCollections()
    }

    override suspend fun getProductsByCustomCollection(collectionId: Long): Flow<List<Product>> {
        return remote.getProductsByCustomCollection(collectionId)
    }

    override suspend fun getPriceRules(): Flow<PriceRules> = remote.getPriceRules()

    override suspend fun getCopuons(priceId: Long) = remote.getCopuons(priceId)


    override suspend fun getPrice_rules(id: Long): Flow<PriceRule> {
        return remote.getPriceRulesByid(id)
    }

    override suspend fun updateMetaData(
        id: Long,
        metaData: ResponseMetaData
    ): Flow<ResponseMetaData> {
        return remote.updateMetaData(id,metaData)
    }

//    override suspend fun compeleteDraftOrder(draftOrder: DraftOrder): Flow<Boolean> {
//    }

    override suspend fun getDiscountCode(code: String): Flow<DiscountCodeX> {
        return remote.getDiscountCode(code)
    }
    override suspend fun getOrdersByCustomerId(customer_id: Long): Flow<List<Order>> {
        return remote.getOrdersByCustomerId(customer_id)
    }

    /**
     *  get Product by id
     */
    override suspend fun getProductById(productId: Long): Flow<Product> {
        val result  = remote.getProductById(productId)
        return result
    }



}
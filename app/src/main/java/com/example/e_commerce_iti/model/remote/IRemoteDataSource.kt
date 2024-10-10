package com.example.e_commerce_iti.model.remote

import com.example.e_commerce_iti.model.pojos.AllProduct
import com.example.e_commerce_iti.model.pojos.BrandData
import com.example.e_commerce_iti.model.pojos.CustomCollection
import com.example.e_commerce_iti.model.pojos.Order
import com.example.e_commerce_iti.model.pojos.Product
import com.example.e_commerce_iti.model.pojos.ProductResponse
import com.example.e_commerce_iti.model.pojos.currenyex.CurrencyExc
import com.example.e_commerce_iti.model.pojos.customer.Customer
import com.example.e_commerce_iti.model.pojos.customer.CustomerX
import com.example.e_commerce_iti.model.pojos.discountcode.DiscountCode
import com.example.e_commerce_iti.model.pojos.discountcode.DiscountCodeX
import com.example.e_commerce_iti.model.pojos.draftorder.DraftOrder
import com.example.e_commerce_iti.model.pojos.metadata.MetaData
import com.example.e_commerce_iti.model.pojos.metadata.ReMetaData
import com.example.e_commerce_iti.model.pojos.price_rules.PriceRule
import com.example.e_commerce_iti.model.pojos.price_rules.PriceRules
import com.example.e_commerce_iti.model.pojos.repsonemetadata.FullMeatDataResponse
import com.example.e_commerce_iti.model.pojos.repsonemetadata.ResponseMetaData
import com.example.e_commerce_iti.model.pojos.updatecustomer.UCustomer
import kotlinx.coroutines.flow.Flow

/**
 *      here start to create the functin that will get brands
 */
interface IRemoteDataSource {
    suspend fun getPriceRules() : Flow<PriceRules>
    suspend fun getCopuons(priceId: Long) : Flow<DiscountCode>
    suspend fun getCustomCollections(): Flow<List<CustomCollection>>
    suspend fun getProductsByCustomCollection(collectionId: Long): Flow<List<Product>>
    suspend fun getBrands() : Flow<List<BrandData>>
    suspend fun getProductsByVendor(vendorName: String) : Flow<List<Product>>
    suspend fun getCustomer(email: String) : Flow<CustomerX>
    suspend fun createCustomer(customer: Customer)
    suspend fun updateCustomer(id:Long,customer: String):Flow<Customer>
    suspend fun getCurrency(currency: String) : Flow<CurrencyExc>
    suspend fun getMetaFields(customerId: Long): Flow<FullMeatDataResponse>
    suspend fun updateCart(cart: DraftOrder): Flow<DraftOrder>
    suspend fun getCart(id:Long): Flow<DraftOrder>
    suspend fun getProductByID(id: Long): Flow<Product>
    suspend fun getDiscountCode(code: String): Flow<DiscountCodeX>
    suspend fun getPriceRulesByid(priceId: Long): Flow<PriceRule>
    suspend fun updateMetaData(id: Long, metaData: ResponseMetaData): Flow<ResponseMetaData>
    suspend fun compeleteDraftOrder(draftOrder: DraftOrder): Flow<Boolean>
    suspend fun getOrdersByCustomerId(customer_id:Long):Flow<List<Order>>
    suspend fun getProductById(productId: Long):Flow<Product>
    fun getAllProduct():Flow<AllProduct>
    suspend fun getAllDrafts(): Flow<List<DraftOrder>>


}
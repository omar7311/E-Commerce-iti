package com.example.e_commerce_iti.model.reposiatory

import com.example.e_commerce_iti.model.pojos.BrandData
import com.example.e_commerce_iti.model.pojos.CustomCollection
import com.example.e_commerce_iti.model.pojos.Product
import com.example.e_commerce_iti.model.pojos.currenyex.CurrencyExc
import com.example.e_commerce_iti.model.pojos.customer.Customer
import com.example.e_commerce_iti.model.pojos.customer.CustomerX
import com.example.e_commerce_iti.model.pojos.discountcode.DiscountCode
import com.example.e_commerce_iti.model.pojos.price_rules.PriceRules
import com.example.e_commerce_iti.model.pojos.updatecustomer.UCustomer
import kotlinx.coroutines.flow.Flow

interface IReposiatory {
    suspend fun getCustomCollections(): Flow<List<CustomCollection>>
    suspend fun getProductsByCustomCollection(collectionId: Long): Flow<List<Product>>
    suspend fun getPriceRules(): Flow<PriceRules>
    suspend fun getCopuons(priceId: Long): Flow<DiscountCode>
    suspend fun getBrands(): Flow<List<BrandData>>
    suspend fun getProductsByVendor(vendorName: String) : Flow<List<Product>>
    suspend fun getCustomer(email:String): Flow<CustomerX>
    suspend fun updateCustomer(id:Long,customer: String):Flow<Customer>
    suspend fun getCurrency(currency: String) : Flow<CurrencyExc>
    suspend fun getCurrencyFromLocal(currency: String): Flow<CurrencyExc>
    suspend fun insertCurrency(currency: CurrencyExc)
}
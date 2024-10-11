package com.example.e_commerce_iti

import com.example.e_commerce_iti.model.pojos.AllProduct
import com.example.e_commerce_iti.model.pojos.BrandData
import com.example.e_commerce_iti.model.pojos.CustomCollection
import com.example.e_commerce_iti.model.pojos.Order
import com.example.e_commerce_iti.model.pojos.Product
import com.example.e_commerce_iti.model.pojos.currenyex.CurrencyExc
import com.example.e_commerce_iti.model.pojos.customer.Customer
import com.example.e_commerce_iti.model.pojos.customer.CustomerX
import com.example.e_commerce_iti.model.pojos.discountcode.DiscountCode
import com.example.e_commerce_iti.model.pojos.discountcode.DiscountCodeX
import com.example.e_commerce_iti.model.pojos.draftorder.DraftOrder
import com.example.e_commerce_iti.model.pojos.price_rules.PriceRule
import com.example.e_commerce_iti.model.pojos.price_rules.PriceRules
import com.example.e_commerce_iti.model.pojos.repsonemetadata.FullMeatDataResponse
import com.example.e_commerce_iti.model.pojos.repsonemetadata.ResponseMetaData
import com.example.e_commerce_iti.model.remote.IRemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import products

class FakeRemote:IRemoteDataSource {
    override suspend fun getPriceRules(): Flow<PriceRules> {
        TODO("Not yet implemented")
    }

    override suspend fun getCopuons(priceId: Long): Flow<DiscountCode> {
        TODO("Not yet implemented")
    }

    override suspend fun getCustomCollections(): Flow<List<CustomCollection>> {
        TODO("Not yet implemented")
    }

    override suspend fun getProductsByCustomCollection(collectionId: Long): Flow<List<Product>> {
        TODO("Not yet implemented")
    }

    override suspend fun getBrands(): Flow<List<BrandData>> {
        TODO("Not yet implemented")
    }

    override suspend fun getProductsByVendor(vendorName: String): Flow<List<Product>> {
        TODO("Not yet implemented")
    }

    override suspend fun getCustomer(email: String): Flow<CustomerX> {
        TODO("Not yet implemented")
    }

    override suspend fun createCustomer(customer: Customer) {
        TODO("Not yet implemented")
    }

    override suspend fun updateCustomer(id: Long, customer: String): Flow<Customer> {
        TODO("Not yet implemented")
    }

    override suspend fun getCurrency(currency: String): Flow<CurrencyExc> {
        TODO("Not yet implemented")
    }

    override suspend fun getMetaFields(customerId: Long): Flow<FullMeatDataResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun updateCart(cart: DraftOrder): Flow<DraftOrder> {
        TODO("Not yet implemented")
    }

    override suspend fun getCart(id: Long): Flow<DraftOrder> {
        TODO("Not yet implemented")
    }

    override suspend fun getProductByID(id: Long): Flow<Product> {
        val d = products.find { it.id == id }
        return flowOf(d!!)
    }

    override suspend fun getDiscountCode(code: String): Flow<DiscountCodeX> {
        TODO("Not yet implemented")
    }

    override suspend fun getPriceRulesByid(priceId: Long): Flow<PriceRule> {
        TODO("Not yet implemented")
    }

    override suspend fun updateMetaData(
        id: Long,
        metaData: ResponseMetaData
    ): Flow<ResponseMetaData> {
        TODO("Not yet implemented")
    }

    override suspend fun compeleteDraftOrder(draftOrder: DraftOrder): Flow<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun getOrdersByCustomerId(customer_id: Long): Flow<List<Order>> {
        TODO("Not yet implemented")
    }

    override suspend fun getProductById(productId: Long): Flow<Product> {
        TODO("Not yet implemented")
    }

    override fun getAllProduct(): Flow<AllProduct> {
        TODO("Not yet implemented")
    }

    override suspend fun getAllDrafts(): Flow<List<DraftOrder>> {
        TODO("Not yet implemented")
    }

    override suspend fun getTempProductById(id: Long): Product {
        TODO("Not yet implemented")
    }
}
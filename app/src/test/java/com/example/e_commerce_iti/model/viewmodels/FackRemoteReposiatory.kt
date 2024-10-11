package com.example.e_commerce_iti.model.viewmodels

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
import com.example.e_commerce_iti.model.remotes.dummydaya.dummyOrders
import com.example.e_commerce_iti.model.remotes.dummydaya.product1
import com.example.e_commerce_iti.model.remotes.dummydaya.products
import com.example.e_commerce_iti.model.reposiatory.IReposiatory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FackRemoteReposiatory : IReposiatory {

    override suspend fun getDiscountCode(code: String): Flow<DiscountCodeX> {
        TODO("Not yet implemented")
    }

    override suspend fun getCustomCollections(): Flow<List<CustomCollection>> {         // ahmed
        TODO("Not yet implemented")
    }

    override suspend fun getProductsByCustomCollection(collectionId: Long): Flow<List<Product>> {   // ahmed
        TODO("Not yet implemented")
    }

    override suspend fun getPriceRules(): Flow<PriceRules> {
        TODO("Not yet implemented")
    }

    override suspend fun getCopuons(priceId: Long): Flow<DiscountCode> {
        TODO("Not yet implemented")
    }

    override suspend fun getBrands(): Flow<List<BrandData>> {            // ahmed
        TODO("Not yet implemented")
    }

    override suspend fun getProductsByVendor(vendorName: String): Flow<List<Product>> {  // ahmed
        val filteredProducts = products.filter { it.vendor == vendorName }
        return flowOf(filteredProducts)
    }

    override suspend fun getCustomer(email: String): Flow<CustomerX> {
        TODO("Not yet implemented")
    }

    override suspend fun updateCustomer(id: Long, customer: String): Flow<Customer> {
        TODO("Not yet implemented")
    }

    override suspend fun getCurrency(currency: String): Flow<CurrencyExc> {
        TODO("Not yet implemented")
    }

    override suspend fun getCurrencyFromLocal(currency: String): Flow<Pair<String, Float>> {
        TODO("Not yet implemented")
    }

    override suspend fun insertCurrency(currency: CurrencyExc) {
        TODO("Not yet implemented")
    }

    override suspend fun getChoosedCurrency(): Flow<Pair<String, Float>> {
        TODO("Not yet implemented")
    }

    override suspend fun updateCurrency(currency: String): Flow<Pair<String, Float>> {
        TODO("Not yet implemented")
    }

    override suspend fun updateCart(cart: DraftOrder): Flow<DraftOrder> {
        TODO("Not yet implemented")
    }

    override suspend fun getPrice_rules(id: Long): Flow<PriceRule> {
        TODO("Not yet implemented")
    }

    override suspend fun updateMetaData(
        id: Long,
        metaData: ResponseMetaData
    ): Flow<ResponseMetaData> {
        TODO("Not yet implemented")
    }

    override suspend fun getOrdersByCustomerId(customer_id: Long): Flow<List<Order>> {   // ahmed
        val filtered  = dummyOrders.orders.filter { it.customer.id == customer_id }
        return flowOf(filtered)
    }

    override suspend fun getProductById(productId: Long): Flow<Product> {           // omar
        val product = products.find { it.id == productId }
            ?: throw IllegalArgumentException("Product with id $productId not found") // Handle null case
        return flowOf(product)
    }

    override fun getAllProduct(): Flow<AllProduct> {   // omar
        TODO("Not yet implemented")
    }

    override suspend fun compeleteDraftOrder(draftOrder: DraftOrder): Flow<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun getMetaFields(customerId: Long): Flow<FullMeatDataResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun getCart(id: Long): Flow<DraftOrder> {
        TODO("Not yet implemented")
    }

    override suspend fun getProductByID(id: Long): Flow<Product> {    // omar
        val product = products.find { it.id == id }
            ?: throw IllegalArgumentException("Product with id $id not found") // Handle null case
        return flowOf(product)
    }

    override suspend fun getAllDrafts(): Flow<List<DraftOrder>> {
        TODO("Not yet implemented")
    }

    override suspend fun getTempProductById(ProductId: Long): Product {
        return product1  // as temp product
    }
}
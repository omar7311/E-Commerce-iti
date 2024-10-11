package com.example.e_commerce_iti.model.viewmodels

import com.example.e_commerce_iti.model.pojos.AllProduct
import com.example.e_commerce_iti.model.pojos.BrandData
import com.example.e_commerce_iti.model.pojos.CustomCollection
import com.example.e_commerce_iti.model.pojos.Order
import com.example.e_commerce_iti.model.pojos.Product
import com.example.e_commerce_iti.model.pojos.currenyex.ConversionRates
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
import com.example.e_commerce_iti.model.remotes.draftOrders
import com.example.e_commerce_iti.model.remotes.dummydaya.dummyBrandData
import com.example.e_commerce_iti.model.remotes.dummydaya.dummyCustomCollections
import com.example.e_commerce_iti.model.remotes.dummydaya.dummyCustomers
import com.example.e_commerce_iti.model.remotes.dummydaya.dummyDiscountCodes
import com.example.e_commerce_iti.model.remotes.dummydaya.dummycurrency
import com.example.e_commerce_iti.model.remotes.dummydaya.list
import com.example.e_commerce_iti.model.remotes.dummydaya.products
import com.example.e_commerce_iti.model.remotes.dummydaya.dummyOrders
import com.example.e_commerce_iti.model.remotes.dummydaya.product1
import com.example.e_commerce_iti.model.remotes.dummydaya.products
import com.example.e_commerce_iti.model.reposiatory.IReposiatory
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf

class FackRemoteReposiatory : IReposiatory {
    var chosed_currency="EGP"
    override suspend fun getDiscountCode(code: String): Flow<DiscountCodeX> {
        return flowOf(dummyDiscountCodes.find { it.code == code }?:throw Exception("Not Found"))
    }

    override suspend fun getCustomCollections(): Flow<List<CustomCollection>> {
        return flowOf(dummyCustomCollections)
    }

    override suspend fun getProductsByCustomCollection(collectionId: Long): Flow<List<Product>> {
        TODO("Not yet implemented")
    }

    override suspend fun getPriceRules(): Flow<PriceRules> {
        return flowOf(PriceRules(list))
    }

    override suspend fun getCopuons(priceId: Long): Flow<DiscountCode> {
        val e= dummyDiscountCodes.find { it.price_rule_id == priceId }
        if (e==null){
            throw Exception("Not Found")
        }else{
            val discountCode= DiscountCode(listOf(e))
            return flowOf(discountCode)
        }
    }

    override suspend fun getBrands(): Flow<List<BrandData>> {
      return flowOf(dummyBrandData)
    }

    override suspend fun getProductsByVendor(vendorName: String): Flow<List<Product>> {
        TODO()


    override suspend fun getProductsByVendor(vendorName: String): Flow<List<Product>> {  // ahmed
        val filteredProducts = products.filter { it.vendor == vendorName }
        return flowOf(filteredProducts)
    }

    override suspend fun getCustomer(email: String): Flow<CustomerX> {
        return flowOf(dummyCustomers.find { it.email == email }?:throw Exception("Not Found"))
    }

    override suspend fun updateCustomer(id: Long, customer: String): Flow<Customer> {
        val e= Gson().fromJson(customer,Customer::class.java)
        return flowOf(e)
    }

    override suspend fun getCurrency(currency: String): Flow<CurrencyExc> {
       return  flow { emit(dummycurrency) }
    }

    override suspend fun getCurrencyFromLocal(currency: String): Flow<Pair<String, Float>> {
        return flowOf(Pair("EGP",1.0f))
    }

    override suspend fun insertCurrency(currency: CurrencyExc) {
        dummycurrency=currency
    }

    override suspend fun getChoosedCurrency(): Flow<Pair<String, Float>> {
        if (chosed_currency=="USD"){
            return flowOf(Pair("USD",dummycurrency.conversion_rates!!.USD.toFloat()))
        }else if (chosed_currency=="EGP"){
            return flowOf(Pair("EGP",dummycurrency.conversion_rates!!.EGP.toFloat()))
        }else if (chosed_currency=="SAR"){
            return flowOf(Pair("SAR",dummycurrency.conversion_rates!!.SAR.toFloat()))
        } else if (chosed_currency=="EUR"){
            return flowOf(Pair("EUR",dummycurrency.conversion_rates!!.EUR.toFloat()))
        }else{
            throw Exception("Not Found")
        }
    }

    override suspend fun updateCurrency(currency: String): Flow<Pair<String, Float>> {
        chosed_currency=currency
        if (currency=="USD"){
            chosed_currency=currency
            return flowOf(Pair("USD",dummycurrency.conversion_rates!!.USD.toFloat()))
        }else if (currency=="EGP"){
            chosed_currency=currency
            return flowOf(Pair("EGP",dummycurrency.conversion_rates!!.EGP.toFloat()))
        }else if (currency=="SAR"){
            chosed_currency=currency
            return flowOf(Pair("SAR",dummycurrency.conversion_rates!!.SAR.toFloat()))
        }else if (currency=="EUR"){
            chosed_currency=currency
            return flowOf(Pair("EUR",dummycurrency.conversion_rates!!.EUR.toFloat()))
        }
        return flowOf(throw Exception("Not Found"))
    }

    override suspend fun updateCart(cart: DraftOrder): Flow<DraftOrder> {
     return flow { emit(cart) }
    }

    override suspend fun getPrice_rules(id: Long): Flow<PriceRule> {
       return flow { emit(list.find { it.id == id }?:throw Exception("Not Found")) }
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

    override fun getAllProduct(): Flow<AllProduct> {
        return flow{ emit(AllProduct(products)) }
    }

    override suspend fun compeleteDraftOrder(draftOrder: DraftOrder): Flow<Boolean> {
        return flow { emit(true) }
    }

    override suspend fun getMetaFields(customerId: Long): Flow<FullMeatDataResponse> {
       return flow {

       }
    }

    override suspend fun getCart(id: Long): Flow<DraftOrder> {
        return flowOf(draftOrders.find { it.id == id }?:throw Exception("Not Found"))
    }

    override suspend fun getProductByID(id: Long): Flow<Product> {    // omar
        val product = products.find { it.id == id }
            ?: throw IllegalArgumentException("Product with id $id not found") // Handle null case
        return flowOf(product)
    }

    override suspend fun getAllDrafts(): Flow<List<DraftOrder>> {
       return flow{ emit(draftOrders) }
    }

    override suspend fun getTempProductById(ProductId: Long): Product {
        return product1  // as temp product
    }
}
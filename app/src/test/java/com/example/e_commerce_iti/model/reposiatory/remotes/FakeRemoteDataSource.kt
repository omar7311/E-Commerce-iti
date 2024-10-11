package com.example.e_commerce_iti.model.reposiatory.remotes

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
import com.example.e_commerce_iti.model.pojos.price_rules.PrerequisiteSubtotalRange
import com.example.e_commerce_iti.model.pojos.price_rules.PrerequisiteToEntitlementPurchase
import com.example.e_commerce_iti.model.pojos.price_rules.PrerequisiteToEntitlementQuantityRatio
import com.example.e_commerce_iti.model.pojos.price_rules.PriceRule
import com.example.e_commerce_iti.model.pojos.price_rules.PriceRules
import com.example.e_commerce_iti.model.pojos.repsonemetadata.FullMeatDataResponse
import com.example.e_commerce_iti.model.pojos.repsonemetadata.ResponseMetaData
import com.example.e_commerce_iti.model.remote.IRemoteDataSource
import com.example.e_commerce_iti.model.reposiatory.remotes.dummydaya.dummyBrandData
import com.example.e_commerce_iti.model.reposiatory.remotes.dummydaya.dummyCustomCollections
import com.example.e_commerce_iti.model.reposiatory.remotes.dummydaya.dummyCustomCollectionsResponse
import com.example.e_commerce_iti.model.reposiatory.remotes.dummydaya.dummyCustomers
import com.example.e_commerce_iti.model.reposiatory.remotes.dummydaya.dummyDiscountCodes
import com.example.e_commerce_iti.model.reposiatory.remotes.dummydaya.dummyOrders
import com.example.e_commerce_iti.model.reposiatory.remotes.dummydaya.list
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import com.example.e_commerce_iti.model.reposiatory.remotes.dummydaya.products
import kotlinx.coroutines.flow.flowOf

class FakeRemoteDataSource : IRemoteDataSource {
    override suspend fun getPriceRules(): Flow<PriceRules> {
     return flowOf(PriceRules(list))
    }

    override suspend fun getCopuons(priceId: Long): Flow<DiscountCode> {
      val e= dummyDiscountCodes.filter { it.price_rule_id==priceId }
        return flowOf(DiscountCode(e))
    }

    override suspend fun getCustomCollections(): Flow<List<CustomCollection>> {
        return  flowOf(dummyCustomCollections)
    }

    override suspend fun getProductsByCustomCollection(collectionId: Long): Flow<List<Product>> {
    TODO()
    }

    override suspend fun getBrands(): Flow<List<BrandData>> {
        return flowOf(dummyBrandData)
    }

    override suspend fun getProductsByVendor(vendorName: String): Flow<List<Product>> {
        val list= products.filter { it.vendor == vendorName }
        return flow { emit(list) }
    }

    override suspend fun getCustomer(email: String): Flow<CustomerX> {
        val customer = dummyCustomers.find { it.email==email }
        return flowOf(customer!!)
    }

    override suspend fun createCustomer(customer: Customer) {
        dummyCustomers.add(customer.customer!!)
    }

    override suspend fun updateCustomer(id: Long, customer: String): Flow<Customer> {
        TODO("Not yet implemented")
    }

    override suspend fun getCurrency(currency: String): Flow<CurrencyExc> {
      return flow { emit(
          CurrencyExc(
              base_code = "GBP",
              conversion_rates = ConversionRates(
                  EUR = 1.16,
                  USD = 1.37,
                  SAR = 5.14,
                  EGP = 42.11
              ),
              documentation = "https://example.com/docs",
              result = "success",
              terms_of_use = "https://example.com/terms",
              time_last_update_unix = 1633024800,
              time_last_update_utc = "2024-10-11T12:00:00Z",
              time_next_update_unix = 1633111200,
              time_next_update_utc = "2024-10-12T12:00:00Z"
          )
      ) }
    }

    override suspend fun getMetaFields(customerId: Long): Flow<FullMeatDataResponse> {
        return flow { emit(FullMeatDataResponse(listOf( ResponseMetaData(
            id = 1,
            key = "test",
            value = "1001",
            namespace = "test",
            type = "1001",
            owner_id = 1,
            owner_resource = "test",
            created_at = "test",
        ),ResponseMetaData(
            id = 2,
            key = "test2",
            value = "test2",
            namespace = "test2",
            type = "test2",
        )
        ))) }
    }

    override suspend fun updateCart(cart: DraftOrder): Flow<DraftOrder> {
        return flow { emit(cart) }
    }

    override suspend fun getCart(id: Long): Flow<DraftOrder> {
        val cart = draftOrders.find { it.id == id }
        return flowOf (cart!!)
    }

    override suspend fun getProductByID(id: Long): Flow<Product> {
        return flow { emit(products.first { it.id == id }) }
    }

    override suspend fun getDiscountCode(code: String): Flow<DiscountCodeX> {
        return flow { emit(DiscountCodeX(
            code = "SAVE20",
            created_at = "2024-09-10T10:00:00Z",
            id = 123456789L,
            price_rule_id = 987654321L,
            updated_at = "2024-10-10T14:30:00Z",
            usage_count = 45
        )) }
    }

    override suspend fun getPriceRulesByid(priceId: Long): Flow<PriceRule> {

        return flow { emit(list.first { it.id == priceId })}
    }

    override suspend fun updateMetaData(
        id: Long,
        metaData: ResponseMetaData
    ): Flow<ResponseMetaData> {
        TODO("Not yet implemented")
    }

    override suspend fun compeleteDraftOrder(draftOrder: DraftOrder): Flow<Boolean> {
        return flow { emit(true) }
    }

    override suspend fun getOrdersByCustomerId(customer_id: Long): Flow<List<Order>> {
       val e= dummyOrders.orders.filter { it.id==customer_id }
        return flowOf(e)
    }

    override suspend fun getProductById(productId: Long): Flow<Product> {
      val e=  products.first { it.id==productId }
        return flowOf(e)
    }

    override fun getAllProduct(): Flow<AllProduct> {
      return flow{ emit(AllProduct(products)) }
    }

    override suspend fun getAllDrafts(): Flow<List<DraftOrder>> {
       return flow{ emit(draftOrders) }
    }

    override suspend fun getTempProductById(id: Long): Product {
       val e= products.filter { it.id==id }
       return (e[0])
    }
}
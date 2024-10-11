package com.example.e_commerce_iti.remotes

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
import draftOrders
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import products

class FakeRemoteDataSource : IRemoteDataSource {
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
            value = "test",
            namespace = "test",
            type = "test",
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
        return flow { emit(draftOrders.first { it.id == id }) }
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
        //val list = listOf()
        return flow { emit(PriceRule(
            admin_graphql_api_id = "gid://shopify/PriceRule/123456789",
            allocation_method = "across",
            created_at = "2024-09-01T08:00:00Z",
            customer_segment_prerequisite_ids = listOf(),
            customer_selection = "all",
            ends_at = "2024-12-31T23:59:59Z",
            entitled_collection_ids = listOf(),
            entitled_country_ids = listOf(),
            entitled_product_ids = listOf(),
            entitled_variant_ids = listOf(),
            id = 987654321L,
            once_per_customer = true,
            prerequisite_collection_ids = listOf(),
            prerequisite_customer_ids = listOf(),
            prerequisite_product_ids = listOf(),
            prerequisite_quantity_range = null,
            prerequisite_shipping_price_range = null,
            prerequisite_subtotal_range = PrerequisiteSubtotalRange(greater_than_or_equal_to = "50.00"),
            prerequisite_to_entitlement_purchase = PrerequisiteToEntitlementPurchase(prerequisite_amount = 1),
            prerequisite_to_entitlement_quantity_ratio = PrerequisiteToEntitlementQuantityRatio(
                prerequisite_quantity = 1,
                entitled_quantity = 1
            ),
            prerequisite_variant_ids = listOf(),
            starts_at = "2024-09-01T08:00:00Z",
            target_selection = "all",
            target_type = "line_item",
            title = "10% Off Orders Over $50",
            updated_at = "2024-09-15T12:00:00Z",
            usage_limit = 100,
            value = "10.0",
            value_type = "percentage"
        ))}
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
      return flow{ emit(AllProduct(products)) }
    }

    override suspend fun getAllDrafts(): Flow<List<DraftOrder>> {
       return flow{ emit(draftOrders) }
    }

    override suspend fun getTempProductById(id: Long): Product {
        TODO("Not yet implemented")
    }
}
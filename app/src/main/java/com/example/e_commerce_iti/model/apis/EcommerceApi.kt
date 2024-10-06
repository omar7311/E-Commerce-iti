package com.example.e_commerce_iti.model.apis

import com.example.e_commerce_iti.model.pojos.ProductResponse
import com.example.e_commerce_iti.model.pojos.SmartCollectionResponse
import com.example.e_commerce_iti.model.pojos.customer.Customer
import com.example.e_commerce_iti.model.pojos.customer.SearchedReslutCustomer
import com.example.e_commerce_iti.model.pojos.discountcode.DiscountCode
import com.example.e_commerce_iti.model.pojos.price_rules.PriceRules
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import com.example.e_commerce_iti.model.pojos.CustomCollectionsResponse
import com.example.e_commerce_iti.model.pojos.OrderResponse
import com.example.e_commerce_iti.model.pojos.customer.CustomerX
import com.example.e_commerce_iti.model.pojos.draftorder.DraftOrder
import com.example.e_commerce_iti.model.pojos.metadata.MetaData
import com.example.e_commerce_iti.model.pojos.metadata.Metafield
import com.example.e_commerce_iti.model.pojos.updatecustomer.UCustomer
import com.example.e_commerce_iti.model.pojos.updatecustomer.UpdateCustomer
import org.json.JSONObject
import retrofit2.Response
import retrofit2.http.PUT

/**
 *      frist start to create a brand get function
 *      2 - getting products by brand Title like "Adidas"
 */
interface EcommerceApi {

    @GET("smart_collections.json")
    suspend fun getSmartCollections(): SmartCollectionResponse
    @GET("price_rules.json")
    suspend fun getPriceRules(): PriceRules
    @GET("price_rules/{priceId}/discount_codes.json")
    suspend fun getCopuons(@Path("priceId") priceId: Long) : DiscountCode

    @GET("products.json")
    suspend fun getProductsByVendorID(@Query("vendor") vendorName: String): ProductResponse
    @POST("customers.json")
    suspend fun createCustomer(@Body customer: Customer): Response<Customer>
    @GET("customers/search.json")
    suspend fun searchCustomerByEmail(@Query("query") query: String): SearchedReslutCustomer
    @GET("customers/{customer_id}/metafields.json")
    suspend fun getCustomerMetafields(@Path("customer_id") customerId: Long): MetaData
    // get the custom collections
    @GET("custom_collections.json")
    suspend fun getCustomCollections(): CustomCollectionsResponse

    // get the products by custom collection
    @GET("products.json")
    suspend fun getProductsByCustomCollection(@Query("collection_id") collectionId: Long): ProductResponse
    @POST("draft_orders/{IdDO}.json")
    suspend fun updateDraftOrder(@Path("IdDO") IdDO: Long, @Body draftOrder: DraftOrder): DraftOrder
    @GET("draft_orders.json")
    suspend fun createDraftOrder(@Body draftOrder: DraftOrder): DraftOrder
    @POST("customers/{customer_id}/metafields.json")
    suspend fun updateCustomerMetafields(@Path("customer_id") customerId: Long, @Body metafields: MetaData): MetaData
    @PUT("customers/{customer_id}.json")
    suspend fun updateCustomer(@Path("customer_id") customerId: Long, @Body customer: UpdateCustomer): Response<Customer>

    /**
     *      function to get the orders by customer id
     */

    @GET("orders.json")
    suspend fun getOrdersByCustomerId(@Query("customer_id") customerId: Long): OrderResponse

}
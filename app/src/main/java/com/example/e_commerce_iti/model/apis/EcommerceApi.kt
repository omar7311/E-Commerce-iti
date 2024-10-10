package com.example.e_commerce_iti.model.apis

import com.example.e_commerce_iti.model.pojos.AllProduct
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
import com.example.e_commerce_iti.model.pojos.Product
import com.example.e_commerce_iti.model.pojos.customer.CustomerX
import com.example.e_commerce_iti.model.pojos.OrdersResponse
import com.example.e_commerce_iti.model.pojos.ProductWrapper
import com.example.e_commerce_iti.model.pojos.SearchedProductResponse
import com.example.e_commerce_iti.model.pojos.discountcode.FoundedDiscountCode
import com.example.e_commerce_iti.model.pojos.draftorder.AllDraftOrder
import com.example.e_commerce_iti.model.pojos.draftorder.DraftOrder
import com.example.e_commerce_iti.model.pojos.draftorder.SearchDraftOrder
import com.example.e_commerce_iti.model.pojos.metadata.MetaData
import com.example.e_commerce_iti.model.pojos.metadata.Metafield
import com.example.e_commerce_iti.model.pojos.metadata.ReMetaData
import com.example.e_commerce_iti.model.pojos.price_rules.FoundPriceRules
import com.example.e_commerce_iti.model.pojos.repsonemetadata.FullMeatDataResponse
import com.example.e_commerce_iti.model.pojos.repsonemetadata.ResponseMetaData
import com.example.e_commerce_iti.model.pojos.repsonemetadata.UMeatDataResponse
import com.example.e_commerce_iti.model.pojos.responsedorder.Responsed_order
import com.example.e_commerce_iti.model.pojos.updatecustomer.UCustomer
import com.example.e_commerce_iti.model.pojos.updatecustomer.UpdateCustomer
import com.example.e_commerce_iti.model.remote.RDraftOrderRequest
import com.example.e_commerce_iti.model.remote.UReposeMeta
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Response
import retrofit2.http.DELETE
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
    suspend fun getCustomerMetafields(@Path("customer_id") customerId: Long): FullMeatDataResponse
    // get the custom collections
    @GET("custom_collections.json")
    suspend fun getCustomCollections(): CustomCollectionsResponse
    // get the products by custom collection
    @GET("products.json")
    suspend fun getProductsByCustomCollection(@Query("collection_id") collectionId: Long): ProductResponse
    @POST("draft_orders/{ahmed}.json")
    suspend fun updateDraftOrder(@Path("ahmed") IdDO: Long, @Body draftOrder: DraftOrder): DraftOrder
    @POST("draft_orders.json")
    suspend fun createDraftOrder(@Body draftOrder: RDraftOrderRequest): Response<SearchDraftOrder>
    @POST("customers/{customer_id}/metafields.json")
    suspend fun updateCustomerMetafields(@Path("customer_id") customerId: Long, @Body metafields: MetaData): MetaData
    @PUT("customers/{customer_id}.json")
    suspend fun updateCustomer(@Path("customer_id") customerId: Long, @Body customer: UpdateCustomer): Response<Customer>
    @GET("draft_orders/{id}.json")
    suspend fun getCart(@Path("id") id: Long): Response<SearchDraftOrder>
    @GET("products/{id}.json")
    suspend fun getProduct(@Path("id") id: Long): SearchedProductResponse
    @GET("products.json")
    suspend fun getAllProduct(): AllProduct
    @POST("customers/{customer_id}/metafields.json")
    suspend fun createCustomerMetafields(@Path("customer_id") customerId: Long, @Body metafields: ReMetaData): UMeatDataResponse

    /**
     *      function to get the orders by customer id
     */

    @GET("orders.json")
    suspend fun getOrdersByCustomerId(@Query("customer_id") customerId: Long): OrdersResponse

    /**
     *      get product by id
     */
    @GET("products/{productId}.json")
    suspend fun getProductById(@Path("productId") productId: Long): Response<ProductWrapper>

    @PUT("draft_orders/{id}.json")
    suspend fun updateCartDraftOrder(@Path("id") id: Long, @Body draftOrder: SearchDraftOrder): Response<SearchDraftOrder>
    @GET("discount_codes/lookup.json")
    suspend fun getDiscountCode(@Query("code") code: String): FoundedDiscountCode
    @GET("price_rules/{priceId}.json")
    suspend fun getPriceRulesByid( @Path("priceId") priceId: Long): FoundPriceRules
    @PUT("customers/{customer_id}/metafields/{mid}.json")
    suspend fun updateCustomerMetafield(@Path("customer_id") customerId: Long, @Path("mid") metafieldId: Long, @Body metafield: UReposeMeta): ResponseMetaData
    @PUT("draft_orders/{id}/complete.json")
    suspend fun completeDraftOrder(@Path("id") id: Long): ResponseBody
    @PUT("draft_orders/{id}/send_invoice.json")
    suspend fun sendInvoice(@Path("id") id: Long)
    @GET("draft_orders.json")
    suspend fun getAllDrafts(): AllDraftOrder
    @PUT("customers/{customer_id}/metafields/{mid}.json")
    suspend fun updateCustomerMetafield2(@Path("customer_id") customerId: Long, @Path("mid") metafieldId: Long, @Body metafield: UReposeMeta): ResponseBody

}
package com.example.e_commerce_iti.model.remote

import android.util.Log
import com.example.e_commerce_iti.model.apis.RetrofitHelper
import com.example.e_commerce_iti.model.pojos.AllProduct
import com.example.e_commerce_iti.model.pojos.BrandData
import com.example.e_commerce_iti.model.pojos.CustomCollection
import com.example.e_commerce_iti.model.pojos.Order
import com.example.e_commerce_iti.model.pojos.Product

import com.example.e_commerce_iti.model.pojos.customer.Customer
import com.example.e_commerce_iti.model.pojos.customer.CustomerX
import com.example.e_commerce_iti.model.pojos.discountcode.DiscountCode
import com.example.e_commerce_iti.model.pojos.draftorder.DraftOrder
import com.example.e_commerce_iti.model.pojos.draftorder.LineItems
import com.example.e_commerce_iti.model.pojos.draftorder.SearchDraftOrder
import com.example.e_commerce_iti.model.pojos.metadata.MetaData
import com.example.e_commerce_iti.model.pojos.metadata.Metafield
import com.example.e_commerce_iti.model.pojos.metadata.ReMetaData
import com.example.e_commerce_iti.model.pojos.price_rules.PriceRules
import com.example.e_commerce_iti.model.pojos.updatecustomer.UpdateCustomer
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import org.json.JSONObject
import retrofit2.HttpException

class RemoteDataSourceImp : IRemoteDataSource {
    override suspend fun getBrands(): Flow<List<BrandData>> {

        val response = RetrofitHelper.service.getSmartCollections()
        val brands = response.collections.map {
            BrandData(
                id = it.id,
                title = it.title,
                imageSrc = it.image?.src,
                imageWidth = it.image?.width,
                imageHeight = it.image?.height,
            )

        }
        return flow {
            emit(brands)
        }
    }

    /**
     *      get Products by vendor name
     */
    override suspend fun getProductsByVendor(vendorName: String): Flow<List<Product>> {
        // val response = RetrofitHelper.service.getProductsByVendorID(vendorName)
        return flow {
            try {
                val response = RetrofitHelper.service.getProductsByVendorID(vendorName)
                emit(response.products)
            } catch (e: HttpException) {
                Log.e("API_ERROR", "Error fetching products by collection: ${e.message()}")
                emit(emptyList())
            }
        }
    }

    override suspend fun getCustomer(email: String): Flow<CustomerX> {

        val data = RetrofitHelper.service.searchCustomerByEmail("email:${email}")
        return flow { emit(data.customers!!.get(0)) }
    }

    override suspend fun createCustomer(customer: Customer): Flow<Customer> {
        Log.e("1231232131231adasdadad3213", "${customer} ------------ ")
        val helper = RetrofitHelper.service
        val response = helper.createCustomer(customer)
        val cart = createDumpDraft(response.customer!!)
        val fav = createDumpDraft(response.customer!!)
        println("cart        ${cart}")
        println("fav        ${fav}")
        var data = Gson().fromJson(Gson().toJson(cart), RDraftOrderRequest::class.java)
        println("asdasdsad  ${Gson().toJson(cart)}")
        val cartDraft = helper.createDraftOrder(data)
        data = Gson().fromJson(Gson().toJson(fav), RDraftOrderRequest::class.java)
        val favDraft = helper.createDraftOrder(data)
        println("eeeeeeeeeeeeeeeee   ${cartDraft.errorBody()?.string()}")
        val cartmeta =
            createDummyMetafield("cart_id", cartDraft.body()!!.draft_order!!.id.toString())
        println("55555555555555555  ${Gson().toJson(cartmeta)}")
        val favmeta = createDummyMetafield("fav_id", favDraft.body()!!.draft_order!!.id.toString())
        helper.createCustomerMetafields(response.customer!!.id!!, cartmeta)
        helper.createCustomerMetafields(response.customer!!.id!!, favmeta)
        return flow { emit(response) }
    }

    fun createDummyMetafield(key: String, value: String) = ReMetaData(
        Metafield(
            namespace = "namespace",
            key = key,
            value = value,
            value_type = "string"
        )
    )

    private fun createDumpDraft(customerx: CustomerX): SearchDraftOrder {
        val searchDraftOrder = SearchDraftOrder()
        val lineItems = LineItems()
        lineItems.quantity = 1
        lineItems.price = "0.00"
        lineItems.title = "Dummy"
        val customer = com.example.e_commerce_iti.model.pojos.draftorder.Customer(id = customerx.id)
        val lineItem = listOf(lineItems)
        val draftOrder = DraftOrder(line_items = lineItem, customer = customer)
        searchDraftOrder.draft_order = draftOrder
        return searchDraftOrder
    }

    override suspend fun updateCustomer(id: Long, customer: String): Flow<Customer> {
        val ucustomer = Gson().fromJson(customer, UpdateCustomer::class.java)
        val req = RetrofitHelper.service.updateCustomer(id, ucustomer)
        Log.e("12312321312313213", "${req.errorBody()?.string()}")
        return flow { emit(Customer(req.body()?.customer)) }
    }

    override suspend fun getCurrency(currency: String) =
        flow { emit(RetrofitHelper.currencyService.getCurrencies()) }



    override suspend fun getMetaFields(customerId: Long): Flow<MetaData> {
        return flow { emit(RetrofitHelper.service.getCustomerMetafields(customerId)) }
    }

    override suspend fun updateCart(cart: DraftOrder): Flow<DraftOrder> {
        val data = RetrofitHelper.service.updateCartDraftOrder(cart.id!!, SearchDraftOrder(cart))
        println(cart)
        println(data.errorBody()?.string())
        return flow { emit(data.body()!!.draft_order!!) }
    }

    override suspend fun getCart(id: Long): Flow<DraftOrder> {
        val data = RetrofitHelper.service.getCart(id)
        Log.e("12312321312313213", "${data} ------------ $id")
        return flow { emit(data.draft_order!!) }
    }

    override suspend fun getProductByID(id: Long): Flow<Product> {
        val data = RetrofitHelper.service.getProduct(id)
        Log.e("12312321312313213", "${data} ------------ $id")
        return flow { emit(data.product) }
    }

    /**
     *      get orders by customer id
     */
    override suspend fun getOrdersByCustomerId(customer_id: Long): Flow<List<Order>> {
        return flow { emit(RetrofitHelper.service.getOrdersByCustomerId(customer_id).orders) }
    }

    /**
     *      get Product By id
     */
    override suspend fun getProductById(productId: Long): Flow<Product> {
        // Make the network call to fetch the product by ID
        val response = RetrofitHelper.service.getProduct(productId)
        Log.e("API Error", "Error fetching product: $response")
        return flow { response.product }
    }

    override  fun getAllProduct(): Flow<AllProduct> = flow{
        emit(RetrofitHelper.service.getAllProduct())
    }


    // to get the custom collections
    override suspend fun getCustomCollections(): Flow<List<CustomCollection>> = flow {
        try {
            val response = RetrofitHelper.service.getCustomCollections()
            emit(response.custom_collections)
        } catch (e: Exception) {
            // Handle the exception (e.g., log it, show a message)
            Log.e("API_ERROR", "Failed to fetch custom collections: ${e.message}")
            emit(emptyList()) // Emit an empty list or you can also emit a specific error state
        }
    }

    // get Products by custom collection id
    override suspend fun getProductsByCustomCollection(collectionId: Long): Flow<List<Product>> =
        flow {
            try {
                val response = RetrofitHelper.service.getProductsByCustomCollection(collectionId)
                emit(response.products)
            } catch (e: Exception) {
                // Handle the exception (e.g., log it, show a message)
                Log.e(
                    "API_ERROR",
                    "Failed to fetch products for collection ID $collectionId: ${e.message}"
                )
                emit(emptyList()) // Emit an empty list or handle error state accordingly
            }
        }

    override suspend fun getPriceRules(): Flow<PriceRules> {
        return flow { emit(RetrofitHelper.service.getPriceRules()) }
    }

    override suspend fun getCopuons(priceId: Long): Flow<DiscountCode> {
        val data = RetrofitHelper.service.getCopuons(priceId)
        return flow { emit(data) }
    }
}

data class RDraftOrderRequest(
    val draft_order: RDraftOrder
)

data class RDraftOrder(
    val customer: Customer,
    val line_items: List<RLineItem>
)

data class RCustomer(
    val id: Long
)

data class RLineItem(
    val price: String,
    val quantity: Int,
    val title: String
)
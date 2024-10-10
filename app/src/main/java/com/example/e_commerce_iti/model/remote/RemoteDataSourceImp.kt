package com.example.e_commerce_iti.model.remote

import android.util.Log
import com.example.e_commerce_iti.currentUser
import com.example.e_commerce_iti.metadata
import com.example.e_commerce_iti.model.apis.RetrofitHelper
import com.example.e_commerce_iti.model.pojos.AllProduct
import com.example.e_commerce_iti.model.pojos.BrandData
import com.example.e_commerce_iti.model.pojos.CustomCollection
import com.example.e_commerce_iti.model.pojos.Order
import com.example.e_commerce_iti.model.pojos.Product
import com.example.e_commerce_iti.model.pojos.Producut

import com.example.e_commerce_iti.model.pojos.customer.Customer
import com.example.e_commerce_iti.model.pojos.customer.CustomerX
import com.example.e_commerce_iti.model.pojos.discountcode.DiscountCode
import com.example.e_commerce_iti.model.pojos.discountcode.DiscountCodeX
import com.example.e_commerce_iti.model.pojos.draftorder.DraftOrder
import com.example.e_commerce_iti.model.pojos.draftorder.LineItems
import com.example.e_commerce_iti.model.pojos.draftorder.SearchDraftOrder
import com.example.e_commerce_iti.model.pojos.metadata.Metafield
import com.example.e_commerce_iti.model.pojos.metadata.ReMetaData
import com.example.e_commerce_iti.model.pojos.price_rules.PriceRule
import com.example.e_commerce_iti.model.pojos.price_rules.PriceRules
import com.example.e_commerce_iti.model.pojos.repsonemetadata.FullMeatDataResponse
import com.example.e_commerce_iti.model.pojos.repsonemetadata.ResponseMetaData
import com.example.e_commerce_iti.model.pojos.updatecustomer.UpdateCustomer
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import okhttp3.ResponseBody
import retrofit2.HttpException
import retrofit2.Response

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

    override suspend fun createCustomer(customer: Customer) {
        Log.e("CustomerCreation", "Customer data: $customer")

        val helper = RetrofitHelper.service

        try {
            // Create customer request
            val customerResponse = helper.createCustomer(customer)
            Log.i("CustomerCreation", "Error body: ${customerResponse.errorBody()?.string()}")

            val response = customerResponse.body()!!
            Log.e("CustomerCreation", "Customer created: $response")

            // Create draft orders for cart and favorites
            val cartDraftData = createDumpDraft(response.customer!!)
            val favDraftData = createDumpDraft(response.customer!!)

            // Convert drafts to RDraftOrderRequest and send API requests
            var data = Gson().fromJson(Gson().toJson(cartDraftData), RDraftOrderRequest::class.java)
            val cartDraftResponse = helper.createDraftOrder(data)

            data = Gson().fromJson(Gson().toJson(favDraftData), RDraftOrderRequest::class.java)
            val favDraftResponse = helper.createDraftOrder(data)

            Log.i("DraftOrder", "Cart draft error: ${cartDraftResponse.errorBody()?.string()}")

            // Create metafields for cart and favorites
            val cartMeta = createDummyMetafield(
                "cart_id",
                cartDraftResponse.body()!!.draft_order!!.id.toString()
            )
            Log.i("DraftOrder", "Cart metafield: ${Gson().toJson(cartMeta)}")

            val favMeta = createDummyMetafield(
                "fav_id",
                favDraftResponse.body()!!.draft_order!!.id.toString()
            )
            Log.i("DraftOrder", "Favorite metafield: $favMeta")

            // Associate metafields with customer
            val cartMetaResponse =
                helper.createCustomerMetafields(response.customer!!.id!!, cartMeta)
            Log.i("MetafieldCreation", "Cart metafield response: $cartMetaResponse")

            val favMetaResponse = helper.createCustomerMetafields(response.customer!!.id!!, favMeta)
            Log.i("MetafieldCreation", "Favorite metafield response: $favMetaResponse")

            // Log the result with all necessary details
            Log.i(
                "CustomerCreationSummary",
                "Customer email = ${customer.customer?.email}, id = ${response.customer?.id}, " +
                        "name = ${response.customer?.first_name}, cartMeta = ${cartMetaResponse.body()!!.metafield.id}, " +
                        "favMeta = ${favMetaResponse.body()!!.metafield.id}"
            )

        } catch (e: Exception) {
            Log.e("CustomerCreationError", "Error occurred: $e")
        }
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
        lineItems.taxable = false
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
        currentUser!!.email = ucustomer!!.customer!!.email!!
        currentUser!!.name = ucustomer.customer!!.first_name!!
        currentUser!!.lname = ucustomer.customer!!.last_name!!
        currentUser!!.id = ucustomer.customer!!.id!!
        currentUser?.address =
            (req.body()?.customer?.addresses?.get(0)?.address1 ?: currentUser!!.address)
        Log.e("12312321312313213", "${req.errorBody()?.string()}")
        return flow { emit(Customer(req.body()?.customer)) }
    }


    override suspend fun getCurrency(currency: String) =
        flow { emit(RetrofitHelper.currencyService.getCurrencies()) }

    override suspend fun getMetaFields(customerId: Long): Flow<FullMeatDataResponse> {

        return flow { emit(RetrofitHelper.service.getCustomerMetafields(customerId)) }
    }

    override suspend fun updateCart(cart: DraftOrder): Flow<DraftOrder> {
        val data = RetrofitHelper.service.updateCartDraftOrder(cart.id!!, SearchDraftOrder(cart))
        println(cart)
        Log.e("12312321312313213", "${data} ------------ ")
        Log.e("12312321312313213", "${data.errorBody()?.string()} ------------ ")
        println(data.errorBody()?.string())
        return flow { emit(data.body()!!.draft_order!!) }
    }

    override suspend fun getCart(id: Long): Flow<DraftOrder> {
        val data = RetrofitHelper.service.getCart(id)
        Log.e("12312321312313213", "${data.errorBody()?.string()} ----${data.message()}--- $id")
        return flow { emit(data.body()?.draft_order!!) }
    }

    override suspend fun getProductByID(id: Long): Flow<Product> {
        val data = RetrofitHelper.service.getProduct(id)
        Log.e("12312321312313213", "${data} ------------ $id")
        return flow { emit(data.product) }
    }

    override suspend fun getDiscountCode(code: String): Flow<DiscountCodeX> {
        Log.e("12312321312313213", "${code} ------------ ")
        val data = RetrofitHelper.service.getDiscountCode(code)
        Log.e("12312321312313213", "${data} ------------ ")
        return flow { emit(data.discount_code) }
    }

    override suspend fun getPriceRulesByid(priceId: Long): Flow<PriceRule> {
        return flow { emit(RetrofitHelper.service.getPriceRulesByid(priceId).price_rule) }
    }

    override suspend fun updateMetaData(
        id: Long,
        metaData: ResponseMetaData
    ): Flow<ResponseMetaData> {
        return flow {
            emit(
                RetrofitHelper.service.updateCustomerMetafield(
                    id,
                    metaData.id!!,
                    UReposeMeta(metaData)
                ).body()!!.metafield
            )
        }
    }

    override suspend fun compeleteDraftOrder(draftOrder: DraftOrder): Flow<Boolean> {
        Log.e("mmmmmmmmmmmmmmmmmmmmmmmmmmmmmm", "${draftOrder.id} ------------ ")
        updateCart(draftOrder)
        draftOrder.email = currentUser?.email
        //  RetrofitHelper.service.sendInvoice(draftOrder.id!!,)
        val f = RetrofitHelper.service.completeDraftOrder(draftOrder.id!!)
        val data = create_draftorder(f)
        Log.e("eeeeeeeeeeeeeeeeeeeeeeeee", "create draft  -> ${data.errorBody()}")
        metadata?.value = data.body()!!.draft_order!!.id.toString()
        val tmp = RetrofitHelper.service.updateCustomerMetafield(
            currentUser!!.id,
            metadata!!.id!!,
            UReposeMeta(metadata!!)
        )
        Log.e("eeeeeeeeeeeeeeeeeeeeeeeee", "update meta draft  -> ${tmp.errorBody()}")
        Log.e("eeeeeeeeeeeeeeeeeeeeeeeee", "update meta draft  -> ${tmp.body()}")

        metadata = tmp.body()!!.metafield
        Log.e("dasdsasdsadsadsad", "$metadata")
        currentUser!!.cart = metadata!!.value!!.toLong()

        Log.i("ddddddddddddddddddddddddd", "${metadata}")
        return flowOf(true)

    }

    private suspend fun create_draftorder(f: ResponseBody): Response<SearchDraftOrder> {
        Log.e("nnnnnnnnnnnnnnnnnnnnnnnnnnn", f.string())
        Log.e("nnnnnnnnnnnnnnnnnnnnnnnnnnn", f.string())

        val draft1 = createDumpDraft(
            CustomerX(
                id = currentUser!!.id,
                email = currentUser!!.email,
                first_name = currentUser!!.name,
                last_name = currentUser!!.lname
            )
        )
        val draft = Gson().fromJson(Gson().toJson(draft1), RDraftOrderRequest::class.java)
        val data = RetrofitHelper.service.createDraftOrder(draft)
        return data
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
        return flow {
            try {
                val response = RetrofitHelper.service.getProductById(productId)

                // Log the response status if needed
                if (response.isSuccessful && response.body() != null) {
                    emit(response.body()!!.product) // Emit the product
                } else {
                    // Log the error if the response failed
                    val errorBody = response.errorBody()?.string() ?: "Unknown error"
                    Log.e("API Error", "Error fetching product: $errorBody")
                    throw Exception("Error fetching product: $errorBody")
                }
            } catch (e: Exception) {
                // Log the exception
                Log.e("API Error", "Network or API error: ${e.message}", e)
                throw e // Rethrow the exception for upstream handling
            }
        }
    }

    override suspend fun getTempProductById(id: Long): Product {
        val response = RetrofitHelper.service.getProduct(id).product
        Log.i("ProductsFetched", "FetchProductsDetails: $response")
        return response
    }

    override fun getAllProduct(): Flow<AllProduct> = flow {
        emit(RetrofitHelper.service.getAllProduct())
    }

    override suspend fun getAllDrafts(): Flow<List<DraftOrder>> {
        return flow { emit(RetrofitHelper.service.getAllDrafts().draft_orders!!) }
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

data class RLineItem(
    val price: String,
    val quantity: Int,
    val title: String
)

data class UReposeMeta(val metafield: ResponseMetaData)
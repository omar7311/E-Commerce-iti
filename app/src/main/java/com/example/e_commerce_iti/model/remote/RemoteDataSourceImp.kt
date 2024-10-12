package com.example.e_commerce_iti.model.remote

import android.util.Log
import com.example.e_commerce_iti.CurrentUser
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
import com.example.e_commerce_iti.model.pojos.invoice.DraftOrderInvoice
import com.example.e_commerce_iti.model.pojos.metadata.MetaData
import com.example.e_commerce_iti.model.pojos.metadata.Metafield
import com.example.e_commerce_iti.model.pojos.metadata.ReMetaData
import com.example.e_commerce_iti.model.pojos.price_rules.PriceRule
import com.example.e_commerce_iti.model.pojos.price_rules.PriceRules
import com.example.e_commerce_iti.model.pojos.repsonemetadata.FullMeatDataResponse
import com.example.e_commerce_iti.model.pojos.repsonemetadata.ResponseMetaData
import com.example.e_commerce_iti.model.pojos.updatecustomer.UCustomer
import com.example.e_commerce_iti.model.pojos.updatecustomer.UpdateCustomer
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import org.json.JSONObject
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
        Log.e("qweqwewqeeeeeeeeeee", "${customer} ------------ ")
        val helper = RetrofitHelper.service
        val response1 = helper.createCustomer(customer)
        if (!response1.isSuccessful) {
            throw Exception(response1.errorBody()?.string())
        }
        Log.i("eoorradasdesadasd", "${response1.errorBody()?.string()} ------------ ")
        val response = response1.body()!!
        Log.e("reasdasdsdsdsdsdsa213212eq", "${response} ------------ ")
        val cart = createDumpDraft(response.customer!!)
        val fav = createDumpDraft(response.customer!!)
        var data = Gson().fromJson(Gson().toJson(cart), RDraftOrderRequest::class.java)
        val cartDraft = helper.createDraftOrder(data)
        data = Gson().fromJson(Gson().toJson(fav), RDraftOrderRequest::class.java)
        val favDraft = helper.createDraftOrder(data)
        Log.i("eeeeeeeeeeeeeeeee", "${cartDraft.errorBody()?.string()}")
        val cartmeta =
            createDummyMetafield("cart_id", cartDraft.body()!!.draft_order!!.id.toString())
        Log.i("55555555555555555draftfav", Gson().toJson(cartmeta))
        val favmeta = createDummyMetafield("fav_id", favDraft.body()!!.draft_order!!.id.toString())
        Log.i("55555555555555555draftfav", "${favmeta}")
        val a = helper.createCustomerMetafields(response.customer!!.id!!, cartmeta)
        Log.i("deeeeeeeeeee cart", "${a}")
        val b = helper.createCustomerMetafields(response.customer!!.id!!, favmeta)
        Log.i("deeeeeeeeeee fav", "${b}")
        Log.i(
            "resopne from raeteunseadsa",
            "email = ${customer.customer?.email},id=${response.customer?.id},name = ${response.customer?.first_name}, cart = ${a.body()!!.metafield.id},fav= ${b.body()!!.metafield.id}"
        )
        metadata = b.body()!!.metafield
        withContext(Dispatchers.Main) {
            currentUser.value = CurrentUser(
                id = response.customer!!.id!!,
                cart = a.body()!!.metafield.value!!.toLong(),
                fav = b.body()!!.metafield.value!!.toLong(),
                name = response.customer!!.first_name!!,
                lname = response.customer!!.last_name!!,
                email = response.customer!!.email!!
            )
        }
    }
    fun createDummyMetafield(key: String, value: String) = ReMetaData(Metafield(namespace = "namespace", key = key, value = value, value_type = "string"))
    private fun createDumpDraft(customerx: CustomerX):SearchDraftOrder{
        val searchDraftOrder=SearchDraftOrder()
        val lineItems=LineItems()
        lineItems.quantity=1
        lineItems.taxable=false
        lineItems.price="0.00"
        lineItems.title="Dummy"
        val customer= com.example.e_commerce_iti.model.pojos.draftorder.Customer(id =customerx.id)
        val lineItem= listOf(lineItems)
        val draftOrder=DraftOrder(line_items = lineItem, customer = customer,)
        searchDraftOrder.draft_order=draftOrder
        return searchDraftOrder
    }

    override suspend fun updateCustomer(id: Long, customer: String): Flow<Customer> {
        val ucustomer = Gson().fromJson(customer, UpdateCustomer::class.java)
        val req = RetrofitHelper.service.updateCustomer(id, ucustomer)
        withContext(Dispatchers.Main) {
            currentUser.value = CurrentUser(
                id = ucustomer.customer!!.id!!,
                name = ucustomer.customer!!.first_name!!,
                lname = ucustomer.customer!!.last_name!!,
                email = ucustomer.customer!!.email!!,
                address = (req.body()?.customer?.addresses?.get(0)?.address1
                    ?: currentUser.value!!.address)
            )

        }
        return flow { emit(Customer(req.body()?.customer)) }
    }



    override suspend fun getCurrency(currency: String)=flow { emit(RetrofitHelper.currencyService.getCurrencies()) }
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
        val data=RetrofitHelper.service.getDiscountCode(code)
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
        return flow { emit(RetrofitHelper.service.updateCustomerMetafield(id,metaData.id!!,UReposeMeta(metaData)).body()!!.metafield)}
    }

    override suspend fun compeleteDraftOrder(draftOrder: DraftOrder): Flow<Boolean> {
        Log.e("DraftOrder", "$draftOrder ------------ ")

        // Filter out line items with null product_id
        draftOrder.line_items = draftOrder.line_items.filter { it.product_id != null }

        // Set email and invoice information
        draftOrder.invoice_sent_at = currentUser.value?.email
        draftOrder.email = currentUser.value?.email

        Log.e("CurrentUserEmail", "Current user email: ${currentUser.value?.email}")

        // Update cart
        val cart = updateCart(draftOrder).first()
        Log.e("UpdateCart", "Updated cart -> $cart")

        draftOrder.email = currentUser.value?.email

        // Try to send invoice
        try {
            RetrofitHelper.service.sendInvoice(draftOrder.id!!)
        } catch (e: Exception) {
            Log.e("SendInvoiceError", "Error sending invoice -> ${e.message}")
        }

        // Complete draft order
        val response = RetrofitHelper.service.completeDraftOrder(draftOrder.id!!)
        Log.e("CompleteDraftOrder", response.string())

        // Try to send invoice again
        try {
            RetrofitHelper.service.sendInvoice(draftOrder.id!!)
        } catch (e: Exception) {
            Log.e("SendInvoiceError", "Error sending invoice -> ${e.message}")
        }

        // Create draft order
        val data = create_draftorder(response)
        Log.e("CreateDraftOrder", "Create draft response -> ${data.errorBody()}")

        // Update metadata with draft order ID
        metadata?.value = data.body()!!.draft_order!!.id.toString()
        val updateMetaResponse = RetrofitHelper.service.updateCustomerMetafield(
            currentUser.value!!.id,
            metadata!!.id!!,
            UReposeMeta(metadata!!)
        )

        Log.e("UpdateMetafieldError", "Update meta response error -> ${updateMetaResponse.errorBody()}")
        Log.e("UpdateMetafieldSuccess", "Update meta response body -> ${updateMetaResponse.body()}")

        // Try to send invoice again
        try {
            RetrofitHelper.service.sendInvoice(draftOrder.id!!)
        } catch (e: Exception) {
            Log.e("SendInvoiceError", "Error sending invoice -> ${e.message}")
        }

        // Update current user metadata
        metadata = updateMetaResponse.body()!!.metafield
        Log.e("UpdatedMetadata", "$metadata")
        currentUser.value?.cart = metadata!!.value!!.toLong()

        Log.i("FinalMetadata", "${metadata}")

        return flowOf(true)
    }


    private suspend fun create_draftorder(f: ResponseBody): Response<SearchDraftOrder> {
        Log.e("nnnnnnnnnnnnnnnnnnnnnnnnnnn", f.string())
        Log.e("nnnnnnnnnnnnnnnnnnnnnnnnnnn", f.string())

        val draft1 = createDumpDraft(
            CustomerX(
                id = currentUser.value?.id,
                email =  currentUser.value?.email,
                first_name = currentUser.value?.name,
                last_name = currentUser.value?.lname
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
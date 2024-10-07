package com.example.e_commerce_iti

import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry
import com.example.e_commerce_iti.model.local.LocalDataSourceImp
import com.example.e_commerce_iti.model.pojos.customer.CustomerX
import com.example.e_commerce_iti.model.pojos.customer.Customer
import com.example.e_commerce_iti.model.pojos.draftorder.LineItems
import com.example.e_commerce_iti.model.remote.RDraftOrderRequest
import com.example.e_commerce_iti.model.remote.RemoteDataSourceImp
import com.example.e_commerce_iti.model.reposiatory.ReposiatoryImpl
import com.example.e_commerce_iti.ui.theme.createCustomer
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import org.junit.Test

import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(RobolectricTestRunner::class)
class ExampleUnitTest {
//    @Test
//    fun addition_isCorrect() :Unit= runBlocking {
//        val appContext: Context = InstrumentationRegistry.getInstrumentation().targetContext
//
//        val dataSourceImp= RemoteDataSourceImp()
//            val data= dataSourceImp.getPriceRules().firstOrNull()
//        for (item in data!!.price_rules){
//            println(item.id)
//            val data2= dataSourceImp.getCopuons(item.id).firstOrNull()
//            println(data2)
//        }
//            println(data)
//    }
    @Test
    fun createCustomertest():Unit= runBlocking {
        val data=RemoteDataSourceImp()

    val context: Context = InstrumentationRegistry.getInstrumentation().targetContext

//        val customerx= CustomerX(first_name = "gheaftfmed", last_name = "sgggagttfmy", email = "amgedtamer12345@gmail.com", phone = "+14125957999")
//        val customer=Customer(customer =customerx)
//        data.createCustomer(customer)

        val repo=ReposiatoryImpl((RemoteDataSourceImp()), LocalDataSourceImp(context.getSharedPreferences(LocalDataSourceImp.currencies, Context.MODE_PRIVATE)))
        val currentUser= CurrentUser(email="amgedtamer12345@gmail.com", id=7495181697201, fav=1003024744625, cart=1003024711857, name="gheaftfmed", phone="+14125957999")
        val carts= repo.getCart(currentUser.cart).first()

        carts.customer=
            com.example.e_commerce_iti.model.pojos.draftorder.Customer(id=currentUser.id, email=currentUser.email)
        val aeeee = ArrayList<LineItems>(carts.line_items)
        val lineItem=LineItems()
        lineItem.quantity=1
        lineItem.product_id=8141705806001
        lineItem.variant_id=44695693459633
        lineItem.price="80.00"
        lineItem.title="OS / black"
        lineItem.vendor="ADIDAS"
        aeeee.add(lineItem)
        carts.line_items=aeeee.toList()
        println(repo.updateCart(carts).first())
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
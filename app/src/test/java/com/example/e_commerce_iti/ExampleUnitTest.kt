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
//        val data=RemoteDataSourceImp()
//
    val context: Context = InstrumentationRegistry.getInstrumentation().targetContext
//
//        val customerx= CustomerX(first_name = "gheaftffmed", last_name = "sgggaagttfmy", email = "amgedtamer333@gmail.com")
//        val customer=Customer(customer =customerx)
//        data.createCustomer(customer)


        val repo=ReposiatoryImpl((RemoteDataSourceImp()), LocalDataSourceImp(context.getSharedPreferences(LocalDataSourceImp.currencies, Context.MODE_PRIVATE)))
       getCurrent("ahmedhassans3040@gmail.com",repo)
        val fav= repo.getCart(currentUser!!.fav).first()
    val carts= repo.getCart(currentUser!!.cart).first()
    println(carts.line_items)

//  //  println(fav.line_items)
//    println(fav.line_items.size)
//
//        //currentUser.fav
//        carts.customer= com.example.e_commerce_iti.model.pojos.draftorder.Customer(id= currentUser!!.id, email= currentUser!!.email)
//        println(carts)
//        val aeeee = ArrayList<LineItems>()
//    val lineItem2=LineItems()
//    lineItem2.quantity=1
//    lineItem2.product_id=8141697286321
//    lineItem2.variant_id=44695674552497
//    lineItem2.price="119.00"
//    lineItem2.title="dasdsdsd"
//    lineItem2.vendor="sdsdsadas"
//    aeeee.add(lineItem2)
//    carts.line_items=aeeee.toList()
//    println(repo.updateCart(carts).first())

    //println(RemoteDataSourceImp().compeleteDraftOrder(carts))
    }
}

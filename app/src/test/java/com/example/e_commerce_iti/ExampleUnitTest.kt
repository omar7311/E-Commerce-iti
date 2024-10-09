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

        val customerx= CustomerX(first_name = "gheaftffmed", last_name = "sgggaagttfmy", email = "amgedtamer333@gmail.com")
        val customer=Customer(customer =customerx)
        data.createCustomer(customer)


//        val repo=ReposiatoryImpl((RemoteDataSourceImp()), LocalDataSourceImp(context.getSharedPreferences(LocalDataSourceImp.currencies, Context.MODE_PRIVATE)))
//       getCurrent("amgedtamer12345@gmail.com",repo)
//        val carts= repo.getCart(currentUser!!.cart).first()
//       //   println(carts)
//        //currentUser.fav
//        carts.customer=
//            com.example.e_commerce_iti.model.pojos.draftorder.Customer(id= currentUser!!.id, email= currentUser!!.email)
////    val aeeee = ArrayList<LineItems>()
////    val lineItem=LineItems()
////    val lineItem2=LineItems()
////    val lineItem3=LineItems()
////    val lineItem4=LineItems()
////
////    lineItem.price="00.00"
////    lineItem.title="Dummy"
////    lineItem.taxable=false
////    lineItem.quantity=3
////    lineItem.vendor="ADIDAS"
////    lineItem.taxable=false
////
////    lineItem2.quantity=1
////    lineItem2.product_id=8141705806001
////    lineItem2.variant_id=44695693459633
////    lineItem2.price="80.00"
////    lineItem2.title="OS / black"
////    lineItem2.vendor="ADIDAS"
////    lineItem3.quantity=3
////    lineItem3.product_id=8141703938225
////    lineItem3.variant_id=44695690379441
////    lineItem3.price="70.00"
////    lineItem3.title="4 / black"
////    lineItem3.vendor="NIKE"
////    lineItem4.quantity=1
////    lineItem4.product_id=8141702627505
////    lineItem4.variant_id=44695687889073
////    lineItem4.price="249.00"
////    lineItem4.title="4 / black"
////    lineItem4.vendor="NIKE"
////    aeeee.add(lineItem)
////    aeeee.add(lineItem4)
////    aeeee.add(lineItem2)
////    aeeee.add(lineItem3)
////    carts.line_items=aeeee.toList()
//
//       // println(repo.updateCart(carts).first())
//        println(RemoteDataSourceImp().compeleteDraftOrder(carts))
    }
}

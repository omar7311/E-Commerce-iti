package com.example.e_commerce_iti.model.reposiatory.remotes

import kotlinx.coroutines.runBlocking
import org.junit.Test

import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import java.util.Calendar

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
////
//    val context: Context = InstrumentationRegistry.getInstrumentation().targetContext
////
////        val customerx= CustomerX(first_name = "gheaftffmed", last_name = "sgggaagttfmy", email = "amgedtamer333@gmail.com")
////        val customer=Customer(customer =customerx)
////        data.createCustomer(customer)
//
//
//        val repo=ReposiatoryImpl((RemoteDataSourceImp()), LocalDataSourceImp(context.getSharedPreferences(LocalDataSourceImp.currencies, Context.MODE_PRIVATE)))
//       getCurrent("ahmedhassans3040@gmail.com",repo)
//        val fav= repo.getCart(currentUser!!.fav).first()
//    val carts= repo.getCart(currentUser!!.cart).first()
//    println(carts.line_items)
//
////  //  println(fav.line_items)
////    println(fav.line_items.size)
////
////        //currentUser.fav
////        carts.customer= com.example.e_commerce_iti.model.pojos.draftorder.Customer(id= currentUser!!.id, email= currentUser!!.email)
////        println(carts)
////        val aeeee = ArrayList<LineItems>()
////    val lineItem2=LineItems()
////    lineItem2.quantity=1
////    lineItem2.product_id=8141697286321
////    lineItem2.variant_id=44695674552497
////    lineItem2.price="119.00"
////    lineItem2.title="dasdsdsd"
////    lineItem2.vendor="sdsdsadas"
////    aeeee.add(lineItem2)
////    carts.line_items=aeeee.toList()
////    println(repo.updateCart(carts).first())
//
//    //println(RemoteDataSourceImp().compeleteDraftOrder(carts))
    println(isExpiryDateValid("01","24"))
    }


    fun isExpiryDateValid(month: String, year: String): Boolean {
        val currentYear = Calendar.getInstance().get(Calendar.YEAR) % 100
        val currentMonth = Calendar.getInstance().get(Calendar.MONTH) + 1

        val expiryMonth = month.toIntOrNull() ?: return false
        val expiryYear = year.toIntOrNull() ?: return false

        if (expiryYear < currentYear) return false
        if (expiryYear == currentYear && expiryMonth < currentMonth) return false
        if (expiryMonth !in 1..12) return false

        return true
    }

}

package com.example.e_commerce_iti

import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry
import com.example.e_commerce_iti.model.remote.RemoteDataSourceImp
import com.example.e_commerce_iti.ui.theme.createCustomer
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
    @Test
    fun addition_isCorrect() :Unit= runBlocking {
        val appContext: Context = InstrumentationRegistry.getInstrumentation().targetContext

        val dataSourceImp= RemoteDataSourceImp()
            val data= dataSourceImp.getPriceRules().firstOrNull()
        for (item in data!!.price_rules){
            println(item.id)
            val data2= dataSourceImp.getCopuons(item.id).firstOrNull()
            println(data2)
        }
            println(data)
    }
    @Test
    fun createCustomertest():Unit= runBlocking {
        val context = Robolectric.buildActivity(MainActivity::class.java).create().get()
        val dataSourceImp= RemoteDataSourceImp()
       val customer= dataSourceImp.createCustomer(createCustomer("amgedtamer123456789@gmail.com","amged","tamer"))
       println(customer.firstOrNull())
    }
}
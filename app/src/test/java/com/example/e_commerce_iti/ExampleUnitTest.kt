package com.example.e_commerce_iti

import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry
import com.example.e_commerce_iti.model.remote.RemoteDataSourceImp
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
}
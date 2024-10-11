package com.example.e_commerce_iti.model.reposiatory

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.e_commerce_iti.model.local.IlocalDataSource
import com.example.e_commerce_iti.model.local.LocalDataSourceImp
import com.example.e_commerce_iti.model.pojos.customer.Addresse
import com.example.e_commerce_iti.model.pojos.customer.Customer
import com.example.e_commerce_iti.model.pojos.customer.CustomerX
import com.example.e_commerce_iti.model.pojos.customer.DefaultAddress
import com.example.e_commerce_iti.model.remote.IRemoteDataSource
import com.example.e_commerce_iti.model.reposiatory.remotes.FakeLocalDataSource
import com.example.e_commerce_iti.model.reposiatory.remotes.FakeRemoteDataSource
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class ReposiatoryImplTest {

    lateinit var      remoteDataSource : IRemoteDataSource
    lateinit var  localDataSource : IlocalDataSource
    lateinit var  reposiatory:IReposiatory


    @Before
    fun setUp() {
        remoteDataSource = FakeRemoteDataSource()
        val context: Context = ApplicationProvider.getApplicationContext()

        localDataSource = FakeLocalDataSource(context.getSharedPreferences(LocalDataSourceImp.currentCurrency, Context.MODE_PRIVATE))
        reposiatory = ReposiatoryImpl(remoteDataSource, localDataSource)
    }
    /**
     * Test case 1: mostafa
     */
    @Test
    fun create_get_Customer() = runBlocking{
        val customer = CustomerX(
            addresses = listOf(
                Addresse(
                    address1 = "123 Maple Street",
                    city = "Springfield",
                    country = "USA",
                    zip = "12345",
                    phone = "+123456789"
                )
            ),
            admin_graphql_api_id = "gid://shopify/Customer/123456789",
            created_at = "2024-01-01T10:00:00Z",
            currency = "USD",
            default_address = DefaultAddress(
                address1 = "123 Maple Street",
                city = "Springfield",
                country = "USA",
                zip = "12345",
                phone = "+123456789"
            ),
            email = "alice.smith@example.com",
            first_name = "Alice",
            id = 10000123L,
            last_name = "Smith",
            orders_count = 5L,
            phone = "+123456789",
            state = "active",
            tags = "VIP, email_subscriber",
            tax_exempt = false,
            total_spent = "500.00",
            updated_at = "2024-09-10T08:00:00Z",
            verified_email = true
        )
              remoteDataSource.createCustomer(Customer(customer))

            val result = remoteDataSource.getCustomer("alice.smith@example.com").firstOrNull()

            println(result)
            assert(result?.email=="alice.smith@example.com")
    }
    @Test
    fun get_Copun() = runBlocking{
        val result = remoteDataSource.getDiscountCode("SPRING20").firstOrNull()
        println(result)
        assert(result?.code!=null)
    }
    @Test
    fun get_price_rule() = runBlocking {
        val result =  remoteDataSource.getDiscountCode("SPRING20").firstOrNull()
        val result2 = remoteDataSource.getPriceRulesByid(result?.price_rule_id!!).firstOrNull()
        println(result2)
        assert(result2?.title!=null)
    }
    @Test
    fun get_cart()= runBlocking {
        val result = remoteDataSource.getCart(1001).firstOrNull()
        println(result)
        assert(result!=null)
    }
    @Test
    fun get_cart_from_meta()= runBlocking {
        val result2 = reposiatory.getMetaFields(1001).firstOrNull()?.metafields?.get(0)?.value?.toLong()
        val result= reposiatory.getCart(result2!!).firstOrNull()
        println(result)
        assert(result!=null)
    }
    @Test
    fun get_currency_from_api()= runBlocking{
        val result = reposiatory.getCurrency("USD").firstOrNull()
        println(result)
        assert(result!=null)
    }
    @Test
    fun get_currency_from_local()= runBlocking{
        val result = reposiatory.getCurrencyFromLocal("USD").firstOrNull()
        println(result)
        assert(result!=null)
    }
    @Test
    fun change_currency()= runBlocking{
        val result = reposiatory.getCurrencyFromLocal("EGP").firstOrNull()
        println(result)
        assert(result?.first=="EGP")
    }
    @Test
    fun compelete_order()= runBlocking{
        val result = reposiatory.compeleteDraftOrder(reposiatory.getCart(1001).firstOrNull()!!).firstOrNull()
        println(result)
        assert(result==true)
    }
    //*************************


    /*****
     * Test case 2: ahmed   ahmed
     */



    //***********


    /**
     * Test case 3: omar
     */




    //*****************8
}
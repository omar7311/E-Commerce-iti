package com.example.e_commerce_iti.model.remotes.reposiatory

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.e_commerce_iti.model.local.IlocalDataSource
import com.example.e_commerce_iti.model.local.LocalDataSourceImp
import com.example.e_commerce_iti.model.pojos.AllProduct
import com.example.e_commerce_iti.model.pojos.Product
import com.example.e_commerce_iti.model.pojos.customer.Addresse
import com.example.e_commerce_iti.model.pojos.customer.Customer
import com.example.e_commerce_iti.model.pojos.customer.CustomerX
import com.example.e_commerce_iti.model.pojos.customer.DefaultAddress
import com.example.e_commerce_iti.model.remote.IRemoteDataSource
import com.example.e_commerce_iti.model.remotes.FakeLocalDataSource
import com.example.e_commerce_iti.model.remotes.FakeRemoteDataSource
import com.example.e_commerce_iti.model.remotes.dummydaya.dummyBrandData
import com.example.e_commerce_iti.model.remotes.dummydaya.dummyCustomCollections
import com.example.e_commerce_iti.model.remotes.dummydaya.order1
import com.example.e_commerce_iti.model.remotes.dummydaya.order2
import com.example.e_commerce_iti.model.remotes.dummydaya.order3
import com.example.e_commerce_iti.model.remotes.dummydaya.order4
import com.example.e_commerce_iti.model.remotes.dummydaya.product1
import com.example.e_commerce_iti.model.remotes.dummydaya.product2
import com.example.e_commerce_iti.model.remotes.dummydaya.product3
import com.example.e_commerce_iti.model.remotes.dummydaya.product4
import com.example.e_commerce_iti.model.remotes.dummydaya.product5
import com.example.e_commerce_iti.model.remotes.dummydaya.product6
import com.example.e_commerce_iti.model.remotes.dummydaya.product7
import com.example.e_commerce_iti.model.remotes.dummydaya.product8
import com.example.e_commerce_iti.model.remotes.dummydaya.product9
import com.example.e_commerce_iti.model.remotes.dummydaya.products
import com.example.e_commerce_iti.model.reposiatory.IReposiatory
import com.example.e_commerce_iti.model.reposiatory.ReposiatoryImpl
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.hamcrest.MatcherAssert
import org.hamcrest.core.IsEqual
import org.junit.Assert


import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class ReposiatoryImplTest {

    lateinit var      remoteDataSource : IRemoteDataSource
    lateinit var  localDataSource : IlocalDataSource
    lateinit var  reposiatory: ReposiatoryImpl


    @Before
    fun setUp() {
        remoteDataSource = FakeRemoteDataSource()
        val context: Context = ApplicationProvider.getApplicationContext()

        localDataSource = FakeLocalDataSource(
            context.getSharedPreferences(
                LocalDataSourceImp.currentCurrency,
                Context.MODE_PRIVATE
            )
        )
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
     * Test  ahmed   ahmed
     */

    @Test
    fun getOrders_passCustomerId_assertNumOfOrders() = runTest {

        val ordersFlow = reposiatory.getOrdersByCustomerId(1000001L)
        val result = ordersFlow.first()
        Assert.assertEquals(2, result.size)
    }


    @Test
    fun getOrders_passWrongCustomerId_asserEqualityList() = runTest {
        // when
        val listToTest = listOf(
            order1,
            order4
        )
        val ordersFlow = reposiatory.getOrdersByCustomerId(1000001L)
        val result = ordersFlow.first()
        // then
        Assert.assertEquals(listToTest, result)
        // when 2
        val listToTest2 = listOf(
            order2,
            order3
        )
        val ordersFlow2 = reposiatory.getOrdersByCustomerId(1000003L)
        val result2 = ordersFlow2.first()
        // then 2
        Assert.assertEquals(listToTest2, result2)
    }


    // test get products by vendor

    @Test
    fun getProductsByVendor_passVendorName_assertList() = runTest{
        // when
        val listToTest = listOf(
            product1,
            product2,
            product3,
            product4
        )
        val productsFlow = reposiatory.getProductsByVendor("Vendor1")
        val result = productsFlow.first()
        // then
        Assert.assertEquals(listToTest, result)
    }

    @Test
   fun  getProductsByVendor_passWrongVendorName_assertList2() = runTest{
       // when
        val listToTest2 = listOf(
            product5,
            product6,
            product7,
            product8,
            product9
        )
        val productsFlow2 = reposiatory.getProductsByVendor("Vendor2")
        val result2 = productsFlow2.first()
        // then
        Assert.assertEquals(listToTest2,result2)
    }

    @Test
    fun getProductsByVendor_passWrongVendorName_assertEmptyList() = runTest{
        //when
        val listToTest3 = emptyList<Product>()
        val productsFlow3 = reposiatory.getProductsByVendor("Vendor3")
        val result3 = productsFlow3.first()
        // then
        Assert.assertEquals(listToTest3,result3)
    }

    @Test
    fun getProductsByVendor_passNullVendorName_assertListSize() = runTest{
        // when
        val productsFlow4 = reposiatory.getProductsByVendor("Vendor1")
        val result4 = productsFlow4.first()
        // then
        Assert.assertEquals(4,result4.size)
    }

    // test  brands
    @Test
    fun getBrands_assertListSize() = runTest{
        // when
        val brandsFlow = reposiatory.getBrands()
        val result = brandsFlow.first()
        // then
        Assert.assertEquals(5,result.size)
    }

    @Test
    fun getBrands_assertListEquality() = runTest{
        // when
        val brandsFlow = reposiatory.getBrands()
        val result = brandsFlow.first()
        // then
        Assert.assertEquals(dummyBrandData,result)
    }

    // test customCollections

    @Test
    fun getCustomCollections_assertListSize() = runTest{
        // when
        val customCollectionsFlow = reposiatory.getCustomCollections()
        val result = customCollectionsFlow.first()
        // then
        Assert.assertEquals(dummyCustomCollections.size,result.size)
    }

    @Test
    fun getCustomCollections_assertListEquality() = runTest{

        // when
        val customStateFlow = reposiatory.getCustomCollections()
        val result = customStateFlow.first()
        // then
        Assert.assertEquals(dummyCustomCollections,result)
    }


    //***********


    /**
     * Test case 3: omar
     */
@Test
fun getAllProduct_assertEquality()= runTest {
        reposiatory.getAllProduct().collectLatest {
            Assert.assertEquals(it,AllProduct(products))
        }
    }
    @Test
    fun getAllProduct_assertNotEquality()= runTest {
        reposiatory.getAllProduct().collectLatest {
            Assert.assertNotEquals(it,AllProduct(listOf<Product>()))
        }
    }
@Test
fun getProductById_LongValue_assertEquality()= runTest{
    reposiatory.getProductByID(1).collectLatest {
        Assert.assertEquals(it, product1)

    }
}
    @Test
    fun getProductById_LongValue_assertNotEquality()= runTest{
        reposiatory.getProductByID(2).collectLatest {
            Assert.assertNotEquals(it, product1)
        }
    }
    //*****************8
}
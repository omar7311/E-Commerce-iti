package com.example.e_commerce_iti.ui.theme.orders

import android.content.Context
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.e_commerce_iti.model.pojos.Order
import com.example.e_commerce_iti.network.NetworkObserver
import com.example.e_commerce_iti.ui.theme.home.CustomButtonBar
import com.example.e_commerce_iti.ui.theme.home.CustomTopBar
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import coil.compose.rememberAsyncImagePainter
import com.example.e_commerce_iti.model.apistates.UiState
import com.example.e_commerce_iti.model.pojos.Address
import com.example.e_commerce_iti.model.pojos.Customer
import com.example.e_commerce_iti.model.pojos.LineItem
import com.example.e_commerce_iti.model.pojos.Product
import com.example.e_commerce_iti.ui.theme.viewmodels.orders.OrdersViewModel
import kotlinx.coroutines.flow.first


@Composable
fun OrderDetailsScreen(
    context: Context,
    order: Order,
    orderViewModel: OrdersViewModel,
    controller: NavHostController,
    networkObserver: NetworkObserver
) {

    Scaffold(
        topBar = { CustomTopBar("Orders", controller) },
        bottomBar = { CustomButtonBar(controller, context) },
    ) { innerPadding ->
        val isConnected = networkObserver.isConnected.collectAsState()
        if (isConnected.value) {


            //Log.i("Productsssss", "OrderDetailsScreen:$products")
            ScreenContent(
                order,
                orderViewModel,
                modifier = Modifier.padding(innerPadding)
            ) // ScreenContent
        } else {
            NetworkErrorContent() // when no connection

        }
    }
}


@Composable
fun ScreenContent(order: Order, orderViewModel: OrdersViewModel, modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp)
    ) {
        item {
            val products =
                FetchProductsDetails(orderViewModel, order)  // to fetch products from details
            OrderItemsRow(products, orderViewModel)
        }
        item { OrderHeader(order) }

        item { Spacer(modifier = Modifier.height(16.dp)) }
        item { OrderSummary(order) }
        item { Spacer(modifier = Modifier.height(16.dp)) }
        item { CustomerInfo(order.customer, order.shippingAddress) }
        item { Spacer(modifier = Modifier.height(16.dp)) }
        // item { OrderStatus(order) }
        item { Spacer(modifier = Modifier.height(16.dp)) }
        item { LineItemsList(order.lineItems) }
    }
}

@Composable
fun OrderHeader(order: Order) {
    Column {
        Text(
            text = "Order Details",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            InfoItem("Order Num ", "#${order.orderNumber.toString()}")
        }
        InfoItem("Date", order.createdAt.substringBefore('T'))
        //InfoItem("Name", order.name)
    }
}


@Composable
fun OrderSummary(order: Order) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Order Summary", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Spacer(modifier = Modifier.height(8.dp))
            InfoItem("Subtotal", "${order.currentSubtotalPrice} ${order.currency}")
            /*InfoItem(
                "Shipping",
                "${order.totalShippingPriceSet.shopMoney.amount} ${order.currency}"
            )*/
            InfoItem("Tax", "${order.currentTotalTax} ${order.currency}")
            InfoItem("Discounts", "-${order.totalDiscounts} ${order.currency}")
            Divider(modifier = Modifier.padding(vertical = 8.dp))
            InfoItem("Total", "${order.currentTotalPrice} ${order.currency}", FontWeight.Bold)
        }
    }
}

@Composable
fun CustomerInfo(customer: Customer, address: Address) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            /*   Text("Customer Information", fontWeight = FontWeight.Bold, fontSize = 18.sp)
               Spacer(modifier = Modifier.height(8.dp))
               Text("${customer.firstName} ${customer.lastName}")
               Text(customer.email)
               Text(customer.phone ?: "No phone provided")*/
            Spacer(modifier = Modifier.height(8.dp))
            Text("Shipping Address", fontWeight = FontWeight.Bold)
            Text(address.address1 ?: "N/A", fontWeight = FontWeight.Bold)
            Text(
                "${address.city}, ${address.country} ${address.zip ?: ""}",
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun OrderStatus(order: Order) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Order Status", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Spacer(modifier = Modifier.height(8.dp))
            InfoItem("Financial Status", order.financialStatus.capitalize())
            InfoItem("Fulfillment Status", order.fulfillmentStatus?.capitalize() ?: "Not available")
            InfoItem("Order Status", order.orderStatus?.capitalize() ?: "Not available")
        }
    }
}

@Composable
fun LineItemsList(lineItems: List<LineItem>) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Order Items", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Spacer(modifier = Modifier.height(8.dp))
            lineItems.forEach { item ->
                LineItemRow(item)
                Divider(modifier = Modifier.padding(vertical = 8.dp))
            }
        }
    }
}

@Composable
fun LineItemRow(item: LineItem) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(item.name, fontWeight = FontWeight.Bold)
            Text("Quantity: ${item.quantity}", fontSize = 14.sp, fontWeight = FontWeight.Bold)
            if (item.sku != null) Text(
                "SKU: ${item.sku}",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
            if (item.vendor!!.isNotBlank()) Text(
                "Vendor: ${item.vendor}",
                fontSize = 1.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Column(horizontalAlignment = Alignment.End) {
            Text(
                text = "${item.price}",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.End)
            )
            if (item.totalDiscount.toFloat() > 0) {
                Text(
                    text = "Discount: -${item.totalDiscount}",
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 14.sp
                )
            }
        }
    }
}

@Composable
fun InfoItem(label: String, value: String, fontWeight: FontWeight = FontWeight.Bold) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, fontWeight = fontWeight)
        Text(value, fontWeight = fontWeight)
    }
}


/**
 *      this for orders items images and titels
 */
@Composable
fun FetchProductsDetails(orderViewModel: OrdersViewModel, order: Order): List<Product> {
    val productsIds = getLineItemsProductsIds(order)  // get line item products frist
    Log.i("ProductIds", "FetchProductsDetails: $productsIds")
    val ActualProducts = getActualProductsFromApi(
        productsIds.toMutableList(),
        orderViewModel
    ) // then get Actuall products
    return ActualProducts
}

@Composable
fun OrderItemsRow(
    products: List<Product>,
    orderViewModel: OrdersViewModel
) {
    val configuration = LocalConfiguration.current
    /*   val screenWidth = configuration.screenWidthDp.dp
       val ActualProducts = getActualProductsFromApi(products.toMutableList(),orderViewModel)*/
    LazyRow(
        modifier = Modifier
            .padding(7.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(products) { actualProduct ->
            OrderItems(actualProduct)
        }
    }
}

@Composable
fun OrderItems(
    product: Product,
) {

    Column(
        modifier = Modifier
            .padding(4.dp)
    ) {
        Image(
            painter = rememberAsyncImagePainter(product.images[0].src),
            contentDescription = null,
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surface),
            contentScale = ContentScale.FillBounds
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = product.title,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onSurface),
            textAlign = TextAlign.Center
        )
    }
}

fun getLineItemsProductsIds(order: Order): List<Long> {
    return order.lineItems.mapNotNull { item ->
        item.productId.takeIf { it != 0L }?.also { productId ->
            Log.d("ProductID", "Added product ID: $productId")
        } ?: run {
            Log.e("ProductID", "Product ID is null or 0 for item: $item")
            null
        }
    }.also { productIds ->
        if (productIds.isEmpty()) {
            Log.e("LineItems", "No valid product IDs found in line items")
        } else {
            Log.d("LineItems", "Found ${productIds.size} valid product IDs")
        }
    }
}

/*fun getActualProductsFromApi(productIds:MutableList<Long>,orderViewModel:OrdersViewModel):List<Product>{
    val ActualProducts :MutableList<Product> = mutableListOf()
    for (i in 0 until productIds.size){
        orderViewModel.getProductById(productIds[i])
        val state = orderViewModel.singleProductFlow.value
        if (state is UiState.Success){
            ActualProducts.add(state.data)
        }
    }
    return ActualProducts
}*/
@Composable
fun getActualProductsFromApi(
    productIds: List<Long>,
    orderViewModel: OrdersViewModel
): List<Product> {
    val actualProducts = remember { mutableStateListOf<Product>() }

    // Collect product flow using LaunchedEffect
    LaunchedEffect(productIds) {
        productIds.forEach { productId ->
            if (productId > 0) {
                // Fetch product details from the ViewModel
                orderViewModel.getProductById(productId)
            } else {
                Log.e("Invalid Product ID", "Product ID is invalid: $productId")
            }
        }
    }

    // Observe the product state
    val productState by orderViewModel.singleProductFlow.collectAsState(initial = UiState.Loading)

    // Handle the product result
    when (productState) {
        is UiState.Success -> {
            val product = (productState as UiState.Success<Product>).data
            if (!actualProducts.contains(product)) {
                actualProducts.add(product)
            }
        }

        is UiState.Error -> {
            Log.e("Product Error", (productState as UiState.Error).message)
        }

        UiState.Loading -> {
            // Loading state, handle it if needed
        }

        is UiState.Failure -> {}
        UiState.Non -> {}
    }

    return actualProducts.toList() // Return the current list of actual products
}


/**
 *      this for product images and titles
 */
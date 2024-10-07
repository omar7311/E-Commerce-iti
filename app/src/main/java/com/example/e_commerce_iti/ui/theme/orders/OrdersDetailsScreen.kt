package com.example.e_commerce_iti.ui.theme.orders

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
import androidx.compose.foundation.layout.width
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
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import androidx.lifecycle.viewModelScope
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
    order: Order,
    orderViewModel: OrdersViewModel,
    controller: NavHostController,
    networkObserver: NetworkObserver
) {

    Scaffold(
        topBar = { CustomTopBar("Orders", controller) },
        bottomBar = { CustomButtonBar(controller) },
    ) { innerPadding ->
        val isConnected = networkObserver.isConnected.collectAsState()
        if (isConnected.value) {
            Log.i("Orddddrr", "OrderDetailsScreen:$order")
            ScreenContent(order,orderViewModel, modifier = Modifier.padding(innerPadding)) // ScreenContent
        }
    }
}


@Composable
fun ScreenContent(order: Order, orderViewModel: OrdersViewModel, modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp)
    ) {
        item { OrderHeader(order) }
        item { Spacer(modifier = Modifier.height(16.dp)) }
        item { OrderSummary(order) }
        item { Spacer(modifier = Modifier.height(16.dp)) }
        item { CustomerInfo(order.customer, order.shippingAddress) }
        item { Spacer(modifier = Modifier.height(16.dp)) }
        item { OrderStatus(order) }
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
            InfoItem("Order #", order.orderNumber.toString())
            InfoItem("Date", order.createdAt.substringBefore('T'))
        }
        InfoItem("Name", order.name)
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
            InfoItem("Shipping", "${order.totalShippingPriceSet.shopMoney.amount} ${order.currency}")
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
            Text("Customer Information", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text("${customer.firstName} ${customer.lastName}")
            Text(customer.email)
            Text(customer.phone ?: "No phone provided")
            Spacer(modifier = Modifier.height(8.dp))
            Text("Shipping Address", fontWeight = FontWeight.Bold)
            Text(address.address1 ?: "N/A")
            Text("${address.city}, ${address.country} ${address.zip ?: ""}")
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
            Text("Quantity: ${item.quantity}", fontSize = 14.sp)
            if (item.sku != null) Text("SKU: ${item.sku}", fontSize = 14.sp)
            if (item.vendor!!.isNotBlank()) Text("Vendor: ${item.vendor}", fontSize = 14.sp)
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
fun InfoItem(label: String, value: String, fontWeight: FontWeight = FontWeight.Normal) {
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
fun FetchProductsDetails(orderViewModel: OrdersViewModel,order:Order){
    val productsIds = getLineItemsProductsIds(order)  // get line item products frist
    Log.i("ProductIds", "FetchProductsDetails: $productsIds")
    val ActualProducts = getActualProductsFromApi(productsIds.toMutableList(),orderViewModel) // then get Actuall products
    OrderItemsRow(ActualProducts,orderViewModel)

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
   product:Product,
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
    val products = mutableListOf<Long>()
    order.lineItems?.let { lineItems ->
        Log.d("LineItems", "Processing line items: $lineItems")
        for (item in lineItems) {
            item.productId?.let {
                products.add(it)
                Log.d("ProductID", "Added product ID: $it")
            } ?: Log.e("ProductID", "Product ID is null for item: $item")
        }
    } ?: Log.e("LineItems", "Line items are null or empty")
    return products
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
fun getActualProductsFromApi(productIds: List<Long>, orderViewModel: OrdersViewModel): List<Product> {
    val actualProducts = remember { mutableStateListOf<Product>() } // State-backed list for recomposition
    val scope = rememberCoroutineScope() // Use a coroutine scope for launching coroutines

    LaunchedEffect(productIds) { // Re-run when productIds changes
        for (productId in productIds) {
            // Check if the productId is not null and greater than 0
            if (productId > 0) {
                // Fetch product details
                orderViewModel.getProductById(productId)

                // Collect the state safely
                orderViewModel.singleProductFlow.collect { state ->
                    when (state) {
                        is UiState.Success -> actualProducts.add(state.data)
                        is UiState.Error -> Log.e("Product Error", state.message) // Handle errors if necessary
                        else -> {} // Handle loading state if needed
                    }
                }
            } else {
                Log.e("Invalid Product ID", "Product ID is null or zero: $productId")
            }
        }
    }

    return actualProducts.toList() // Return a snapshot of the actual products
}


/**
 *      this for product images and titles
 */
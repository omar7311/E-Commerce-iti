package com.example.e_commerce_iti.ui.theme.orders

import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.e_commerce_iti.model.apistates.UiState
import com.example.e_commerce_iti.model.pojos.Order
import com.example.e_commerce_iti.network.NetworkObserver
import com.example.e_commerce_iti.ui.theme._navigation.Screens
import com.example.e_commerce_iti.ui.theme.home.CustomButtonBar
import com.example.e_commerce_iti.ui.theme.home.CustomTopBar
import com.example.e_commerce_iti.ui.theme.home.MyLottieAnimation
import com.example.e_commerce_iti.ui.theme.viewmodels.orders.OrdersViewModel
import com.google.gson.Gson

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrdersScreen(
    orderViewModel: OrdersViewModel,
    controller: NavController,
    networkObserver: NetworkObserver
) {
    Scaffold(
        topBar = { CustomTopBar("Orders", controller) },
        bottomBar = { CustomButtonBar(controller) }
    ) { innerPadding ->
        val isConnected by networkObserver.isConnected.collectAsState()

        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            if (isConnected) {
                OrdersContent(orderViewModel, controller)
            } else {
                NetworkErrorContent()
            }
        }
    }
}

@Composable
fun OrdersContent(orderViewModel: OrdersViewModel, controller: NavController) {
    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "Orders History",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.CenterHorizontally)
        )
        FetchOrdersByCustomerId(7491636658353, orderViewModel, controller)
    }
}

@Composable
fun FetchOrdersByCustomerId(
    customerId: Long,
    orderViewModel: OrdersViewModel,
    controller: NavController
) {
    LaunchedEffect(Unit) {
        orderViewModel.getOrdersByCustomerId(customerId)
    }

    val ordersState by orderViewModel.ordersFlowState.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        when (ordersState) {
            is UiState.Success -> {
                val orders = (ordersState as UiState.Success<List<Order>>).data
                OrdersList(orders, controller)
            }

            is UiState.Loading -> LoadingIndicator()
            is UiState.Failure -> ErrorContent((ordersState as UiState.Failure).exception)
            else -> {} // Handle other states if needed
        }
    }
}

@Composable
fun OrdersList(orders: List<Order>, controller: NavController) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(1),
        contentPadding = PaddingValues(16.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(orders) { order ->
            OrderItem(order, controller)
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun LoadingIndicator() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun ErrorContent(exception: Throwable) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "An error occurred",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.error
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = exception.localizedMessage ?: "Unknown error",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
        )
    }
}

@Composable
fun NetworkErrorContent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "No Internet Connection",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.error
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Please check your network settings and try again.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))
        MyLottieAnimation() // Assuming this is your custom animation for network error
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderItem(order: Order, controller: NavController) {
    val gson = Gson()
    val orderJson = gson.toJson(order)

    ElevatedCard(

        shape = RoundedCornerShape(25.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 8.dp)
            .animateContentSize()
            .clickable { controller.navigate(Screens.OrderDetails.createRoute(orderJson)) },
                elevation = CardDefaults.elevatedCardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.elevatedCardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OrderImage(url = "https://www.shutterstock.com/image-photo/calm-weather-on-sea-ocean-260nw-2212935531.jpg")
            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OrderInfoRow("Order ID", order.id.toString())
                OrderInfoRow("Date", order.createdAt.substringBefore('T'))  // for date  built in
                OrderInfoRow("Total", "${order.currentTotalPrice} ${order.currency}")
                OrderStatusChip(order.orderStatus ?: "Order Status UnDefined Yet")
            }
        }
    }
}

@Composable
fun OrderImage(url: String) {
    val painter = rememberAsyncImagePainter(model = url)
    Image(
        painter = painter,
        contentDescription = "Order Image",
        modifier = Modifier
            .size(80.dp)
           // .clip(CircleShape),
                ,
        contentScale = ContentScale.Fit
    )
}

@Composable
fun OrderInfoRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
        )
        Text(
            text = value,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
fun OrderStatusChip(status: String) {
    val (backgroundColor, contentColor) = when (status.lowercase()) {
        "completed" -> MaterialTheme.colorScheme.primary to MaterialTheme.colorScheme.onPrimary
        "processing" -> MaterialTheme.colorScheme.secondary to MaterialTheme.colorScheme.onSecondary
        "cancelled" -> MaterialTheme.colorScheme.error to MaterialTheme.colorScheme.onError
        else -> MaterialTheme.colorScheme.surfaceVariant to MaterialTheme.colorScheme.onSurfaceVariant
    }

    Surface(
        shape = RoundedCornerShape(16.dp),
        color = backgroundColor,
        contentColor = contentColor,
        modifier = Modifier.padding(top = 4.dp)
    ) {
        Text(
            text = status,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            style = MaterialTheme.typography.labelSmall
        )
    }
}

/*
    @Composable
    fun OrderImage(url: String) {
        val painter = rememberAsyncImagePainter(model = url)
        Image(
            painter = painter,
            contentDescription = null,
            // contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(80.dp) // Adjust size as needed
                .clip(CircleShape) // Use CircleShape for fully rounded image
                .padding(10.dp) // Padding inside the image
        )
    }
*/


@Composable
fun OrderText(
    textToUse: String,
    fontSize: TextUnit = 14.sp, // Default font size
    textColor: Color = Color.Black, // Default text color
    fontWeight: FontWeight? = null, // Optional font weight
    modifier: Modifier = Modifier, // Optional modifier
) {
    Text(
        text = textToUse,
        fontSize = fontSize,
        color = textColor,
        fontWeight = fontWeight,
        modifier = modifier
    )
}


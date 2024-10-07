package com.example.e_commerce_iti.ui.theme.orders

import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.content.MediaType.Companion.Image
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.e_commerce_iti.model.apistates.UiState
import com.example.e_commerce_iti.model.pojos.LineItem
import com.example.e_commerce_iti.model.pojos.Order
import com.example.e_commerce_iti.model.pojos.Product
import com.example.e_commerce_iti.network.NetworkObserver
import com.example.e_commerce_iti.ui.theme._navigation.Screens
import com.example.e_commerce_iti.ui.theme.home.CustomButtonBar
import com.example.e_commerce_iti.ui.theme.home.CustomImage
import com.example.e_commerce_iti.ui.theme.home.CustomText
import com.example.e_commerce_iti.ui.theme.home.CustomTopBar
import com.example.e_commerce_iti.ui.theme.home.HomeContent
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
        bottomBar = { CustomButtonBar(controller) },
    ) { innerPadding ->

        val isConnected = networkObserver.isConnected.collectAsState()
        if (isConnected.value) {
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            ) {
                OrderText(
                    "Orders History",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(10.dp)
                        .align(Alignment.CenterHorizontally)
                )
                FetchOrdersByCustomerId(7491636658353, orderViewModel, controller)

            }
        } else {
            MyLottieAnimation()
        }

    }
}


@Composable
fun FetchOrdersByCustomerId(
    customerId: Long,
    orderViewModel: OrdersViewModel,
    controller: NavController
) {
    // Trigger the order fetching when the composable is first launched
    LaunchedEffect(Unit) {
        orderViewModel.getOrdersByCustomerId(customerId) // Use the passed customerId
    }
    val ordersFlow = orderViewModel.ordersFlowState.collectAsState()
    when (val state = ordersFlow.value) { // Access the value here
        is UiState.Success -> {
            val orders = state.data
            OrdersList(orders, controller) // pass the orders here to show its data
            Log.i("Orddddrr", "OrderDetailsScreen:$orders")

        }

        is UiState.Loading -> {
        }

        is UiState.Failure -> {
            val exception = state.exception
        }

        else -> {
        }
    }
}

@Composable
fun OrdersList(orders: List<Order>, controller: NavController) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(1), // Adjust the minimum size as needed
        contentPadding = PaddingValues(8.dp),
        modifier = Modifier.fillMaxSize() // Ensure grid takes full available space
    ) {
        items(orders) { order ->
            OrderItem(order, controller)
            Spacer(modifier = Modifier.height(15.dp))
        }
    }
}

@Composable
fun OrderItem(order: Order, controller: NavController) {
    val gson = Gson()
    val orderJson = gson.toJson(order)
    Card(
        shape = RoundedCornerShape(30.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
        modifier = Modifier
            .clickable { controller.navigate(Screens.OrderDetails.createRoute(orderJson)) }
            .padding(2.dp)
            .fillMaxWidth()
            .animateContentSize(),
        border = BorderStroke(2.dp, Color.Black),

        colors = CardDefaults.cardColors(containerColor = Color.White),
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
                .wrapContentHeight(),

            verticalAlignment = Alignment.CenterVertically // Align items vertically in the center
        ) {
            // Display Order Image on the left
            OrderImage(
                url = "https://www.shutterstock.com/image-photo/calm-weather-on-sea-ocean-260nw-2212935531.jpg",
            )
            Spacer(modifier = Modifier.width(3.dp)) // Space between image and text

            // Column to hold text information
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp), // Add padding for better layout
                verticalArrangement = Arrangement.spacedBy(2.dp) // Reduce space between rows
            ) {
                // Row for Order ID and Status
                Row(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    OrderText(
                        textToUse = "Order ID:",
                        textColor = Color.Black, // Label in black
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        modifier = Modifier.weight(1f)

                    )
                    OrderText(
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 20.dp),
                        textToUse = order.id.toString(), // Value in different color
                        textColor = Color.Blue, // Change this to your preferred color
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                }

                // Row for Date and Status
                Row(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    OrderText(
                        modifier = Modifier
                            .weight(1f),
                        textToUse = "Date      :",
                        textColor = Color.Black, // Label in black
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                    OrderText(
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 20.dp),
                        textToUse = "${order.createdAt}", // Value in different color
                        textColor = Color.Blue, // Change this to your preferred color
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                    )
                }

                // Row for Total Cost
                Row(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    OrderText(
                        modifier = Modifier
                            .weight(1f),
                        textToUse = "Total     :",
                        textColor = Color.Black, // Label in black
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                    OrderText(
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 20.dp),
                        textToUse = "${order.currentTotalPrice} ${order.currency}", // Value in different color
                        textColor = Color.Blue, // Change this to your preferred color
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                }

                // Status row
                Row(modifier = Modifier.fillMaxWidth()) {
                    // Status row
                    OrderText(
                        modifier = Modifier
                            .weight(1f),
                        textToUse = "Status  :",
                        textColor = Color.Black, // Label in black
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                    OrderText(
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 20.dp),
                        textToUse = "${order.orderStatus}",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        textColor = Color.Blue, // Change this to your preferred color


                    )
                }

            }
        }
    }
}

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

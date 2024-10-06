package com.example.e_commerce_iti.ui.theme.product_details

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import kotlinx.coroutines.launch

@Composable
fun ImageCarousel(images: List<String>) {
    val pagerState = rememberPagerState{
        images.size
    }
    val coroutineScope = rememberCoroutineScope()

    Box(modifier = Modifier.fillMaxWidth().height(300.dp)) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            Image(
                painter = rememberImagePainter(images[page]),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }

        // Add page indicators (optional)
        Row(
            Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(images.size) { index ->
                val isSelected = pagerState.currentPage == index
                Box(
                    modifier = Modifier
                        .padding(4.dp)
                        .size(if (isSelected) 12.dp else 8.dp)
                        .background(if (isSelected) Color.White else Color.Gray, shape = CircleShape)
                )
            }
        }
    }
}
@Preview(showSystemUi = true)
@Composable
fun CarouselScreen() {
    val images = listOf(
        "https://via.placeholder.com/600/92c952",
        "https://via.placeholder.com/600/771796",
        "https://via.placeholder.com/600/24f355"
    )

    Column(modifier = Modifier.fillMaxSize()) {
        ImageCarousel(images)
    }
}
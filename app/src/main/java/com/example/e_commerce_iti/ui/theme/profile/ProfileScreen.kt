package com.example.e_commerce_iti.ui.theme.profile

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.e_commerce_iti.R
import com.example.e_commerce_iti.currentUser
import com.example.e_commerce_iti.ui.theme.ECommerceITITheme
import com.example.e_commerce_iti.ui.theme._navigation.Screens
import com.example.e_commerce_iti.ui.theme.home.CustomButtonBar
import com.example.e_commerce_iti.ui.theme.home.CustomTopBar
import com.skydoves.landscapist.glide.GlideImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(controller: NavController) {

    Scaffold(
        topBar = { CustomTopBar("Profile", controller) },  // Update title to "Cart"
        bottomBar = { CustomButtonBar(controller,context = LocalContext.current) },     // Keep the navigation controller for buttons
    ) { innerPadding ->                                // Use padding for the content
       Column(modifier = Modifier
           .fillMaxSize().verticalScroll(rememberScrollState()).
           padding(innerPadding),Arrangement.SpaceEvenly,) {
           Row( modifier = Modifier.fillMaxWidth().wrapContentHeight().padding(20.dp),Arrangement.Center) {
           GlideImage(imageModel = R.drawable.avatar ,Modifier.size(100.dp))
           }
           Row( modifier = Modifier.fillMaxWidth().wrapContentHeight().padding(20.dp),Arrangement.Center) {
               Text(text = "Name : ${currentUser?.name?:"N/A"}", fontSize = 17.sp, fontWeight = FontWeight.Bold)
           }
           Row( modifier = Modifier.fillMaxWidth().wrapContentHeight().padding(20.dp),Arrangement.Center) {
               Text(text = "Email : ${currentUser?.email?:"N/A"}", fontSize = 17.sp, fontWeight = FontWeight.Bold)
           }
           ProfieItem(e = {controller.navigate(Screens.Orders.route)}, t = "Orders", id = R.drawable.ordersicon)
           ProfieItem(e = {controller.navigate(Screens.Favorite.route)}, t = "Favorites", id = R.drawable.baseline_favorite_border_24)
           ProfieItem(e = {controller.navigate(Screens.Setting.route)}, t = "Settings", id = R.drawable.accessory)


       }
    }
    }


@Preview(showBackground = true
    , showSystemUi = true)
@Composable
fun SettingScreenPreview() {
    ECommerceITITheme {
        val context = LocalContext.current
            ProfileScreen(NavController(context))
    }
}
@Composable
fun ProfieItem(e:()->Unit,t:String,id:Int,){
    Row( modifier = Modifier.fillMaxWidth().wrapContentHeight().padding(20.dp),Arrangement.Center) {

        Card(elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
            shape = RoundedCornerShape(30.dp),
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(30.dp)).clickable { e() }
                .animateContentSize(),
            border = BorderStroke(2.dp, Color.Black),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().height(60.dp)
                    .padding(start = 10.dp, end = 10.dp, bottom = 5.dp, top = 9.dp)
            ) {
                Image(
                    painter = painterResource(id = id),
                    contentDescription = "",
                    modifier = Modifier.fillMaxHeight().weight(2F).padding(start = 10.dp)
                )

                Box(
                    modifier = Modifier.fillMaxSize().weight(8F),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = t,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Image(
                    painter = painterResource(id = R.drawable.baseline_arrow_forward_ios_24),
                    contentDescription = "",
                    modifier = Modifier.fillMaxSize().weight(2F).padding(start = 10.dp)
                )
            }

        }
        }

}




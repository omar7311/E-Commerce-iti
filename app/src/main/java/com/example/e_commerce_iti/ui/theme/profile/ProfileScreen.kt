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
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.e_commerce_iti.CurrentUserSaver
import com.example.e_commerce_iti.R
import com.example.e_commerce_iti.currentUser
import com.example.e_commerce_iti.darkSlateGray
import com.example.e_commerce_iti.lavender
import com.example.e_commerce_iti.lightSeaGreen
import com.example.e_commerce_iti.model.remote.RemoteDataSourceImp
import com.example.e_commerce_iti.model.reposiatory.ReposiatoryImpl
import com.example.e_commerce_iti.ui.theme.ECommerceITITheme
import com.example.e_commerce_iti.ui.theme._navigation.Screens
import com.example.e_commerce_iti.ui.theme.guest.GuestScreen
import com.example.e_commerce_iti.ui.theme.home.CustomButtonBar
import com.example.e_commerce_iti.ui.theme.home.CustomTopBar
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.skydoves.landscapist.glide.GlideImage
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(controller: NavController) {

    Scaffold(
        topBar = { CustomTopBar("Profile", controller) },  // Update title to "Cart"
        bottomBar = {
            CustomButtonBar(
                controller,
                context = LocalContext.current
            )
        },     // Keep the navigation controller for buttons
    ) { innerPadding ->

        if (Firebase.auth.currentUser != null && !Firebase.auth.currentUser!!.email.isNullOrBlank()) {  // when guest
            val e= currentUser.observeAsState().value
            if (e!= null){
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(innerPadding),
                Arrangement.SpaceAround,
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(20.dp),
                    Arrangement.Center
                ) {
                    GlideImage(imageModel = R.drawable.avatar, Modifier.size(100.dp))
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(top = 2.dp, start = 20.dp, end = 20.dp), Arrangement.Center
                ) {
                    Text(
                        text = "Name : ${currentUser.value?.name ?: "N/A"}",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                // for withd of emmail size
                val configuration = LocalConfiguration.current
                val screenWidth = configuration.screenWidthDp.dp
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(
                            top = if (screenWidth < 600.dp) 2.dp else 10.dp, // More padding for larger screens
                            start = if (screenWidth < 600.dp) 10.dp else 20.dp, // Adjust start padding
                            end = if (screenWidth < 600.dp) 10.dp else 20.dp  // Adjust end padding
                        ),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Email : ${currentUser.value?.email ?: "N/A"}",
                        fontSize = if (screenWidth < 600.dp) 14.sp else 17.sp, // Smaller font size on small screens
                        fontWeight = FontWeight.Bold
                    )
                }
                ProfieItem(
                    e = { controller.navigate(Screens.Orders.route) },
                    t = "Orders",
                    id = R.drawable.ordersicon
                )
                ProfieItem(
                    e = { controller.navigate(Screens.Favorite.route) },
                    t = "Favorites",
                    id = R.drawable.baseline_favorite_border_24
                )
                ProfieItem(
                    e = { controller.navigate(Screens.Setting.route) },
                    t = "Settings",
                    id = R.drawable.settings
                )
                // Logout Button
                LogoutButton(controller)

            }
        }
            else{
            Box(Modifier.fillMaxSize().padding(innerPadding), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = lightSeaGreen)
            }
        }
        }

        else{
            GuestScreen(controller)
        }
    }
}

@Composable
fun LogoutButton(controller: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize(), // Fill the entire screen
        contentAlignment = Alignment.Center // Center the content (button)
    ) {
        Button(
            onClick = {
                controller.navigate(Screens.Login.route){
                    popUpTo(Screens.Profile.route) { inclusive = true }
                }

                Firebase.auth.signOut()
                currentUser.value = null
            },
            modifier = Modifier
                .wrapContentWidth()
                .padding(vertical = 12.dp), // Add vertical padding if needed
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.error)
        ) {
            Text(
                text = "Logout",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}


@Preview(
    showBackground = true, showSystemUi = true
)
@Composable
fun SettingScreenPreview() {
    ECommerceITITheme {
        val context = LocalContext.current
        ProfileScreen(NavController(context))
    }
}

@Composable
fun ProfieItem(e: () -> Unit, t: String, id: Int) {
    Row(
        modifier = Modifier
            .wrapContentWidth()
            .wrapContentHeight()
            .padding(20.dp),
        Arrangement.Center
    ) {

        Card(
            elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
            shape = RoundedCornerShape(30.dp),
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(30.dp))
                .clickable { e() }
                .animateContentSize(),
            border = BorderStroke(2.dp, Color.Black),
            colors = CardDefaults.cardColors(
                containerColor = lavender // Set background color
            )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .padding(start = 10.dp, end = 10.dp, bottom = 5.dp, top = 9.dp)
            ) {
                Image(
                    painter = painterResource(id = id),
                    contentDescription = "",
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(2F)
                        .padding(start = 10.dp)
                )

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(8F),
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
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(2F)
                        .padding(start = 10.dp)
                )
            }

        }
    }

}




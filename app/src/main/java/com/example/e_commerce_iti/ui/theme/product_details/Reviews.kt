package com.example.e_commerce_iti.ui.theme.product_details

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.e_commerce_iti.R
import com.example.e_commerce_iti.ui.theme.favorite.FavouriteItem
import kotlin.random.Random

@Composable
fun Reviews(){
    val emails= listOf("Omar.gohary123@gmail.com","ahmedsoftware404@gmail.com",
        "sammaralaa95@gmail.com","amgedtamer12345@gmail.com","mahmoudsaaddarwish1@gmail.com",
        "mostafa.gamal123456789@outlook.sa","ga71387@gmail.com","noureldin0@gmail.com")
         val randomList=emails.shuffled()
          for(i in randomList)
            ReviewItem(i)
}
@Composable
fun ReviewItem(email:String){
    Row(modifier = Modifier.fillMaxWidth().height(75.dp).padding(8.dp), horizontalArrangement = Arrangement.Start){
        Image(painter = painterResource(R.drawable.avatar), contentDescription = null,
            modifier = Modifier.size(60.dp).padding(8.dp).clip(CircleShape), contentScale = ContentScale.Crop)
        Column() {
            Spacer(Modifier.height(8.dp))
            Text(text = email)
            Spacer(Modifier.height(4.dp))
            RatingBar(rating = Random.nextInt(1,6), starSize = 22)
        }
    }
}
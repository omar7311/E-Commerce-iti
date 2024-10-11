package com.example.e_commerce_iti.model.remotes.dummydaya

import com.example.e_commerce_iti.model.pojos.BrandData

val brand1 = BrandData(
    id = 101L,
    title = "Eco Essentials",
    imageSrc = "https://fakeshopify.com/images/eco-essentials-logo.jpg",
    imageWidth = 300,
    imageHeight = 300
)

val brand2 =  BrandData(
    id = 102L,
    title = "GreenSmile",
    imageSrc = "https://fakeshopify.com/images/greensmile-logo.jpg",
    imageWidth = 250,
    imageHeight = 250
)

val brand3 =  BrandData(
    id = 103L,
    title = "NatureGoods",
    imageSrc = "https://fakeshopify.com/images/naturegoods-logo.jpg",
    imageWidth = 350,
    imageHeight = 350
)

val brand4 = BrandData(
    id = 104L,
    title = "Sustainify",
    imageSrc = "https://fakeshopify.com/images/sustainify-logo.jpg",
    imageWidth = 400,
    imageHeight = 400
)

val brand5 = BrandData(
    id = 105L,
    title = "Pure Planet",
    imageSrc = "https://fakeshopify.com/images/pure-planet-logo.jpg",
    imageWidth = 320,
    imageHeight = 320
)

// list of brands
val dummyBrandData = listOf(
    brand1,
   brand2,
    brand3,
    brand4,
   brand5

)

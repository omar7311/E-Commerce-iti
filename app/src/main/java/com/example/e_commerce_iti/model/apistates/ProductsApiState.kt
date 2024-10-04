package com.example.e_commerce_iti.model.apistates

import com.example.e_commerce_iti.model.pojos.Product

sealed class ProductsApiState (){

    class Success(val products: List<Product>) : ProductsApiState()
    object Loading : ProductsApiState()
    class Failure(val msg: Throwable) : ProductsApiState()
}
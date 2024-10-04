package com.example.e_commerce_iti.model.apistates

import com.example.e_commerce_iti.model.pojos.CustomCollection

sealed class CustomCollectionStates {

    class Success(val customCollections: List<CustomCollection>) : CustomCollectionStates()
    class Failure(val msg: Throwable) : CustomCollectionStates()
    object Loading : CustomCollectionStates()
}
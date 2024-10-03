package com.example.e_commerce_iti.model.apistates

import com.example.e_commerce_iti.model.pojos.BrandData

sealed class BrandsApiState {

    class Success (val brands :List<BrandData>) : BrandsApiState()
    object Loading  : BrandsApiState()
    class Failure (val msg :Throwable) : BrandsApiState()
}
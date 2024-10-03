package com.example.e_commerce_iti.ui.theme.viewmodels.coupn_viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.e_commerce_iti.R
import com.example.e_commerce_iti.model.pojos.Coupons

class CouponViewModel : ViewModel() {
    private val _couponImages = MutableLiveData<List<Coupons>>()
    val couponImages: LiveData<List<Coupons>> get() = _couponImages

    init {
        // Load images from a repository or generate them
        loadCouponImages()
    }

    private fun loadCouponImages() {
        // You can either fetch images from an API or locally
        val images = listOf(
            Coupons(R.drawable.coupon1, "50% off"),
            Coupons(R.drawable.coupon2, "30% off"),
            Coupons(R.drawable.coupon3, "30% off"),
        )
        _couponImages.value = images
    }
}

package com.example.e_commerce_iti.ui.theme.viewmodels.coupn_viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.e_commerce_iti.R
import com.example.e_commerce_iti.model.apistates.UiState
import com.example.e_commerce_iti.model.pojos.Coupons
import com.example.e_commerce_iti.model.pojos.discountcode.DiscountCode
import com.example.e_commerce_iti.model.pojos.price_rules.PriceRules
import com.example.e_commerce_iti.model.remote.IRemoteDataSource
import com.example.e_commerce_iti.model.reposiatory.IReposiatory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class CouponViewModel(private val remoteDataSource: IReposiatory) : ViewModel() {
    private val _couponImages = MutableStateFlow<List<Coupons>>(emptyList())
    val couponImages: StateFlow<List<Coupons>> = _couponImages

    private val _couponsStateflow = MutableStateFlow<UiState<List<DiscountCode>>>(UiState.Loading)
    val couponsStateflow: StateFlow<UiState<List<DiscountCode>>> = _couponsStateflow

    private val _priceRulesStateflow = MutableStateFlow<UiState<PriceRules>>(UiState.Loading)
    val priceRulesStateflow: StateFlow<UiState<PriceRules>> = _priceRulesStateflow

    init {
        loadCouponImages()
    }

    private fun loadCouponImages() {
        val images = listOf(
            Coupons(R.drawable.discound2, "50% off"),
            Coupons(R.drawable.discound1, "30% off"),
            Coupons(R.drawable.discound3, "30% off"),
        )
        _couponImages.value = images
    }

    private var job: Job? = null

    fun getCoupons() {
        job?.cancel()
        _couponsStateflow.value = UiState.Loading
        job = viewModelScope.launch(Dispatchers.IO) {
            try {
                val priceRules = remoteDataSource.getPriceRules().first()
                _priceRulesStateflow.value = UiState.Success(priceRules)

                val discountCodes = priceRules.price_rules.map { priceRule ->
                    remoteDataSource.getCopuons(priceRule.id).first()
                }
                _couponsStateflow.value = UiState.Success(discountCodes)
            } catch (e: Exception) {
                _couponsStateflow.value = UiState.Error(e.message ?: "An error occurred")
            }
        }
    }
}

class CouponsViewModelFactory(private val repo: IReposiatory) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CouponViewModel::class.java)) {
            return CouponViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}
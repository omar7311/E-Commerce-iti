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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class CouponViewModel(val remoteDataSource: IReposiatory) : ViewModel() {
    private val _couponImages = MutableLiveData<List<Coupons>>()
    val couponImages: LiveData<List<Coupons>> get() = _couponImages

    init {
        // Load images from a repository or generate them
        loadCouponImages()
    }

    private val _couponsStateflow = MutableStateFlow<UiState<List<DiscountCode>>>(UiState.Loading)
    val couponsStateflow: StateFlow<UiState<List<DiscountCode>>> = _couponsStateflow
    private val _priceRulesStateflow = MutableStateFlow<UiState<PriceRules>>(UiState.Loading)

    private fun loadCouponImages() {
        // You can either fetch images from an API or locally
        val images = listOf(
            Coupons(R.drawable.discound2, "50% off"),
            Coupons(R.drawable.discound1, "30% off"),
            Coupons(R.drawable.discound3, "30% off"),
        )
        _couponImages.value = images
    }

    var job: Job? = null
    private suspend fun getPriceRules() = remoteDataSource.getPriceRules()
    fun getCoupons() {
        job?.cancel()
        _couponsStateflow.value = UiState.Loading
        job = viewModelScope.launch(Dispatchers.IO) {
            getPriceRules().collect {
                _priceRulesStateflow.value = UiState.Success(it)
                val list = mutableListOf<DiscountCode>()
                for (i in it.price_rules) {
                    val cop = remoteDataSource.getCopuons(i.id).first()
                    list.add(cop)
                }
                _couponsStateflow.value = UiState.Success(list)
            }
        }
    }

}

class CouponsViewModelFactory(private val repo: IReposiatory) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(CouponViewModel::class.java) -> {
                CouponViewModel(repo) as T
            }

            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}

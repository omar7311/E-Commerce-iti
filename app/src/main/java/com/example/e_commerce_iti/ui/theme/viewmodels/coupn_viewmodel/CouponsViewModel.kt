package com.example.e_commerce_iti.ui.theme.viewmodels.coupn_viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.e_commerce_iti.R
import com.example.e_commerce_iti.model.pojos.Coupons
import com.example.e_commerce_iti.model.pojos.discountcode.DiscountCode
import com.example.e_commerce_iti.model.pojos.price_rules.PriceRules
import com.example.e_commerce_iti.model.remote.IRemoteDataSource
import com.example.e_commerce_iti.model.reposiatory.IReposiatory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CouponViewModel(val remoteDataSource: IReposiatory) : ViewModel() {
    private val _couponImages = MutableLiveData<List<Coupons>>()
    val couponImages: LiveData<List<Coupons>> get() = _couponImages

    init {
        // Load images from a repository or generate them
        loadCouponImages()
    }
    private val _couponsStateflow= MutableStateFlow<UiState<DiscountCode>>(UiState.Loading)
     val couponsStateflow: StateFlow<UiState<DiscountCode>> =_couponsStateflow
    private val _priceRulesStateflow= MutableStateFlow<UiState<PriceRules>>(UiState.Loading)
    val priceRulesStateflow: StateFlow<UiState<PriceRules>> =_priceRulesStateflow
    private fun loadCouponImages() {
        // You can either fetch images from an API or locally
        val images = listOf(
            Coupons(R.drawable.coupon1, "50% off"),
            Coupons(R.drawable.coupon2, "30% off"),
            Coupons(R.drawable.coupon3, "30% off"),
        )
        _couponImages.value = images
    }
    var job: Job? = null
    private suspend fun getPriceRules() =remoteDataSource.getPriceRules()
    fun getCoupons(priceId: Long) {
        job?.cancel()
        _couponsStateflow.value=UiState.Loading
       job=viewModelScope.launch(Dispatchers.IO) {
            getPriceRules().collect{
                _priceRulesStateflow.value=UiState.Success(it)
                for (i in it.price_rules){
                    remoteDataSource.getCopuons(i.id).collect{
                        _couponsStateflow.value=UiState.Success(it)
                    }
                }
            }
        }
    }

}
sealed class UiState<out T> {
    object Loading : UiState<Nothing>()
    data class Success<out T>(val data: T) : UiState<T>()
    data class Error(val message: String) : UiState<Nothing>()
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

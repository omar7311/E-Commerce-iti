package com.example.e_commerce_iti.ui.theme.viewmodels.home_viewmodel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commerce_iti.model.apistates.BrandsApiState
import com.example.e_commerce_iti.model.apistates.ProductsApiState
import com.example.e_commerce_iti.model.pojos.Product
import com.example.e_commerce_iti.model.reposiatory.IReposiatory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

/**
 *      intially create a view Model to fetch brands
 */
class HomeViewModel(val repo:IReposiatory) : ViewModel() {

    // fist brand stateFlow
    private val _brandsStateFlow = MutableStateFlow<BrandsApiState>(BrandsApiState.Loading)
    val brandStateFlow = _brandsStateFlow.asStateFlow()
    // second products stateFlow
    private val _productStateFlow = MutableStateFlow<ProductsApiState>(ProductsApiState.Loading)
    val productStateFlow = _productStateFlow.asStateFlow()

    /**
     *      methods
     */
    fun getBrands(){
        viewModelScope.launch(Dispatchers.IO) {
            repo.getBrands()
                .catch { error -> _brandsStateFlow.value = BrandsApiState.Failure(error) }
                .collect { brands ->
                _brandsStateFlow.value = BrandsApiState.Success(brands)
            }
        }
    }

    fun getProductsByVendor(vendornName: String){
        viewModelScope.launch(Dispatchers.IO) {
            repo.getProductsByVendor(vendornName)
                .catch { error -> _productStateFlow.value = ProductsApiState.Failure(error) }
                .collect { products ->
                _productStateFlow.value = ProductsApiState.Success(products)
            }
        }
    }
}

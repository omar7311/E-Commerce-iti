package com.example.e_commerce_iti.ui.theme.viewmodels.home_viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commerce_iti.model.apistates.BrandsApiState
import com.example.e_commerce_iti.model.apistates.CustomCollectionStates
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
class HomeViewModel(val repo: IReposiatory) : ViewModel() {

    // fist brand stateFlow
    private val _brandsStateFlow = MutableStateFlow<BrandsApiState>(BrandsApiState.Loading)
    val brandStateFlow = _brandsStateFlow.asStateFlow()

    // second products stateFlow
    private val _productStateFlow = MutableStateFlow<ProductsApiState>(ProductsApiState.Loading)
    val productStateFlow = _productStateFlow.asStateFlow()

    // third state flow to get custom collections
    val _customCollectionStateFlow =
        MutableStateFlow<CustomCollectionStates>(CustomCollectionStates.Loading)
    val customCollectionStateFlow = _customCollectionStateFlow.asStateFlow()

    // fourth state flow to get products by custom collection
    val _productsByCustomCollectionStateFlow =
        MutableStateFlow<ProductsApiState>(ProductsApiState.Loading)
    val productsByCustomCollectionStateFlow = _productsByCustomCollectionStateFlow.asStateFlow()

    /**
     *      methods
     */
    fun getBrands() {
        viewModelScope.launch(Dispatchers.IO) {
            repo.getBrands()
                .catch { error -> _brandsStateFlow.value = BrandsApiState.Failure(error) }
                .collect { brands ->
                    _brandsStateFlow.value = BrandsApiState.Success(brands)
                }
        }
    }

    fun getProductsByVendor(vendornName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.getProductsByVendor(vendornName)
                .catch { error -> _productStateFlow.value = ProductsApiState.Failure(error) }
                .collect { products ->
                    _productStateFlow.value = ProductsApiState.Success(products)
                }
        }
    }

    fun getCustomCollections() {
        viewModelScope.launch(Dispatchers.IO) {
            repo.getCustomCollections()
                .catch { error ->
                    _customCollectionStateFlow.value = CustomCollectionStates.Failure(error)
                }
                .collect { customCollections ->
                    // Ignore the first item in the list
                    val collectionsToDisplay = customCollections.drop(1)
                    _customCollectionStateFlow.value =
                        CustomCollectionStates.Success(collectionsToDisplay)
                }
        }
    }

    fun getProductsByCustomCollection(collectionId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.getProductsByCustomCollection(collectionId)
                .catch { error ->
                    _productsByCustomCollectionStateFlow.value = ProductsApiState.Failure(error)
                }.collect { products ->
                    _productsByCustomCollectionStateFlow.value = ProductsApiState.Success(products)
                }
        }
    }
}

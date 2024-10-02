package com.example.e_commerce_iti.ui.theme.viewmodels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commerce_iti.model.apistates.BrandsApiState
import com.example.e_commerce_iti.model.reposiatory.IReposiatory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 *      intially create a view Model to fetch brands
 */
class HomeViewModel(val repo:IReposiatory) : ViewModel() {

    // fist brand stateFlow
    private val _brandsStateFlow = MutableStateFlow<BrandsApiState>(BrandsApiState.Loading())
    val brandStateFlow = _brandsStateFlow.asStateFlow()

    fun getBrands(){
        viewModelScope.launch(Dispatchers.IO) {
            repo.getBrands().collect { brands ->
                _brandsStateFlow.value = BrandsApiState.Success(brands)
            }
        }
    }
}

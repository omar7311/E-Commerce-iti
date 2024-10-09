package com.example.e_commerce_iti.ui.theme.viewmodels.searchViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.e_commerce_iti.model.apistates.UiState
import com.example.e_commerce_iti.model.pojos.AllProduct
import com.example.e_commerce_iti.model.pojos.Product
import com.example.e_commerce_iti.model.pojos.draftorder.DraftOrder
import com.example.e_commerce_iti.model.reposiatory.IReposiatory
import com.example.e_commerce_iti.ui.theme.viewmodels.productInfo_viewModel.ProductInfoViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SearchViewModel(var repo:IReposiatory):ViewModel() {
    private val _allProduct = MutableStateFlow<UiState<List<Product>>>(UiState.Loading)
    val allProduct: StateFlow<UiState<List<Product>>> = _allProduct

   fun getAllProduct(){
       viewModelScope.launch {
           repo.getAllProduct().catch {
               _allProduct.emit(UiState.Failure(it as Exception))
           }.collectLatest{
               _allProduct.emit(UiState.Success(it.products))
           }
       }
   }

}

class SearchViewModelFac(private val repo: IReposiatory) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(SearchViewModel::class.java) -> {
                SearchViewModel(repo) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}
package com.example.e_commerce_iti.ui.theme.viewmodels.productInfo_viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.e_commerce_iti.model.apistates.UiState
import com.example.e_commerce_iti.model.pojos.draftorder.DraftOrder
import com.example.e_commerce_iti.model.reposiatory.IReposiatory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class ProductInfoViewModel(var repo:IReposiatory):ViewModel() {
    private val _draftOrderState = MutableStateFlow<UiState<DraftOrder>>(UiState.Loading)
    val draftOrderState: StateFlow<UiState<DraftOrder>> = _draftOrderState
    private val _upDatedDraftOrderState = MutableStateFlow<UiState<DraftOrder>>(UiState.Loading)
    val updatedDraftOrderState: StateFlow<UiState<DraftOrder>> = _upDatedDraftOrderState
    fun getDraftOrder(id:Long) {
        viewModelScope.launch {
            repo.getCart(id).
                    catch { _draftOrderState.value=UiState.Failure(it as Exception) }
                .collect{ _draftOrderState.value=UiState.Success(it) }

        }
    }

    fun updateDraftOrder(draftOrder:DraftOrder) {
        viewModelScope.launch {
            repo.updateCart(draftOrder).
                    catch { _upDatedDraftOrderState.value=UiState.Failure(it as Exception) }
                .collect{  _upDatedDraftOrderState.value=UiState.Success(it) }
        }
    }


  /*  *//***
     *      some functions needed for add infavorite
     *//*

    private val _favoritesMap = MutableStateFlow<Map<Long, Boolean>>(emptyMap())
    val favoritesMap: StateFlow<Map<Long, Boolean>> = _favoritesMap

    fun setInFavorites(productId: Long, value: Boolean) {
        _favoritesMap.value = _favoritesMap.value + (productId to value)
    }

    fun checkIfInFavorites(productId: Long, draftOrder: DraftOrder) {
        val isFavorite = draftOrder.line_items.any { it.product_id == productId }
        setInFavorites(productId, isFavorite)
    }*/
}




class ProductInfoViewModelFac(private val repo: IReposiatory) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(ProductInfoViewModel::class.java) -> {
                ProductInfoViewModel(repo) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}

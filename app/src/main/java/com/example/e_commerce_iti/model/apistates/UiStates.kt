package com.example.e_commerce_iti.model.apistates

sealed class UiState<out T> {
    object Loading : UiState<Nothing>()
    data class Success<out T>(val data: T) : UiState<T>()
    data class Error(val message: String) : UiState<Nothing>()
    object Non : UiState<Nothing>()
    data class Failure(val exception: Exception) : UiState<Nothing>()
}
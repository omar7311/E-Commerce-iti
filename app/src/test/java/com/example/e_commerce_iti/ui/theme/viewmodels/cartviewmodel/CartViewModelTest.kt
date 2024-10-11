package com.example.e_commerce_iti.ui.theme.viewmodels.cartviewmodel

import com.example.e_commerce_iti.model.reposiatory.IReposiatory
import org.junit.jupiter.api.Assertions.*
import org.mockito.Mockito.mock

class CartViewModelTest {

    @org.junit.jupiter.api.Test
    fun getCartState() {
        val cartRepository = mock(IReposiatory::class.java)
        val viewModel = CartViewModel(cartRepository)

    }

    @org.junit.jupiter.api.Test
    fun getProduct() {
    }

    @org.junit.jupiter.api.Test
    fun getTotalAmount() {
    }

    @org.junit.jupiter.api.Test
    fun getNavigateto() {
    }

    @org.junit.jupiter.api.Test
    fun getCartDraftOrder() {
    }

    @org.junit.jupiter.api.Test
    fun endnav() {
    }

    @org.junit.jupiter.api.Test
    fun add() {
    }

    @org.junit.jupiter.api.Test
    fun gettotalValue() {
    }

    @org.junit.jupiter.api.Test
    fun sub() {
    }

    @org.junit.jupiter.api.Test
    fun getCurrentCurrency() {
    }

    @org.junit.jupiter.api.Test
    fun updateCart() {
    }

    @org.junit.jupiter.api.Test
    fun getCurrency() {
    }

    @org.junit.jupiter.api.Test
    fun submit() {
    }
}
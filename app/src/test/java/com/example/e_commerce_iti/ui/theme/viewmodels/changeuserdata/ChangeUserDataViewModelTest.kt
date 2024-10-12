package com.example.e_commerce_iti.ui.theme.viewmodels.changeuserdata

import com.example.e_commerce_iti.model.apistates.UiState
import com.example.e_commerce_iti.model.reposiatory.IReposiatory
import com.example.e_commerce_iti.model.viewmodels.FakeReposiatory
import com.example.e_commerce_iti.ui.theme.viewmodels.cartviewmodel.CartViewModel
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class ChangeUserDataViewModelTest{
    private lateinit var viewModel: ChangeUserDataViewModel
    private  lateinit var repo: IReposiatory
    @Before
    fun setUp() {
        val mockReposiatory = FakeReposiatory()
        viewModel = ChangeUserDataViewModel(mockReposiatory)
    }
    @Test
    fun dsadsd()= runTest{
        viewModel.getCustomerData("alice.smith@example.com")
        launch {
            viewModel.userStateData.collect{
                if (it is UiState.Success){
                    assert(it.data.email=="alice.smith@example.com")
                    cancel()
                }
            }
        }
    }
    @Test
    fun asdsad()= runTest {
        var a=true
        viewModel.getCustomerData("alice.smith@example.com")
        launch {
            viewModel.userStateData.collect{
                println(it)
                if (it is UiState.Success&&a){
                    a=false
                    viewModel.updateCustomerData("asddas","asddas","adda","sdsdasd")
                }
                if (it is UiState.Success&&!a){
                    println(it.data)
                    assert(it.data.email=="alice.smith@example.com")
                    cancel()
                }
            }
        }

    }
}
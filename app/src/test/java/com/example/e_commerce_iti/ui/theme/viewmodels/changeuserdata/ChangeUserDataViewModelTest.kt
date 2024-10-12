package com.example.e_commerce_iti.ui.theme.viewmodels.changeuserdata

import com.example.e_commerce_iti.model.apistates.UiState
import com.example.e_commerce_iti.model.reposiatory.IReposiatory
import com.example.e_commerce_iti.model.viewmodels.FackRemoteReposiatory
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
        val mockReposiatory = FackRemoteReposiatory()
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
        viewModel.getCustomerData("alice.smith@example.com")
        viewModel.updateCustomerData("asddas","asddas","adda","sdsdasd")
        launch {
            viewModel.userStateData.collect{
                if (it is UiState.Success&& it.data.first_name=="asddas"){
                    assert(it.data.first_name=="asddas")
                }
            }
        }
    }
}
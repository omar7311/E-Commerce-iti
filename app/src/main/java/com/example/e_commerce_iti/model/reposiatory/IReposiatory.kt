package com.example.e_commerce_iti.model.reposiatory

import com.example.e_commerce_iti.model.pojos.BrandData
import kotlinx.coroutines.flow.Flow

interface IReposiatory {

    suspend fun getBrands(): Flow<List<BrandData>>
}
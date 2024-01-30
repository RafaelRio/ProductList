package com.example.productlist.ui.list

import com.example.productlist.data.ProductsResponse
import com.example.productlist.repository.ProductListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProductListViewmodel @Inject constructor(
    val plRepo: ProductListRepository
) {


    suspend fun getAllProducts(): ProductsResponse {
        return plRepo.getAllProducts()
    }
}
package com.example.productlist.repository

import com.example.productlist.api.ProductListApi
import com.example.productlist.data.ProductsResponse
import com.example.productlist.data.UserResponse
import com.example.productlist.utils.Constants
import javax.inject.Inject

class ProductListRepository @Inject constructor(
    private val plApi: ProductListApi,
) {

    suspend fun login(userName: String, password: String): UserResponse {
        return plApi.login(username = userName, password = password)
    }

    suspend fun getAllProducts(): ProductsResponse {
        return plApi.getAllProducts()
    }

    suspend fun getSmartPhones(): ProductsResponse {
        return plApi.getSmartPhones()
    }
}
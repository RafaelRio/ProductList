package com.example.productlist.repository

import com.example.productlist.api.ProductListApi
import com.example.productlist.data.UserResponse
import javax.inject.Inject

class ProductListRepository @Inject constructor(
    private val plApi: ProductListApi,
) {

    suspend fun login(userName: String, password: String): UserResponse {
        return plApi.login(username = userName, password = password)
    }
}
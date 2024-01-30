package com.example.productlist.api

import com.example.productlist.data.ProductsResponse
import com.example.productlist.data.UserResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ProductListApi {

    @FormUrlEncoded
    @POST("auth/login")
    suspend fun login(
        @Field("username") username: String,
        @Field("password") password: String,
    ): UserResponse

    @GET("products?limit=50")
    suspend fun getAllProducts(): ProductsResponse

    @GET("products/category/smartphones")
    suspend fun getSmartPhones(): ProductsResponse
}
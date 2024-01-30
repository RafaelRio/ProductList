package com.example.productlist.api

import com.example.productlist.data.Product
import com.example.productlist.data.ProductsResponse
import com.example.productlist.data.UserResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ProductListApi {

    @FormUrlEncoded
    @POST("auth/login")
    suspend fun login(
        @Field("username") username: String,
        @Field("password") password: String,
    ): UserResponse

    @GET("products?limit=50")
    suspend fun getAllProducts(): ProductsResponse

    /*
        Aquí se podría hacer un selector con todas las categorías obteniéndolas de
        products/categories
     */
    @GET("products/category/{category}")
    suspend fun getProductByCategory(
        @Path("category") category: String
    ): ProductsResponse

    @FormUrlEncoded
    @POST("products/add")
    suspend fun addProduct(
        @Field("id") id: Int = 200,
        @Field("title") title: String,
        @Field("description") description: String,
        @Field("price") price: Double,
        @Field("discountPercentage") discountPercentage: Double,
        @Field("rating") rating: Double,
        @Field("stock") stock: Int,
        @Field("brand") brand: String,
        @Field("category") category: String,
        @Field("thumbnail") thumbnail: String,
        @Field("images") images: List<String> = emptyList()
    ): Product
}
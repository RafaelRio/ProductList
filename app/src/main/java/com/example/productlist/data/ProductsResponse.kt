package com.example.productlist.data

import com.google.gson.annotations.SerializedName

data class ProductsResponse(
    @SerializedName("products")
    val productList: List<Product>,

    @SerializedName("total")
    val total: Int,

    @SerializedName("skip")
    val skip: Int,

    @SerializedName("limit")
    val limit: Int
)

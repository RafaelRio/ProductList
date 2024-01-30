package com.example.productlist.repository

import com.example.productlist.api.ProductListApi
import com.example.productlist.data.Product
import com.example.productlist.data.ProductsResponse
import com.example.productlist.data.UserResponse
import com.example.productlist.utils.Constants
import javax.inject.Inject

class ProductRepository @Inject constructor(
    private val productAPI: ProductListApi,
) {

    suspend fun login(userName: String, password: String): UserResponse {
        return productAPI.login(username = userName, password = password)
    }

    suspend fun getAllProducts(): ProductsResponse {
        return productAPI.getAllProducts()
    }

    suspend fun getSmartPhones(): ProductsResponse {
        return productAPI.getProductByCategory(category = Constants.SMARTPHONE)
    }

    suspend fun addProduct(product: Product): Product {
        return productAPI.addProduct(
            title = product.title,
            description = product.description,
            price = product.price,
            discountPercentage = product.discountPercentage,
            rating = product.rating,
            stock = product.stock,
            brand = product.brand,
            category = product.category,
            thumbnail = product.thumbnail
        )
    }
}
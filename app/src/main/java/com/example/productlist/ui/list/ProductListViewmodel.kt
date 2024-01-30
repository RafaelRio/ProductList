package com.example.productlist.ui.list

import androidx.lifecycle.ViewModel
import com.example.productlist.data.Product
import com.example.productlist.repository.ProductListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ProductListViewmodel @Inject constructor(
    val plRepo: ProductListRepository
): ViewModel() {

    var list = mutableListOf<Product>()

    private val _filterCategory = MutableStateFlow(false)
    val filterCategory = _filterCategory.asStateFlow()
    suspend fun getAllProducts() {
        list = plRepo.getAllProducts().productList.toMutableList()
    }

    suspend fun getSmartPhones() {
        list = plRepo.getSmartPhones().productList.toMutableList()
    }

    fun setFilterActive(active: Boolean) {
        _filterCategory.value = active
    }
}
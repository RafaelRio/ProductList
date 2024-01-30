package com.example.productlist.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.viewModelScope
import com.example.productlist.data.Product
import com.example.productlist.repository.ProductListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

@HiltViewModel
class ProductListViewmodel @Inject constructor(
    private val plRepo: ProductListRepository
): ViewModel() {

    private val _list = MutableStateFlow<List<Product>>(emptyList())
    val list = _list.asStateFlow()

    private val _showOnlySmartphones = MutableStateFlow(false)
    val showOnlySmartphones = _showOnlySmartphones.asStateFlow()

    private val _loadingState = MutableStateFlow(false)
    val loadingState = _loadingState.asStateFlow()

    init {
        viewModelScope.launch {
            showOnlySmartphones.collectLatest {
                _loadingState.value = true
                _list.value = getProducts(it) ?: emptyList()
                _loadingState.value = false
            }
        }
    }

    private suspend fun getProducts(showOnlySmartphones: Boolean): List<Product>? {
        try {
            return if(!showOnlySmartphones) {
                plRepo.getAllProducts().productList
            } else {
                plRepo.getSmartPhones().productList
            }
        }catch (e: IOException) {
            e.printStackTrace()
        } catch (e: HttpException) {
            e.printStackTrace()
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null

    }

    fun setFilterActive(active: Boolean) {
        _showOnlySmartphones.value = active
    }
}
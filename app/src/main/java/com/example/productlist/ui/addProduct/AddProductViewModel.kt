package com.example.productlist.ui.addProduct

import androidx.lifecycle.ViewModel
import com.example.productlist.data.Product
import com.example.productlist.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

@HiltViewModel
class AddProductViewModel @Inject constructor(
    private val productRepository: ProductRepository
) : ViewModel() {

    private val _productImage = MutableStateFlow("")
    val productImage = _productImage.asStateFlow()

    private val _productTitle = MutableStateFlow("")
    val productTitle = _productTitle.asStateFlow()

    private val _productDescription = MutableStateFlow("")
    val productDescription = _productDescription.asStateFlow()

    private val _productPrice = MutableStateFlow(0.0)
    val productPrice = _productPrice.asStateFlow()

    private val _productDiscountPercentage = MutableStateFlow(0.0)
    val productDiscountPercentage = _productDiscountPercentage.asStateFlow()

    private val _productRating = MutableStateFlow(0.0)
    val productRating = _productRating.asStateFlow()

    private val _productStock = MutableStateFlow(0)
    val productStock = _productStock.asStateFlow()

    private val _productBrand = MutableStateFlow("")
    val productBrand = _productBrand.asStateFlow()

    private val _productCategory = MutableStateFlow("")
    val productCategory = _productCategory.asStateFlow()

    fun setImage(image: String) {
        _productImage.value = image
    }

    fun setTitle(title: String) {
        _productTitle.value = title
    }

    fun setDescription(description: String) {
        _productDescription.value = description
    }

    fun setPrice(price: Double) {
        _productPrice.value = price
    }

    fun setDiscountPercentage(discount: Double) {
        //Entiendo que el descuento no puede estar por encima del 100. Si lo ponen por encima, lo acoto yo
        _productDiscountPercentage.value = if (discount > 100.0) 100.0 else discount
    }

    fun setRating(rating: Double) {
        //Pasa igual que con el descuento pero con valor sobre 10
        _productRating.value = if (rating > 10.0) 10.0 else rating
    }

    fun setStock(stock: Int) {
        _productStock.value = stock
    }

    fun setBrand(brand: String) {
        _productBrand.value = brand
    }

    fun setCategory(category: String) {
        _productCategory.value = category
    }


    suspend fun addProduct(product: Product): Product? {
        try {
            return productRepository.addProduct(product)
        } catch (e: IOException) {
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
}
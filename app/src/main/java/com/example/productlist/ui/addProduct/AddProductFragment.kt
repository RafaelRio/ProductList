package com.example.productlist.ui.addProduct

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.productlist.data.Product
import com.example.productlist.databinding.FragmentAddProductBinding
import com.example.productlist.ui.productDetail.SeeProductDetailsFragment
import com.example.productlist.utils.isNotZero
import com.example.productlist.utils.onTextChanged
import com.example.productlist.utils.parseDouble
import com.example.productlist.utils.parseInt
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddProductFragment : Fragment() {

    private lateinit var binding: FragmentAddProductBinding
    private lateinit var addProductViewmodel: AddProductViewModel
    private var product: Product? = null
    private lateinit var onBackPressedCallback: OnBackPressedCallback

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addProductViewmodel = ViewModelProvider(this)[AddProductViewModel::class.java]
        onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigateUp()
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddProductBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    override fun onDestroy() {
        super.onDestroy()
        // Elimina el callback cuando la actividad se destruye para evitar fugas de memoria
        onBackPressedCallback.remove()
    }

    private fun initUI() {
        setValues()
        binding.apply {
            ivProduct.setOnClickListener {
                pickMedia.launch(PickVisualMediaRequest(PickVisualMedia.ImageOnly))
            }
            btnAddProduct.setOnClickListener {
                product = Product(
                    id = 101,
                    title = addProductViewmodel.productTitle.value,
                    description = addProductViewmodel.productDescription.value,
                    price = addProductViewmodel.productPrice.value,
                    discountPercentage = addProductViewmodel.productDiscountPercentage.value,
                    rating = addProductViewmodel.productRating.value,
                    stock = addProductViewmodel.productStock.value,
                    brand = addProductViewmodel.productBrand.value,
                    category = addProductViewmodel.productCategory.value,
                    thumbnail = addProductViewmodel.productImage.value,
                    images = emptyList(),
                )

                lifecycleScope.launch {
                    val addedProduct = addProductViewmodel.addProduct(product!!)

                    if (addedProduct != null && checkFields()) {
                        val deatilDialog = SeeProductDetailsFragment(addedProduct)
                        deatilDialog.show(requireActivity().supportFragmentManager, "detail_dialog")
                    } else {
                        Toast.makeText(requireContext(), "Error when creating product", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun setValues() {
        binding.apply {
            try {
                etProductName.onTextChanged { name ->
                    addProductViewmodel.setTitle(name)
                }
                etProductDescription.onTextChanged { description ->
                    addProductViewmodel.setDescription(description)
                }
                etProductPrice.onTextChanged {
                    val price = parseDouble(it)
                    addProductViewmodel.setPrice(price)
                }
                etProductDiscount.onTextChanged {
                    val discount = parseDouble(it)
                    addProductViewmodel.setDiscountPercentage(discount)
                }
                etProductRating.onTextChanged {
                    val rating = parseDouble(it)
                    addProductViewmodel.setRating(rating)
                }
                etProductStock.onTextChanged {
                    val stock = parseInt(it)
                    addProductViewmodel.setStock(stock)
                }
                etProductBrand.onTextChanged { brand ->
                    addProductViewmodel.setBrand(brand)
                }
                etProductCategory.onTextChanged { category ->
                    addProductViewmodel.setCategory(category)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }

    private fun checkFields(): Boolean {
        return (addProductViewmodel.productImage.value.isNotEmpty() && addProductViewmodel.productTitle.value.isNotEmpty() &&
                addProductViewmodel.productDescription.value.isNotEmpty() && addProductViewmodel.productPrice.value.isNotZero() &&
                addProductViewmodel.productDiscountPercentage.value.isNotZero() && addProductViewmodel.productRating.value.isNotZero() &&
                addProductViewmodel.productStock.value.isNotZero() && addProductViewmodel.productBrand.value.isNotEmpty() &&
                addProductViewmodel.productCategory.value.isNotEmpty())
    }

    private val pickMedia = registerForActivityResult(PickVisualMedia()) { uri ->
        if (uri != null) {
            addProductViewmodel.setImage(uri.toString())
            binding.ivProduct.setImageURI(uri)
        }
    }
}
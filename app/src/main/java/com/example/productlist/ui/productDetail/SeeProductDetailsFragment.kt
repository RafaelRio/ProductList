package com.example.productlist.ui.productDetail

import android.annotation.SuppressLint
import android.graphics.Point
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import coil.load
import com.example.productlist.R
import com.example.productlist.data.Product
import com.example.productlist.databinding.FragmentSeeProductDetailsBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SeeProductDetailsFragment(
    val product: Product
) : DialogFragment() {

    private lateinit var binding: FragmentSeeProductDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSeeProductDetailsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fillData()
    }

    override fun onResume() {
        super.onResume()
        /*
            Este fragmento hace que el diálogo ocupe más ancho, para que así se visualice mejor
         */
        val window = dialog!!.window
        if (window != null) {
            val size = Point()
            val display = window.windowManager.defaultDisplay
            display.getSize(size)
            val width = size.x
            val height = size.y
            window.setLayout((width * 0.9).toInt(), height)
        }
    }

    /*
        Con esta anotación no se queja de la forma en la que se concadenan los strings
     */
    @SuppressLint("SetTextI18n")
    private fun fillData() {
        binding.apply {
            ivProduct.load(product.thumbnail)
            tvProductName.text =
                "${requireContext().getString(R.string.product_name)}: ${product.title}"
            tvProductDescription.text =
                "${requireContext().getString(R.string.product_description)}: ${product.description}"
            tvProductPrice.text =
                "${requireContext().getString(R.string.product_price)}: ${product.price}"
            tvProductDiscount.text =
                "${requireContext().getString(R.string.product_discount_percentage)}: ${product.discountPercentage}"
            tvProductRating.text =
                "${requireContext().getString(R.string.product_rating)}: ${product.rating}"
            tvProductStock.text =
                "${requireContext().getString(R.string.product_stock)}: ${product.stock}"
            tvProductBrand.text =
                "${requireContext().getString(R.string.product_brand)}: ${product.brand}"
            tvProductCategory.text =
                "${requireContext().getString(R.string.product_category)}: ${product.category}"
        }
    }
}
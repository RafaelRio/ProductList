package com.example.productlist.ui.addProduct

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.flowWithLifecycle
import com.example.productlist.R
import com.example.productlist.databinding.FragmentAddProductBinding
import com.example.productlist.ui.login.LoginViewModel
import com.example.productlist.utils.onTextChanged
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest


@AndroidEntryPoint
class AddProductFragment: Fragment() {

    private lateinit var binding: FragmentAddProductBinding
    private lateinit var addProductViewmodel: AddProductViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addProductViewmodel = ViewModelProvider(this)[AddProductViewModel::class.java]
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

    private fun initUI() {
        binding.apply {
            ivProduct.setOnClickListener {
                pickMedia.launch(PickVisualMediaRequest(PickVisualMedia.ImageOnly))
            }
        }
    }

    private fun setValues() {
        binding.apply {
            etProductName.onTextChanged { name ->
                addProductViewmodel.setTitle(name)
            }
            etProductDescription.onTextChanged { description ->
                addProductViewmodel.setDescription(description)
            }
            etProductPrice.onTextChanged { price ->
                addProductViewmodel.setPrice(price.toDouble())
            }
        }
    }

    private val pickMedia = registerForActivityResult(PickVisualMedia()) { uri ->
        if (uri != null) {

            binding.ivProduct.setImageURI(uri)
        }
    }
}
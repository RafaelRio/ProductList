package com.example.productlist.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.datastore.preferences.core.edit
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.productlist.R
import com.example.productlist.databinding.FragmentListProductsBinding
import com.example.productlist.ui.login.dataStore
import com.example.productlist.ui.productDetail.SeeProductDetailsFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProductsListFragment : Fragment() {

    private lateinit var binding: FragmentListProductsBinding
    private val plListAdapter = ProductListAdapter { product ->
        val detailDialog = SeeProductDetailsFragment(product)
        detailDialog.show(requireActivity().supportFragmentManager, "detail_dialog")
    }
    private lateinit var plViewModel: ProductListViewmodel
    private lateinit var onBackPressedCallback: OnBackPressedCallback

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        plViewModel = ViewModelProvider(this)[ProductListViewmodel::class.java]
        onBackPressedCallback = object : OnBackPressedCallback(true) {
            /*
                Así controlo que si se pulsa hacia atrás en el dispositivo, en vez de volver
                al login, se cierre la aplicación
             */
            override fun handleOnBackPressed() {
                requireActivity().finish()
                requireActivity().moveTaskToBack(true)
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(this, onBackPressedCallback)

        lifecycleScope.launch {
            plViewModel.showOnlySmartphones.flowWithLifecycle(lifecycle).collectLatest { showOnlySmartphones ->
                binding.cbRemember.isChecked = showOnlySmartphones
            }
        }

        lifecycleScope.launch {
            plViewModel.list.flowWithLifecycle(lifecycle).collectLatest { productList ->
                plListAdapter.submitList(productList)
            }
        }

        lifecycleScope.launch {
            plViewModel.loadingState.flowWithLifecycle(lifecycle).collectLatest { isLoading ->
                binding.pbLoading.isVisible = isLoading
                binding.btnAddProduct.isGone = isLoading
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListProductsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupList()
        binding.cbRemember.setOnCheckedChangeListener { _, isChecked ->
            plViewModel.setFilterActive(isChecked)
        }
        binding.btnCloseSession.setOnClickListener {
            closeSession()
        }
        binding.btnAddProduct.setOnClickListener {
            findNavController().navigate(R.id.action_productsListFragment_to_addProductFragment)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        // Elimina el callback cuando la actividad se destruye para evitar fugas de memoria
        onBackPressedCallback.remove()
    }

    private fun closeSession() {
        // Se hace con el Dispatchers.IO porque así no bloquea el hilo principal
        lifecycleScope.launch(Dispatchers.IO) {
            requireContext().dataStore.edit { preferences ->
                preferences.clear()
                requireActivity().finish()
            }
        }
    }

    private fun setupList() {
        binding.rvProductList.adapter = plListAdapter
    }
}
package com.example.productlist.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.example.productlist.databinding.FragmentListProductsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProductsListFragment: Fragment() {

    private lateinit var binding: FragmentListProductsBinding
    private val plListAdapter = ProductListAdapter()
    private lateinit var plViewModel: ProductListViewmodel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        plViewModel = ViewModelProvider(this)[ProductListViewmodel::class.java]

        lifecycleScope.launch {
            plViewModel.showOnlySmartphones.flowWithLifecycle(lifecycle).collectLatest {
                binding.cbRemember.isChecked = it
            }
        }

        lifecycleScope.launch {
            plViewModel.list.flowWithLifecycle(lifecycle).collectLatest {
                plListAdapter.submitList(it)
            }
        }

        lifecycleScope.launch {
            plViewModel.loadingState.flowWithLifecycle(lifecycle).collectLatest {
                binding.pbLoading.isVisible = it
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
//        binding.tv.setOnClickListener {
//            lifecycleScope.launch(Dispatchers.IO) {
//                requireContext().dataStore.edit {
//                    it.clear()
//                    requireActivity().finish()
//                }
//            }
//        }

    }

    private fun setupList() {
        binding.rvProductList.adapter = plListAdapter
    }
}
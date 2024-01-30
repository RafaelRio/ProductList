package com.example.productlist.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.edit
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.example.productlist.databinding.FragmentListProductsBinding
import com.example.productlist.ui.login.dataStore
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProductsListFragment: Fragment() {

    private lateinit var binding: FragmentListProductsBinding
    private lateinit var plListAdapter: ProductListAdapter
    private lateinit var plViewModel: ProductListViewmodel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        plViewModel = ViewModelProvider(this)[ProductListViewmodel::class.java]

        lifecycleScope.launch {
            plViewModel.filterCategory.flowWithLifecycle(lifecycle).collectLatest {
                if (!it) {
                    plViewModel.getAllProducts()
                } else {
                    plViewModel.getSmartPhones()
                }
                plListAdapter.submitList(plViewModel.list)
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

        plListAdapter = ProductListAdapter(
            context = requireContext(),
            itemClickListener = {

            }
        )
        lifecycleScope.launch {
            plViewModel.getAllProducts()
            plListAdapter.submitList(plViewModel.list)
        }
        binding.rvProductList.adapter = plListAdapter

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
}
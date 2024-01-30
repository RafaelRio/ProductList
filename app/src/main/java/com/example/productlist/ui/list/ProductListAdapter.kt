package com.example.productlist.ui.list

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.productlist.R
import com.example.productlist.data.Product
import com.example.productlist.databinding.ItemProductBinding

class ProductListAdapter(
    private val context: Context,
    private val itemClickListener: (item: Product) -> Unit,
) : ListAdapter<Product, ProductListAdapter.ViewHolder>(
    object : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product) = oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Product, newItem: Product) = oldItem == newItem
    }) {
    inner class ViewHolder(val binding: ItemProductBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ProductListAdapter.ViewHolder, position: Int) {
        val item = getItem(position)

        item?.also { product ->
            holder.itemView.setOnClickListener { itemClickListener(item) }

            holder.binding.apply {
                //La carga de imágenes se realiza con la librería Coil
                imvProduct.load(product.images[0])

                tvProductName.text = product.title
                tvProductDescription.text = product.description
                tvPrice.text = "${context.applicationContext.getString(R.string.price)} ${product.price}€"
                tvStock.text = "${context.applicationContext.getString(R.string.stock)} ${product.stock} uds."

            }
        }
    }
}
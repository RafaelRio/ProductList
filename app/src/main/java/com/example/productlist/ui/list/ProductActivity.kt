package com.example.productlist.ui.list

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.productlist.R
import com.example.productlist.ui.login.dataStore
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_products)

    }
}
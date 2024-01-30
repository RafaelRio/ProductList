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

    override fun onBackPressed() {
        super.onBackPressed()

        //Uso esta forma porque cuando estoy en la segunda Activity al darle hacia atrás no quiero volver al Login
        finish()
        moveTaskToBack(true)
    }
}
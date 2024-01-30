package com.example.productlist.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.lifecycle.lifecycleScope
import com.example.productlist.R
import com.example.productlist.ui.list.ProductActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val remember = dataStore.data.map { preferences ->
            preferences[booleanPreferencesKey("remember")] ?: false
        }

        lifecycleScope.launch(Dispatchers.IO) {
            remember.collectLatest { remember ->
                if (remember) {
                    startActivity(Intent(this@LoginActivity, ProductActivity::class.java))
                }
            }
        }
    }
}
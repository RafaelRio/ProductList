package com.example.productlist.ui.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.productlist.databinding.FragmentLoginBinding
import com.example.productlist.ui.list.ProductActivity
import com.example.productlist.utils.onTextChanged
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginViewModel = ViewModelProvider(this)[LoginViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {
        binding.apply {
            etUsername.onTextChanged { username ->
                loginViewModel.setUserName(username)
            }
            etPassword.onTextChanged { password ->
                loginViewModel.setPassword(password)
            }

            login()
        }
    }

    private fun login() {
        binding.btnLogin.setOnClickListener {
            lifecycleScope.launch {
                val user = loginViewModel.login(
                    //Uso el trim para controlar que si alguien pone un espacio al principio o final no falle por eso
                    userName = loginViewModel.userName.value.trim(),
                    password = loginViewModel.password.value.trim(),
                )

                if (user == null) {
                    Toast.makeText(requireContext(), "Incorrect user", Toast.LENGTH_SHORT).show()
                } else {
                    saveValues(remember = binding.cbRemember.isChecked)
                    startActivity(Intent(requireContext(), ProductActivity::class.java))
                }
            }
        }
    }

    private suspend fun saveValues(remember: Boolean) {
        /*
            Uso Preferences DataStore para guardar de forma consistente si el
            usuario quiere mantener la sesión iniciada
         */
        requireContext().dataStore.edit { preferences ->
            preferences[booleanPreferencesKey("remember")] = remember
        }
    }
}

val Context.dataStore by preferencesDataStore(name = "USER_PREFERENCES_NAME")
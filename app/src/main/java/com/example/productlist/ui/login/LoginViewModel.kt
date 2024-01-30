package com.example.productlist.ui.login

import androidx.lifecycle.ViewModel
import com.example.productlist.data.UserResponse
import com.example.productlist.repository.ProductListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val plRepo: ProductListRepository,
) : ViewModel() {

    private val _userName = MutableStateFlow("")
    val userName = _userName.asStateFlow()

    private val _password = MutableStateFlow("")
    val password = _password.asStateFlow()

    fun setUserName(username: String) {
        _userName.value = username
    }

    fun setPassword(password: String) {
        _password.value = password
    }

    suspend fun login(userName: String, password: String) : UserResponse? {
        try {
            return plRepo.login(userName = userName, password = password)
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: HttpException) {
            e.printStackTrace()
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }
}
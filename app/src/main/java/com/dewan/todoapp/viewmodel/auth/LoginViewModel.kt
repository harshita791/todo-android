package com.dewan.todoapp.viewmodel.auth

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.dewan.todoapp.BuildConfig
import com.dewan.todoapp.model.local.AppPreferences
import com.dewan.todoapp.model.remote.Networking
import com.dewan.todoapp.model.remote.request.auth.LoginRequest
import com.dewan.todoapp.model.remote.response.auth.LoginResponse
import com.dewan.todoapp.model.repository.LoginRepository
import kotlinx.coroutines.Dispatchers

class LoginViewModel: ViewModel() {
    companion object{
        const val TAG = "LoginViewModel"
    }
    private val networkService = Networking.create(BuildConfig.BASE_URL)
    private lateinit var loginRepository: LoginRepository
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var appPreferences: AppPreferences

    fun init(context: Context){
        sharedPreferences = context.getSharedPreferences("com.dewan.todoapp.pref",Context.MODE_PRIVATE)
        appPreferences = AppPreferences(sharedPreferences)
        loginRepository = LoginRepository(networkService,appPreferences)
    }
    fun login(loginRequest: LoginRequest) = liveData(Dispatchers.IO) {
        val data=loginRepository.login(loginRequest)
        emit(data)
    }

    fun saveUserDetail(loginResponse: LoginResponse) = liveData(Dispatchers.IO) {
        val data = loginRepository.saveUserDetail(loginResponse)

        emit(data)
    }
}
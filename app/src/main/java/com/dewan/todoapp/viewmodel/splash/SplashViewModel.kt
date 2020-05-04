package com.dewan.todoapp.viewmodel.splash

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.dewan.todoapp.BuildConfig
import com.dewan.todoapp.model.local.AppPreferences
import com.dewan.todoapp.model.remote.Networking
import com.dewan.todoapp.model.repository.ValidateTokenRepository

class SplashViewModel: ViewModel() {
    companion object{
        const val TAG="SplashViewModel"
    }


    private lateinit var appPreferences: AppPreferences
    private lateinit var sharedPreferences: SharedPreferences

    private val networkService = Networking.create(BuildConfig.BASE_URL)
    private val validateTokenRepository = ValidateTokenRepository(networkService)
     var token = MutableLiveData<String>()

    fun init(context: Context){
        sharedPreferences = context.getSharedPreferences("com.dewan.todoapp.pref", Context.MODE_PRIVATE)
        appPreferences = AppPreferences(sharedPreferences)
        token.value = appPreferences.getAccessToken()
    }

    fun validateToken() = liveData {
        val data = validateTokenRepository.validateToken(token.value.toString())
        emit(data)
    }
}
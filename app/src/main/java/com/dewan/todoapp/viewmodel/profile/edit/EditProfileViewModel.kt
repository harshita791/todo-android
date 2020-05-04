package com.dewan.todoapp.viewmodel.profile.edit

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.dewan.todoapp.BuildConfig
import com.dewan.todoapp.model.local.AppPreferences
import com.dewan.todoapp.model.remote.Networking
import com.dewan.todoapp.model.remote.response.profile.EditProfileResponse
import com.dewan.todoapp.model.repository.UserProfileRepository
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class EditProfileViewModel(application: Application) : AndroidViewModel(application) {


    private val networkService = Networking.create(BuildConfig.BASE_URL)
    private var userProfileRepository: UserProfileRepository
    private var sharedPreferences: SharedPreferences
    private var appPreferences: AppPreferences
    private var token: String
    private var userId: String
    lateinit var editProfile: EditProfileResponse
    val loading: MutableLiveData<Boolean> = MutableLiveData()
    val nameField: MutableLiveData<String> = MutableLiveData()
    val emailField: MutableLiveData<String> = MutableLiveData()
    val imageField: MutableLiveData<File> = MutableLiveData()
    val bioField: MutableLiveData<String> = MutableLiveData()
    val imageUrl: MutableLiveData<String> = MutableLiveData()
    val profile: MutableLiveData<EditProfileResponse> = MutableLiveData()

    init {
        userProfileRepository = UserProfileRepository(networkService)
        sharedPreferences = application.getSharedPreferences("com.dewan.todoapp.pref",Context.MODE_PRIVATE)
        appPreferences = AppPreferences(sharedPreferences)
        token = appPreferences.getAccessToken().toString()
        userId = appPreferences.getUserId().toString()
    }

    fun editProfile() = liveData {
        loading.postValue(true)
        var profile_img: MultipartBody.Part? = null
        val id = RequestBody.create(MediaType.parse("multipart/form-data"),userId)
        val name = RequestBody.create(MediaType.parse("multipart/form-data"),nameField.value!!)
        val email = RequestBody.create(MediaType.parse("multipart/form-data"),emailField.value!!)
        if(imageField.value != null){
            val file: RequestBody = RequestBody.create(MediaType.parse("multipart/form-data"),imageField.value!!)
            profile_img = MultipartBody.Part.createFormData("profile_img",imageField.value?.name,file)
        }
        val bio = RequestBody.create(MediaType.parse("multipart/form-data"),bioField.value!!)

        val data = userProfileRepository.editProfile(token,id,name,email,profile_img,bio)
        if(data.code() == 200){
            profile.postValue(data.body())
        }
        emit(profile.value)

        loading.postValue(false)
    }
}

package com.dewan.todoapp.viewmodel.home

import android.content.Context
import android.content.SharedPreferences
import androidx.core.os.BuildCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.dewan.todoapp.BuildConfig
import com.dewan.todoapp.model.local.AppPreferences
import com.dewan.todoapp.model.remote.Networking
import com.dewan.todoapp.model.remote.response.todo.TaskResponse
import com.dewan.todoapp.model.repository.TaskRepository

class HomeViewModel : ViewModel() {
    companion object{
        const val TAG = "HomeViewModel"
    }
    private val networkService = Networking.create(BuildConfig.BASE_URL)
    private lateinit var taskRepository: TaskRepository
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var appPreferences: AppPreferences
    private lateinit var token: String
    val taskList: MutableLiveData<List<TaskResponse>> = MutableLiveData()

    fun init(context: Context) {
        sharedPreferences = context.getSharedPreferences("com.dewan.todoapp.pref", Context.MODE_PRIVATE)
        appPreferences = AppPreferences(sharedPreferences)
        taskRepository= TaskRepository(networkService)
        token = appPreferences.getAccessToken().toString()
    }

     fun getAllTask() = liveData {
         val data = taskRepository.getAllTask(token)
         if (data.code()==200){
             taskList.postValue(data.body())
         }
         emit(data)
     }
}

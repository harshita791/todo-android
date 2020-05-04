package com.dewan.todoapp.viewmodel.task

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.dewan.todoapp.BuildConfig
import com.dewan.todoapp.model.local.AppPreferences
import com.dewan.todoapp.model.remote.Networking
import com.dewan.todoapp.model.remote.request.todo.AddTaskRequest
import com.dewan.todoapp.model.repository.AddTaskRepository

class TaskViewModel : ViewModel() {

    companion object{
        const val TAG = "TaskViewModel"
    }

    private val networkService = Networking.create(BuildConfig.BASE_URL)
    private lateinit var appPreferences: AppPreferences
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var addTaskRepository: AddTaskRepository
    private var token: String = ""
    val user_id: MutableLiveData<Int> = MutableLiveData()
    val progress: MutableLiveData<Boolean> = MutableLiveData()

    fun init(context: Context){
        addTaskRepository = AddTaskRepository(networkService)
        sharedPreferences=context.getSharedPreferences("com.dewan.todoapp.pref",Context.MODE_PRIVATE)
        appPreferences = AppPreferences(sharedPreferences)
        token = appPreferences.getAccessToken().toString()
        user_id.value = appPreferences.getUserId()
    }

    fun addTask(addTaskRequest: AddTaskRequest) = liveData {

        progress.value=true
        val data=addTaskRepository.addTask(token,addTaskRequest)
        emit(data)

        progress.value = false
    }
}

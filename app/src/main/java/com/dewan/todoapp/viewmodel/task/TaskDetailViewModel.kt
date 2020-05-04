package com.dewan.todoapp.viewmodel.task

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dewan.todoapp.model.local.AppPreferences
import com.dewan.todoapp.model.repository.EditTaskRepository

class TaskDetailViewModel : ViewModel() {

    companion object{
        const val TAG ="TaskDetailViewModel"
    }

    private lateinit var appPreferences: AppPreferences
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var user_id: String

    val id: MutableLiveData<String> = MutableLiveData()
    val dateTime: MutableLiveData<String> = MutableLiveData()
    val title: MutableLiveData<String> = MutableLiveData()
    val body: MutableLiveData<String> = MutableLiveData()
    val status: MutableLiveData<String> = MutableLiveData()
    val userId: MutableLiveData<String> = MutableLiveData()
    val bgColor: MutableLiveData<String> = MutableLiveData()
    val isEditable: MutableLiveData<Boolean> = MutableLiveData()

    fun init(context: Context){
        sharedPreferences=context.getSharedPreferences("com.dewan.todoapp.pref", Context.MODE_PRIVATE)
        appPreferences = AppPreferences(sharedPreferences)
        user_id = appPreferences.getUserId().toString()
    }

    fun checkUserId(){
        isEditable.value = userId.value == user_id
    }
}

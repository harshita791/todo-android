package com.dewan.todoapp.model.remote.response.todo


import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.google.gson.annotations.SerializedName
data class TaskResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("user_id")
    val userId: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("body")
    val body: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("bg_color")
    var bg_color: Int
){
    companion object{
        @JvmStatic //create static function or getter and setter for property
        @BindingAdapter("viewBackground")//to bind view with textview
        //extension function in kotlin
        fun TextView.setBgColor(color: Int?){
            if(color!=null){
                this.setBackgroundResource(color)
            }
        }
    }
}
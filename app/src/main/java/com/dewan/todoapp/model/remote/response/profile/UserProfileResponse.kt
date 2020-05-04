package com.dewan.todoapp.model.remote.response.profile


import com.google.gson.annotations.SerializedName

data class UserProfileResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("profile_img")
    val profileImg: String,
    @SerializedName("profile_img_path")
    val profileImgPath: String,
    @SerializedName("bio")
    val bio: String
)
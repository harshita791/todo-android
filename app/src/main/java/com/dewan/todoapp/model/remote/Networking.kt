package com.dewan.todoapp.model.remote


import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object Networking {
    fun create(baseUrl: String) : NetworkService{

        // setting custom timeouts
        // setting custom timeouts

        val client: OkHttpClient.Builder = OkHttpClient.Builder()
        client.connectTimeout(100, TimeUnit.SECONDS)
        client.readTimeout(100, TimeUnit.SECONDS)
        client.writeTimeout(100, TimeUnit.SECONDS)
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client.build())
            .build()
            .create(NetworkService::class.java)
    }
}
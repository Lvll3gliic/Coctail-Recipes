package com.example.testtest.api

import com.example.testtest.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }
    val api: SimpleApi by lazy {
        retrofit.create(SimpleApi::class.java)
    }
}

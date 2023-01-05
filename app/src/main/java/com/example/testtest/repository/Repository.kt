package com.example.testtest.repository

import com.example.testtest.api.RetrofitInstance
import com.example.testtest.model.Data
import com.example.testtest.model.FullData
import retrofit2.Response

class Repository {

    suspend fun getPost(): Response<Data> {
        return RetrofitInstance.api.getPost()
    }

    suspend fun getFullInfoById(id:Int): Response<FullData> {
        return RetrofitInstance.api.getFullInfoById(id)
    }
    suspend fun getRandomDrink():Response<FullData>{
        return RetrofitInstance.api.getRandomDrink()
    }
    suspend fun getCocktailsByName(name: String):Response<FullData>{
        return RetrofitInstance.api.getCocktailsByName(name)
    }
}
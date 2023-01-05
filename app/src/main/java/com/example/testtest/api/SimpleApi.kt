package com.example.testtest.api

import com.example.testtest.model.Data
import com.example.testtest.model.FullData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SimpleApi {

    @GET("api/json/v1/1/list.php?c=list")
    suspend fun getPost(): Response<Data>


    @GET("api/json/v1/1/lookup.php?")
     suspend fun getFullInfoById(
        @Query("i") id: Int
    ): Response<FullData>

     @GET("api/json/v1/1/random.php")
     suspend fun getRandomDrink(): Response<FullData>

     @GET("api/json/v1/1/search.php?")
     suspend fun getCocktailsByName(
         @Query("s") name: String
     ): Response<FullData>

}


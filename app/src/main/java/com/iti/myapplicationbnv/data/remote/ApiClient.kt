package com.iti.myapplicationbnv.data.remote

import com.iti.myapplicationbnv.data.remote.MealApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private const val BASE_URL = "https://www.themealdb.com/api/json/v1/1/"

    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val apiService: MealApiService by lazy {
        retrofit.create(MealApiService::class.java)
    }
}
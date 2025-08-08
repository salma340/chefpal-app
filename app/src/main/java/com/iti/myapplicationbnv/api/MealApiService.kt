package com.iti.myapplicationbnv.api

import com.iti.myapplicationbnv.api.MealResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MealApiService {
    @GET("search.php")
    fun searchMeals(@Query("s") query: String): Call<MealResponse>
}
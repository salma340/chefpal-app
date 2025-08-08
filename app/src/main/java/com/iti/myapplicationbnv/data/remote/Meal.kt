package com.iti.myapplicationbnv.data.remote

import com.google.gson.annotations.SerializedName

data class Meal(
    @SerializedName("idMeal") val id: String,
    @SerializedName("strMeal") val name: String,
    @SerializedName("strMealThumb") val imageUrl: String,
    @SerializedName("strInstructions") val instructions: String,
    @SerializedName("strCategory")val category: String,
    @SerializedName("strYoutube") val youtubeUrl: String?,

    var isFavorite: Boolean = false
)
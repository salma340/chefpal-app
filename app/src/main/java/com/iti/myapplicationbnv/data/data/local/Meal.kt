package com.iti.myapplicationbnv.data.data.local

import com.google.gson.annotations.SerializedName

data class Meal(
    @SerializedName("idMeal") val id: String,
    @SerializedName("strMeal") val name: String,
    @SerializedName("strMealThumb") val imageUrl: String,
    @SerializedName("strInstructions") val instructions: String,
    @SerializedName("strCategory")val category: String,
    var isFavorite: Boolean = false
)
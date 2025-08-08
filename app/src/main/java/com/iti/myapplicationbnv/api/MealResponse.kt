package com.iti.myapplicationbnv.api

import com.iti.myapplicationbnv.data.data.local.Meal

data class MealResponse(
    val meals: List<Meal>?
)
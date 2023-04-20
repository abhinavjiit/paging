package com.example.upstox.feature.data.model

import com.google.gson.annotations.SerializedName

data class FeedMealsListModel(
    @SerializedName("meals")
    val meals: List<Meal> = emptyList()
)

data class Meal(
    @SerializedName("idMeal")
    val mealId: String = "",
    @SerializedName("strMeal")
    val mealTitle: String = "",
    @SerializedName("strMealThumb")
    val mealThumbnail: String = "",
    @SerializedName("strInstructions")
    val strInstructions: String,
    @SerializedName("strYoutube")
    val strYoutube: String
)

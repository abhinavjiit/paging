package com.example.upstox.feature.data.apiInterface

import com.example.upstox.feature.data.model.FeedMealsListModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("json/v1/1/filter.php")
    suspend fun fetchMealsList(@Query("c") dessert: String): Response<FeedMealsListModel>

    @GET("json/v1/1/lookup.php")
    suspend fun fetchItemDetail(@Query("i") itemId: String): Response<FeedMealsListModel>

}


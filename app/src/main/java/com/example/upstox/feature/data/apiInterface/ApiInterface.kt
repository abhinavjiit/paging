package com.example.upstox.feature.data.apiInterface

import com.example.upstox.feature.data.model.ResponseModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ApiInterface {

    @Headers("Content-Type: application/json")
    @GET("products")
    suspend fun fetchResponseModel(@Query("page") page: Int): Response<ResponseModel>

}


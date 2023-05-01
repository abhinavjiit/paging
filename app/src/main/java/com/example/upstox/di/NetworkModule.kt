package com.example.upstox.di

import com.example.upstox.base.ProductsDeserializer
import com.example.upstox.feature.data.apiInterface.ApiInterface
import com.example.upstox.feature.data.model.ResponseModel
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Singleton
    @Provides
    fun providesOkHttp(): OkHttpClient = OkHttpClient.Builder().build()

    @Singleton
    @Provides
    fun providesRetrofit(
        client: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit = Retrofit.Builder()
        .baseUrl("https://private-5fa9f9-sumitmarwhanykaa.apiary-mock.com/")
        .addConverterFactory(gsonConverterFactory)
        .client(client)
        .build()

    @Singleton
    @Provides
    fun gson(): Gson {
        val gsonBuilder = GsonBuilder()
        gsonBuilder.registerTypeAdapter(ResponseModel::class.java, ProductsDeserializer())
        return gsonBuilder.setLenient().create()
    }

    @Singleton
    @Provides
    fun gsonConverterFactory(gson: Gson): GsonConverterFactory = GsonConverterFactory.create(gson)

    @Singleton
    @Provides
    fun providesSessionEndApiService(retrofit: Retrofit): ApiInterface =
        retrofit.create(ApiInterface::class.java)

}
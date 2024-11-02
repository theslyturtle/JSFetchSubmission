package com.jonathansligh.fetchcodingexercise.apis

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object FetchHiringRetrofitInstance {
    private const val BASE_URL =" https://fetch-hiring.s3.amazonaws.com/"

    fun getInstance(): Retrofit {
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(
                GsonConverterFactory.create()).build()
    }
}
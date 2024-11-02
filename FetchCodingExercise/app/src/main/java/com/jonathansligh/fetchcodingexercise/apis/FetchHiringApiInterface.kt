package com.jonathansligh.fetchcodingexercise.apis

import com.jonathansligh.fetchcodingexercise.models.Item
import retrofit2.Call
import retrofit2.http.GET

interface FetchHiringApiInterface {
    @GET("hiring.json")
    fun getListData(): Call<List<Item>>
}
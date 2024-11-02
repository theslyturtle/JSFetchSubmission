package com.jonathan.slighfetchtakehome.data.api

import com.jonathan.slighfetchtakehome.data.models.FetchObject
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET

@Module
@InstallIn(SingletonComponent::class)
class FetchApiModule {
    @Provides
    fun provideFetchApi(retrofit: Retrofit): FetchApi {
        return retrofit.create(FetchApi::class.java)
    }
}

interface FetchApi {
    @GET("/hiring.json")
    suspend fun getList() : Response<List<FetchObject>>
}
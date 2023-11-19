package com.example.wanderquest.network

import com.example.wanderquest.data.Response
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Gauri Gadkari on 11/8/23.
 */

private const val BASE_URL = "https://maps.googleapis.com/maps/api/place/"

interface DestinationApiService {
    @GET("textsearch/json")
    suspend fun getDestination(
        @Query("query") query: String,
        @Query("key") apiKey: String
    ): Response
}

private val json = Json {
    ignoreUnknownKeys = true
}


private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
    .build()

object DestinationApi {
    val retrofitService : DestinationApiService by lazy {
        retrofit.create(DestinationApiService::class.java)
    }
}
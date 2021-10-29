package com.example.chucknorris.network

import com.example.chucknorris.model.JokesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface JokesApi {

    @GET("jokes/random/{count}/")
    suspend fun getJokes(
        @Path(value = "count") count: String,
    ):Response<JokesResponse>

}
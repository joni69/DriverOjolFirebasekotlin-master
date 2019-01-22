package com.udacoding.DriverintraojolfirebaseKotlin.network

import com.udacoding.DriverintraojolfirebaseKotlin.utama.home.model.ResultRoute
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {
    @GET("json")
    fun route(
        @Query("origin") origin: String,
        @Query("destination") destination: String,
        @Query("key") key: String
    ): retrofit2.Call<ResultRoute>
}
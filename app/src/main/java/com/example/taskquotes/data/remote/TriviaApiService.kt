package com.example.taskquotes.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

/** Definición de los endpoints REST consumidos por la app (Retrofit). */
interface TriviaApiService {

    @GET("api.php")
    suspend fun getTrivia(
        @Query("amount") amount: Int = 15,
        @Query("encode") encode: String = "base64"
    ): TriviaListResponse
}

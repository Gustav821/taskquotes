package com.example.taskquotes.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

/** Definición de los endpoints REST consumidos por la app (Retrofit). */
interface QuoteApiService {

    @GET("quotes")
    suspend fun getQuotes(@Query("limit") limit: Int = 15): QuoteListResponse
}

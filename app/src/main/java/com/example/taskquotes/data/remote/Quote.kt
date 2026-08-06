package com.example.taskquotes.data.remote

/** Modelos de datos devueltos por la API REST pública https://dummyjson.com/quotes */
data class QuoteDto(
    val id: Int,
    val quote: String,
    val author: String
)

data class QuoteListResponse(
    val quotes: List<QuoteDto>,
    val total: Int,
    val skip: Int,
    val limit: Int
)

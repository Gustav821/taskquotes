package com.example.taskquotes.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

/** Caché local (Room) de las frases obtenidas desde la API REST. */
@Entity(tableName = "quotes")
data class QuoteEntity(
    @PrimaryKey val id: Int,
    val quote: String,
    val author: String
)

package com.example.taskquotes.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

/** Caché local (Room) de las preguntas de trivia obtenidas desde la API REST. */
@Entity(tableName = "trivia")
data class TriviaEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val question: String,
    val correctAnswer: String,
    val category: String,
    val difficulty: String
)

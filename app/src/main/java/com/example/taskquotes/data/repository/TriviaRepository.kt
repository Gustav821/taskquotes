package com.example.taskquotes.data.repository

import android.util.Base64
import com.example.taskquotes.data.local.TriviaDao
import com.example.taskquotes.data.local.TriviaEntity
import com.example.taskquotes.data.remote.TriviaApiService
import com.example.taskquotes.util.Resource
import kotlinx.coroutines.flow.Flow

/**
 * Patrón offline-first: la UI observa siempre Room (getCachedTrivia). Al
 * refrescar, se llama a la API REST y el resultado se guarda en Room.
 */
class TriviaRepository(
    private val api: TriviaApiService,
    private val triviaDao: TriviaDao
) {

    fun getCachedTrivia(): Flow<List<TriviaEntity>> = triviaDao.getAllTrivia()

    suspend fun refreshTrivia(): Resource<Unit> {
        return try {
            val response = api.getTrivia(amount = 15)
            val entities = response.results.map {
                TriviaEntity(
                    question = it.question.decodeBase64(),
                    correctAnswer = it.correctAnswer.decodeBase64(),
                    category = it.category.decodeBase64(),
                    difficulty = it.difficulty.decodeBase64()
                )
            }
            triviaDao.clearTrivia()
            triviaDao.insertTrivia(entities)
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Error al obtener trivia desde la API")
        }
    }

    private fun String.decodeBase64(): String =
        String(Base64.decode(this, Base64.DEFAULT))
}

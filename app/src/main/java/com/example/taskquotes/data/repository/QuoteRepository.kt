package com.example.taskquotes.data.repository

import com.example.taskquotes.data.local.QuoteDao
import com.example.taskquotes.data.local.QuoteEntity
import com.example.taskquotes.data.remote.QuoteApiService
import com.example.taskquotes.util.Resource
import kotlinx.coroutines.flow.Flow

/**
 * Patrón offline-first: la UI observa siempre Room (getCachedQuotes). Al
 * refrescar, se llama a la API REST y el resultado se guarda en Room.
 */
class QuoteRepository(
    private val api: QuoteApiService,
    private val quoteDao: QuoteDao
) {

    fun getCachedQuotes(): Flow<List<QuoteEntity>> = quoteDao.getAllQuotes()

    suspend fun refreshQuotes(): Resource<Unit> {
        return try {
            val response = api.getQuotes(limit = 15)
            val entities = response.quotes.map { QuoteEntity(it.id, it.quote, it.author) }
            quoteDao.clearQuotes()
            quoteDao.insertQuotes(entities)
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Error al obtener frases desde la API")
        }
    }
}

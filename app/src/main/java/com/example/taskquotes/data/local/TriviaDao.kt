package com.example.taskquotes.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TriviaDao {

    @Query("SELECT * FROM trivia")
    fun getAllTrivia(): Flow<List<TriviaEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrivia(trivia: List<TriviaEntity>)

    @Query("DELETE FROM trivia")
    suspend fun clearTrivia()
}

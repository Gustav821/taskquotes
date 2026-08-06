package com.example.taskquotes.util

/** Envoltorio genérico para representar el resultado de una operación asíncrona. */
sealed class Resource<out T> {
    data class Success<T>(val data: T) : Resource<T>()
    data class Error(val message: String) : Resource<Nothing>()
    object Loading : Resource<Nothing>()
}

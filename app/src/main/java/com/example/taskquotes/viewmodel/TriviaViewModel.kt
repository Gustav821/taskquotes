package com.example.taskquotes.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskquotes.data.local.AppDatabase
import com.example.taskquotes.data.remote.RetrofitClient
import com.example.taskquotes.data.repository.TriviaRepository
import com.example.taskquotes.util.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TriviaViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = TriviaRepository(
        RetrofitClient.triviaApiService,
        AppDatabase.getInstance(application).triviaDao()
    )

    val trivia = repository.getCachedTrivia()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    init {
        refresh()
    }

    fun refresh() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            when (val result = repository.refreshTrivia()) {
                is Resource.Error -> _errorMessage.value = result.message
                else -> Unit
            }
            _isLoading.value = false
        }
    }
}

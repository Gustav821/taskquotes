package com.example.taskquotes.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskquotes.data.local.AppDatabase
import com.example.taskquotes.data.remote.RetrofitClient
import com.example.taskquotes.data.repository.QuoteRepository
import com.example.taskquotes.util.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class QuoteViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = QuoteRepository(
        RetrofitClient.quoteApiService,
        AppDatabase.getInstance(application).quoteDao()
    )

    val quotes = repository.getCachedQuotes()
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
            when (val result = repository.refreshQuotes()) {
                is Resource.Error -> _errorMessage.value = result.message
                else -> Unit
            }
            _isLoading.value = false
        }
    }
}

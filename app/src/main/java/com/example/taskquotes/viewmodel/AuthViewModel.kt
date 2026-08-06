package com.example.taskquotes.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskquotes.data.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class AuthUiState {
    object Idle : AuthUiState()
    object Loading : AuthUiState()
    data class Success(val uid: String) : AuthUiState()
    data class Error(val message: String) : AuthUiState()
}

class AuthViewModel(private val repository: AuthRepository = AuthRepository()) : ViewModel() {

    private val _uiState = MutableStateFlow<AuthUiState>(AuthUiState.Idle)
    val uiState: StateFlow<AuthUiState> = _uiState

    val isFirebaseAvailable: Boolean get() = repository.isFirebaseAvailable
    val currentUserId: String? get() = repository.currentUser?.uid

    fun signIn(email: String, password: String) {
        viewModelScope.launch {
            _uiState.value = AuthUiState.Loading
            repository.signIn(email, password)
                .onSuccess { _uiState.value = AuthUiState.Success(it.uid) }
                .onFailure { _uiState.value = AuthUiState.Error(it.message ?: "Error desconocido") }
        }
    }

    fun signUp(email: String, password: String) {
        viewModelScope.launch {
            _uiState.value = AuthUiState.Loading
            repository.signUp(email, password)
                .onSuccess { _uiState.value = AuthUiState.Success(it.uid) }
                .onFailure { _uiState.value = AuthUiState.Error(it.message ?: "Error desconocido") }
        }
    }

    /** Permite probar la app (Room + API REST) sin necesidad de configurar Firebase. */
    fun continueAsGuest() {
        _uiState.value = AuthUiState.Success("guest")
    }

    fun signOut() {
        repository.signOut()
        _uiState.value = AuthUiState.Idle
    }
}

package com.sd.storyteller.feature.auth

/**
 * Created by SDHOLPURIA on 02-11-2026.
 */

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sd.storyteller.data.repository.SupabaseAuthRepository
import com.sd.storyteller.data.repository.StoryRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AuthUiState(
    val isLoading: Boolean = false,
    val isAuthenticated: Boolean = false,
    val isEmailVerificationRequired: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: SupabaseAuthRepository,
    private val storyRepository: StoryRepositoryImpl
) : ViewModel() {

    private val _uiState = MutableStateFlow(AuthUiState(isLoading = true))
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    init {
        restoreSession()
    }

    private fun startStorySync() {
        viewModelScope.launch {
            storyRepository.syncStories()
        }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _uiState.value = AuthUiState(isLoading = true)

            repository.signIn(email, password)
                .onSuccess {
                    _uiState.value = AuthUiState(
                        isAuthenticated = true
                    )
                    startStorySync()
                }
                .onFailure {
                    _uiState.value = AuthUiState(
                        error = it.message ?: "Login failed"
                    )
                }
        }
    }

    fun register(email: String, password: String) {
        viewModelScope.launch {
            _uiState.value = AuthUiState(isLoading = true)

            repository.signUp(email, password)
                .onSuccess { response ->
                    val hasSession =
                        !response.accessToken.isNullOrBlank() &&
                                !response.refreshToken.isNullOrBlank()

                    _uiState.value = AuthUiState(
                        isAuthenticated = hasSession,
                        isEmailVerificationRequired = !hasSession
                    )

                    if (hasSession) {
                        startStorySync()
                    }
                }
                .onFailure {
                    _uiState.value = AuthUiState(
                        error = it.message ?: "Registration failed"
                    )
                }
        }
    }

    fun restoreSession() {
        viewModelScope.launch {
            _uiState.value = AuthUiState(isLoading = true)

            repository.restoreSession()
                .onSuccess {
                    _uiState.value = AuthUiState(
                        isAuthenticated = true
                    )
                    startStorySync()
                }
                .onFailure {
                    _uiState.value = AuthUiState()
                }
        }
    }

    fun logout(onLoggedOut: () -> Unit = {}) {
        viewModelScope.launch {
            _uiState.value = AuthUiState(isLoading = true)

            repository.signOut()

            _uiState.value = AuthUiState()
            onLoggedOut()
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}

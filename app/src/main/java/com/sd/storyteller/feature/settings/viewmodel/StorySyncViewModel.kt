package com.sd.storyteller.feature.settings.viewmodel

/**
 * Created by SDHOLPURIA on 02-11-2026.
 */
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sd.storyteller.data.repository.StoryRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class StorySyncUiState(
    val isSyncing: Boolean = false,
    val message: String? = null
)

@HiltViewModel
class StorySyncViewModel @Inject constructor(
    private val repository: StoryRepositoryImpl
) : ViewModel() {

    private val _uiState = MutableStateFlow(StorySyncUiState())
    val uiState = _uiState.asStateFlow()

    fun syncStories() {
        if (_uiState.value.isSyncing) return

        viewModelScope.launch {
            _uiState.update {
                it.copy(isSyncing = true, message = null)
            }

            val result = repository.syncStories()

            _uiState.update {
                it.copy(
                    isSyncing = false,
                    message = result.fold(
                        onSuccess = { it },
                        onFailure = { error ->
                            error.message ?: "Story sync failed."
                        }
                    )
                )
            }
        }
    }
}
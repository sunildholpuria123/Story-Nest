package com.sd.storyteller.core.designsystem.theme

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sd.storyteller.core.setting.AppThemeMode
import com.sd.storyteller.core.setting.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

@HiltViewModel
class AppThemeViewModel @Inject constructor(
    settingsRepository: SettingsRepository
) : ViewModel() {
    val themeMode = settingsRepository.appThemeMode.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = AppThemeMode.SYSTEM
    )
}

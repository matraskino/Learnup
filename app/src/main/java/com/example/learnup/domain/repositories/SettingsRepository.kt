package com.example.learnup.domain.repositories

import com.example.learnup.domain.models.AppSettings
import kotlinx.coroutines.flow.MutableStateFlow

interface SettingsRepository {

    fun saveAppSettings(settings: AppSettings)

    fun getAppSettings(): MutableStateFlow<AppSettings>

}
package com.example.learnup.domain

import com.example.learnup.data.repositories.SettingsRepositoryImpl
import com.example.learnup.domain.models.AppSettings
import com.example.learnup.domain.repositories.LearnRepository
import com.example.learnup.domain.repositories.SettingsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class AppSettingsHolder @Inject constructor(private val settingsRepository: SettingsRepository) {
    fun getAppSettings():MutableStateFlow<AppSettings>{
        return settingsRepository.getAppSettings()
    }
    fun saveAppSettings(settings: AppSettings){
        settingsRepository.saveAppSettings(settings)

    }



}
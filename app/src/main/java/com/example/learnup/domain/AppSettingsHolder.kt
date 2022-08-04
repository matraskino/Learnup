package com.example.learnup.domain

import com.example.learnup.domain.models.AppSettings
import kotlinx.coroutines.flow.MutableStateFlow

class AppSettingsHolder(private val learnRepository: LearnRepository) {
    fun getAppSettings():MutableStateFlow<AppSettings>{
        return learnRepository.getAppSettings()
    }
    fun saveAppSettings(settings: AppSettings){
        learnRepository.saveAppSettings(settings)
    }

}
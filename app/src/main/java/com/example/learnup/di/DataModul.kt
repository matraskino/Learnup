package com.example.learnup.di

import android.app.Application
import com.example.learnup.data.repositories.LearnRepositoryImpl
import com.example.learnup.data.repositories.SettingsRepositoryImpl
import com.example.learnup.domain.models.AppSettings
import com.example.learnup.domain.repositories.LearnRepository
import com.example.learnup.domain.repositories.SettingsRepository
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.flow.MutableStateFlow

@Module
class DataModule() {


    @Provides
    fun provideSettingsRepository(application:Application):SettingsRepository{
        return SettingsRepositoryImpl.getInstance(application)
    }

    @Provides
    fun provideLearnRepository(application:Application): LearnRepository {
        return LearnRepositoryImpl.getInstance(application)
    }

    @Provides
    fun provideSettings(settingsRepository: SettingsRepository): MutableStateFlow<AppSettings>{
        return settingsRepository.getAppSettings()
    }

}


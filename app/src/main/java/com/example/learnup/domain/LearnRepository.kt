package com.example.learnup.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

interface LearnRepository {
    suspend fun getAllLearnItems(): MutableStateFlow<MutableList<ItemLearn>>

    suspend fun getLearnItemById(id:Int):ItemLearn

    suspend fun saveLearnItem(item:ItemLearn)

    fun saveAppSettings(settings: AppSettings)

    fun getAppSettings():AppSettings
}
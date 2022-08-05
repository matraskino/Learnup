package com.example.learnup.domain

import com.example.learnup.domain.models.AppSettings
import com.example.learnup.domain.models.ItemLearn
import kotlinx.coroutines.flow.MutableStateFlow

interface LearnRepository {
    suspend fun getAllLearnItems(): MutableStateFlow<MutableList<ItemLearn>>

    suspend fun getLearnItemById(id:Int): ItemLearn

    suspend fun saveLearnItem(item: ItemLearn)

    suspend fun deleteLearnItem(id: Int)

    fun saveAppSettings(settings: AppSettings)

    fun getAppSettings(): MutableStateFlow<AppSettings>
}
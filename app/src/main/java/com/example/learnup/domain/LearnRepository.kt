package com.example.learnup.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

interface LearnRepository {
    suspend fun getAllLearnItems(): MutableStateFlow<List<ItemLearn>>

    suspend fun getLearnItemById(id:Int):ItemLearn

    fun updateLearnItem(item:ItemLearn)

    fun addLearnItem(item:ItemLearnToAdd)
}
package com.example.learnup.domain

import kotlinx.coroutines.flow.Flow

interface LearnRepository {
    suspend fun getAllLearnItems(): Flow<List<ItemLearn>>

    suspend fun getLearnItemById(id:Int):ItemLearn

    fun updateLearnItem(item:ItemLearn)

    fun addLearnItem(item:ItemLearnToAdd)
}
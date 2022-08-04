package com.example.learnup.domain


import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class GetAllLearnItemsUseCase(private val learnRepository: LearnRepository) {
    suspend fun execute(): MutableStateFlow<MutableList<ItemLearn>> {
    Log.d("test1","getAllLEarnItemsUseCate")
        return learnRepository.getAllLearnItems()
    }
}
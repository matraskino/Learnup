package com.example.learnup.domain


import com.example.learnup.data.LearnRepositoryImpl
import kotlinx.coroutines.flow.Flow

class GetAllLearnItemsUseCase(private val learnRepository: LearnRepositoryImpl) {
    suspend fun execute():Flow<List<ItemLearn>>{

        val p = learnRepository.getAllLearnItems()
        return p
    }
}
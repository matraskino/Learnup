package com.example.learnup.domain


import kotlinx.coroutines.flow.Flow

class GetAllLearnItemsUseCase(private val learnRepository: LearnRepository) {
    suspend fun execute(): Flow<List<ItemLearn>> {

        return learnRepository.getAllLearnItems()
    }
}
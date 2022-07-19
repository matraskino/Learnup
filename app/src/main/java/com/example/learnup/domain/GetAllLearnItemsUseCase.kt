package com.example.learnup.domain


import com.example.learnup.data.LearnRepository

class GetAllLearnItemsUseCase(private val learnRepository: LearnRepository) {
    suspend fun execute():List<ItemLearn>{
        return learnRepository.getAllLearnItems()
    }
}
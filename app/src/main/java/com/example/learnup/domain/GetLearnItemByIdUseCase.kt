package com.example.learnup.domain


import kotlinx.coroutines.flow.Flow

class GetLearnItemByIdUseCase(private val learnRepository: LearnRepository) {
    suspend fun execute(id:Int): ItemLearn {

        return learnRepository.getLearnItemById(id)
    }
}
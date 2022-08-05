package com.example.learnup.domain


import com.example.learnup.domain.models.ItemLearn

class DeleteLearnItemByIdUseCase(private val learnRepository: LearnRepository) {
    suspend fun execute(id:Int) {
        learnRepository.deleteLearnItem(id)
    }
}
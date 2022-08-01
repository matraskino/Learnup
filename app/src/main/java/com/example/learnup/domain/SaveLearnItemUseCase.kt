package com.example.learnup.domain



class SaveLearnItemUseCase(private val learnRepository: LearnRepository) {
    suspend fun execute(learnItem:ItemLearn) {

        return learnRepository.saveLearnItem(learnItem)
    }
}
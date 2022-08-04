package com.example.learnup.domain

import com.example.learnup.domain.models.ItemLearn


class SaveLearnItemUseCase(private val learnRepository: LearnRepository) {
    suspend fun execute(learnItem: ItemLearn) {

        return learnRepository.saveLearnItem(learnItem)
    }
}
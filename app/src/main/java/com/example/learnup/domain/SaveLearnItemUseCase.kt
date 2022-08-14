package com.example.learnup.domain

import com.example.learnup.domain.models.ItemLearn
import com.example.learnup.domain.repositories.LearnRepository
import javax.inject.Inject


class SaveLearnItemUseCase @Inject constructor(private val learnRepository: LearnRepository) {
    suspend fun execute(learnItem: ItemLearn) {

        return learnRepository.saveLearnItem(learnItem)
    }
}
package com.example.learnup.domain


import com.example.learnup.domain.models.ItemLearn
import com.example.learnup.domain.repositories.LearnRepository
import javax.inject.Inject

class GetLearnItemByIdUseCase @Inject constructor(private val learnRepository: LearnRepository) {
    suspend fun execute(id:Int): ItemLearn {

        return learnRepository.getLearnItemById(id)
    }
}
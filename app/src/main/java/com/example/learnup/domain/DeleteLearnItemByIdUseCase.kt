package com.example.learnup.domain


import com.example.learnup.domain.repositories.LearnRepository
import javax.inject.Inject

class DeleteLearnItemByIdUseCase @Inject constructor(private val learnRepository: LearnRepository) {
    suspend fun execute(id:Int) {
        learnRepository.deleteLearnItem(id)
    }
}
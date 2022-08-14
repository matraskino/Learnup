package com.example.learnup.domain


import android.util.Log
import com.example.learnup.domain.models.ItemLearn
import com.example.learnup.domain.repositories.LearnRepository
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class GetAllLearnItemsUseCase @Inject constructor(private val learnRepository: LearnRepository) {
    suspend fun execute(): MutableStateFlow<MutableList<ItemLearn>> {
    Log.d("test1","getAllLEarnItemsUseCate")
        return learnRepository.getAllLearnItems()
    }
}
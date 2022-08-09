package com.example.learnup.domain.usecases

import com.example.learnup.network.api.ApiService
import com.example.learnup.data.model.LearnItemData
import com.example.learnup.domain.usecaseImpl.GetAllLearnItemsUseCaseImpl
import com.example.learnup.network.data.LearnItemDataClass
import javax.inject.Inject

class GetAllLearnItemsNetworkUseCase @Inject constructor(
    private val apiService: ApiService
) : GetAllLearnItemsUseCaseImpl {

    override suspend fun getAll(): List<LearnItemDataClass> {
        return apiService.getAllLines()
    }

}
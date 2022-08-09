package com.example.learnup.domain.usecaseImpl

import com.example.learnup.data.model.LearnItemData
import com.example.learnup.network.data.LearnItemDataClass

interface GetAllLearnItemsUseCaseImpl {

    suspend fun getAll(): List<LearnItemDataClass>

}
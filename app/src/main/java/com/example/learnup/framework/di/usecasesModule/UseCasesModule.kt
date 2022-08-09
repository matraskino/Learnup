package com.example.learnup.framework.di.usecasesModule

import com.example.learnup.domain.usecaseImpl.GetAllLearnItemsUseCaseImpl
import com.example.learnup.domain.usecases.GetAllLearnItemsNetworkUseCase
import dagger.Binds
import dagger.Module
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
interface UseCasesModule {

    @Binds
    @Reusable
    fun bindGetLearnItemByIdNetworkUseCase(useCaseAll: GetAllLearnItemsNetworkUseCase): GetAllLearnItemsUseCaseImpl

}
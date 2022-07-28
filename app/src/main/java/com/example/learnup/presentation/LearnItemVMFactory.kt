package com.example.learnup.presentation

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.learnup.data.LearnRepositoryImpl
import com.example.learnup.domain.GetAllLearnItemsUseCase
import com.example.learnup.domain.GetLearnItemByIdUseCase

class LearnItemVMFactory(context:Context):ViewModelProvider.Factory {
    private val repository = LearnRepositoryImpl.getInstance(context)
    private val getAllLearnItemsUseCase  by lazy(LazyThreadSafetyMode.NONE) {
        //GetAllLearnItemsUseCase(LearnRepository(context))

        GetAllLearnItemsUseCase(repository)
    }

    private val getLearnItemByIdUseCase  by lazy(LazyThreadSafetyMode.NONE) {

        GetLearnItemByIdUseCase(repository)
    }
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LearnItemViewModel(getAllLearnItemsUseCase,getLearnItemByIdUseCase) as T
    }
}
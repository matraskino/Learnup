package com.example.learnup.presentation

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

import com.example.learnup.data.LearnRepository
import com.example.learnup.domain.GetAllLearnItemsUseCase

class MainViewModelFactory(context:Context):ViewModelProvider.Factory {



    private val getAllLearnItemsUseCase  by lazy(LazyThreadSafetyMode.NONE) {   GetAllLearnItemsUseCase(LearnRepository()) }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(getAllLearnItemsUseCase) as T
    }
}
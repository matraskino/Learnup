package com.example.learnup.presentation.vmFactories

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.learnup.data.repositories.LearnRepositoryImpl
import com.example.learnup.data.repositories.SettingsRepositoryImpl
import com.example.learnup.domain.AppSettingsHolder
import com.example.learnup.domain.GetAllLearnItemsUseCase
import com.example.learnup.domain.GetLearnItemByIdUseCase
import com.example.learnup.domain.SaveLearnItemUseCase
import com.example.learnup.presentation.viewModels.MainViewModel
import javax.inject.Inject

class MainViewModelFactory @Inject constructor(private val application: Application):ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(application) as T
    }
}
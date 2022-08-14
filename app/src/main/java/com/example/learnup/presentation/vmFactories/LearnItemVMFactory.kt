package com.example.learnup.presentation.vmFactories

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.learnup.data.repositories.LearnRepositoryImpl
import com.example.learnup.data.repositories.SettingsRepositoryImpl
import com.example.learnup.domain.*
import com.example.learnup.presentation.viewModels.LearnItemViewModel

class LearnItemVMFactory(private val application: Application):ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LearnItemViewModel(application) as T
    }
}
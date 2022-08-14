package com.example.learnup.presentation.vmFactories

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.learnup.data.repositories.LearnRepositoryImpl
import com.example.learnup.data.repositories.SettingsRepositoryImpl
import com.example.learnup.domain.GetLearnItemByIdUseCase
import com.example.learnup.domain.SaveLearnItemUseCase
import com.example.learnup.presentation.viewModels.EditLearnItemViewModel

class EditLearnItemVMFactory(private val application: Application) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return EditLearnItemViewModel(application) as T
    }
}
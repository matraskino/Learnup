package com.example.learnup.presentation.vmFactories

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.learnup.data.LearnRepositoryImpl
import com.example.learnup.domain.GetLearnItemByIdUseCase
import com.example.learnup.domain.SaveLearnItemUseCase
import com.example.learnup.presentation.viewModels.EditLearnItemViewModel

class EditLearnItemVMFactory(application: Application):ViewModelProvider.Factory {
    private val repository = LearnRepositoryImpl.getInstance(application)
    private val saveLearnItemUseCase  by lazy(LazyThreadSafetyMode.NONE) {
        SaveLearnItemUseCase(repository)
    }

    private val getLearnItemByIdUseCase  by lazy(LazyThreadSafetyMode.NONE) {

        GetLearnItemByIdUseCase(repository)
    }
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return EditLearnItemViewModel(saveLearnItemUseCase,getLearnItemByIdUseCase) as T
    }
}
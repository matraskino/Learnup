package com.example.learnup.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.learnup.domain.usecaseImpl.GetAllLearnItemsUseCaseImpl
import com.example.learnup.network.data.LearnItemDataClass
import com.example.learnup.presentation.utils.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getAllLearnItemsUseCaseImpl: GetAllLearnItemsUseCaseImpl,
) : BaseViewModel() {

    private val _allInfoData = MutableLiveData<List<LearnItemDataClass>?>()
    val allInfoData: LiveData<List<LearnItemDataClass>?>
        get() = _allInfoData

    fun getAllListInfo() {
        scope.launch {
            _allInfoData.postValue(getAllLearnItemsUseCaseImpl.getAll())
        }
    }

}
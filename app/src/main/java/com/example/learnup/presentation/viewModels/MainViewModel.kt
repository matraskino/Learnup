package com.example.learnup.presentation.viewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.learnup.domain.GetAllLearnItemsUseCase
import com.example.learnup.domain.GetLearnItemByIdUseCase
import com.example.learnup.domain.ItemLearn
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*


class MainViewModel(

    private val getAllLearnItemsUseCase: GetAllLearnItemsUseCase,
    private val getLearnItemByIdUseCase: GetLearnItemByIdUseCase
) : ViewModel() {

    var dataToRecycl = MutableLiveData<MutableList<ItemLearn>>()
    var dataToRecyclStateFlow = MutableStateFlow(mutableListOf(ItemLearn()))

    init {
        testFillLiveData()
    }

    fun fillArr() {
        viewModelScope.launch {

            dataToRecyclStateFlow =
                withContext(Dispatchers.IO) {
                    getAllLearnItemsUseCase.execute()
                }


            dataToRecyclStateFlow.collectLatest {
                withContext(Dispatchers.Main) {
                    dataToRecycl.postValue(it)
                }
            }
        }
    }

    fun testFillLiveData() {
        dataToRecycl.value = mutableListOf(
            ItemLearn(
                1,
                "test",
                "test2",
                false,
                "google.com.ua",
                "extra description about nothing"
            ),
            ItemLearn(1, "test", "test2", false, "google.com", "test additional decrtiption")
        )
    }
}
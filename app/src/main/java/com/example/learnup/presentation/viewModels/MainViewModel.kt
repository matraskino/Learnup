package com.example.learnup.presentation.viewModels

import android.app.Application
import android.util.Log
import android.view.MenuItem
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.learnup.R
import com.example.learnup.domain.AppSettingsHolder
import com.example.learnup.domain.GetAllLearnItemsUseCase
import com.example.learnup.domain.GetLearnItemByIdUseCase
import com.example.learnup.domain.SaveLearnItemUseCase
import com.example.learnup.domain.models.AppSettings
import com.example.learnup.domain.models.ItemLearn
import com.example.learnup.presentation.App
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject


class MainViewModel @Inject constructor(
    application: Application
) : ViewModel() {

    @Inject
    lateinit var getAllLearnItemsUseCase: GetAllLearnItemsUseCase
    @Inject
    lateinit var getLearnItemByIdUseCase: GetLearnItemByIdUseCase
    @Inject
    lateinit var appSettingsHolder: AppSettingsHolder
    @Inject
    lateinit var saveLearnItemUseCase : SaveLearnItemUseCase

    @Inject
    lateinit var settings:MutableStateFlow<AppSettings>

    var dataToRecycl = MutableLiveData<MutableList<ItemLearn>>()
    var dataToRecyclStateFlow = MutableStateFlow(mutableListOf(ItemLearn()))

    init {
        testFillLiveData()
        (application as App).applicationComponent.inject(this)
    }

    fun fillArr() {
        viewModelScope.launch {

            dataToRecyclStateFlow =
                withContext(Dispatchers.IO) {
                    getAllLearnItemsUseCase.execute()
                }


            dataToRecyclStateFlow.collectLatest {
                withContext(Dispatchers.Main) {
                    val value = if(settings.value.wordsFilter){
                        it.filter { it.isChecked } as MutableList
                    } else{ it }
                    dataToRecycl.postValue(value)
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

    fun observSettings(){
        viewModelScope.launch {
            settings.collectLatest {
                implementSettings()
                Log.d("test1", "observSettings")
            }
        }
    }

    fun implementSettings(){
        val it = dataToRecyclStateFlow.value
        val value = if(settings.value.wordsFilter){
            it.filter { it.isChecked } as MutableList
        } else{ it }
        dataToRecycl.postValue(value)
    }

    fun changeItemChack(itemLearn: ItemLearn, status:Boolean,position:Int){
        val updatedItem = ItemLearn(itemLearn.id,itemLearn.learnWord,itemLearn.description,status,itemLearn.link,itemLearn.extraDescription)
        dataToRecyclStateFlow.value.add(position,updatedItem)
        viewModelScope.launch(Dispatchers.IO) {
            saveLearnItemUseCase.execute(updatedItem)
        }
    }
}
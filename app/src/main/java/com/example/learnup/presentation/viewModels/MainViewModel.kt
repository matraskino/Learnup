package com.example.learnup.presentation.viewModels

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
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*


class MainViewModel(

    private val getAllLearnItemsUseCase: GetAllLearnItemsUseCase,
    private val getLearnItemByIdUseCase: GetLearnItemByIdUseCase,
    private val appSettingsHolder: AppSettingsHolder,
    private val saveLearnItemUseCase : SaveLearnItemUseCase

) : ViewModel() {

    var dataToRecycl = MutableLiveData<MutableList<ItemLearn>>()
    var dataToRecyclStateFlow = MutableStateFlow(mutableListOf(ItemLearn()))
    var settings = appSettingsHolder.getAppSettings()

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

//    fun onSettingsChanged(item: MenuItem){
//        val replaceLearnMod = when (settings.wayOfLearn){
//            AppSettings.SHOW_WORD -> AppSettings.SHOW_DESCRIPTION
//            AppSettings.SHOW_DESCRIPTION -> AppSettings.SHOW_WORD
//            else -> AppSettings.SHOW_WORD
//        }
//        val getNextLearnMod = when (settings.wayOfLearn){
//            AppSettings.SHOW_WORD -> AppSettings.SHOW_ALL
//            AppSettings.SHOW_DESCRIPTION -> AppSettings.SHOW_ALL
//            else -> AppSettings.SHOW_WORD
//        }
//
//        when (item.itemId) {
//            R.id.setting_replace -> {
//                appSettingsHolder.saveAppSettings(AppSettings(replaceLearnMod,settings.wordsFilter))
//            }
//            R.id.setting_show_all -> {
//                appSettingsHolder.saveAppSettings(AppSettings(settings.wayOfLearn,!settings.wordsFilter))
//            }
//            R.id.setting_learn_mod -> {
//                appSettingsHolder.saveAppSettings(AppSettings(getNextLearnMod,settings.wordsFilter))
//            }
//            else -> {}
//        }
//        implementSettings()
//
//    }

}
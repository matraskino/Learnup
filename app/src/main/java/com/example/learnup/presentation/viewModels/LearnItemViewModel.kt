package com.example.learnup.presentation.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.learnup.domain.*
import com.example.learnup.domain.models.ItemLearn
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LearnItemViewModel(
    private val getAllLearnItemsUseCase:GetAllLearnItemsUseCase,
    private val getLearnItemByIdUseCase:GetLearnItemByIdUseCase,
    private val getLearnItemIdUseCase:GetLearnItemIdUseCase,
    private val appSettingsHolder: AppSettingsHolder,
    private val deleteLearnItemByIdUseCase:DeleteLearnItemByIdUseCase
    ):ViewModel() {
    private val _itemView = MutableLiveData<ItemLearn>()
    var prevId:MutableList<Int> = mutableListOf()
    val DEFAULT_ID = 1
    val settings = appSettingsHolder.getAppSettings()
    val itemView:LiveData<ItemLearn>
        get() = _itemView

    fun getLearnItem(id:Int){
        viewModelScope.launch {
            val item: ItemLearn = getLearnItemByIdUseCase.execute(id)

            withContext(Dispatchers.Main){
                _itemView.value = item
            }
        }
    }
    fun nextItem(){
        val currentId = _itemView.value!!.id
        val nextId = getLearnItemIdUseCase.getNextLearnItem(currentId,settings.value.wordsFilter,true)
//        prevId.add(currentId)
        getLearnItem(nextId)
    }

    fun prevItem(){
        var id = getLearnItemIdUseCase.getPreviosLearnItem(_itemView.value!!.id,settings.value.wordsFilter,true)
        getLearnItem(id)
    }

    fun deleteCurrentItem(){
        viewModelScope.launch(Dispatchers.IO) {
            deleteLearnItemByIdUseCase.execute(itemView.value!!.id)
            getLearnItemIdUseCase.prevId.remove(itemView.value!!.id)
            nextItem()
        }
    }

}
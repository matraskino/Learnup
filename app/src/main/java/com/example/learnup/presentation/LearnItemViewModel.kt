package com.example.learnup.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.learnup.domain.GetAllLearnItemsUseCase
import com.example.learnup.domain.GetLearnItemByIdUseCase
import com.example.learnup.domain.ItemLearn
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LearnItemViewModel(
    private val getAllLearnItemsUseCase:GetAllLearnItemsUseCase,
    private val getLearnItemByIdUseCase:GetLearnItemByIdUseCase):ViewModel() {
    private val _itemView = MutableLiveData<ItemLearn>()
    val itemView:LiveData<ItemLearn>
        get() = _itemView

    fun getLearnItem(id:Int){
        viewModelScope.launch {
            val item:ItemLearn = getLearnItemByIdUseCase.execute(id)

            withContext(Dispatchers.Main){
                _itemView.value = item
            }
        }
    }
    fun nextItem(){
        //додати щод ящо по даному id нічого немає, передати Item з id 1
        getLearnItem(_itemView.value!!.id+1)
    }

    fun prevItem(){
        //додати щод ящо ID = 0 то повернути останню позицію
        getLearnItem(_itemView.value!!.id-1)
    }

}
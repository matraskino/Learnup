package com.example.learnup.presentation.learn_item

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.learnup.data.model.LearnItemData
import com.example.learnup.domain.ItemLearn
import com.example.learnup.domain.usecaseImpl.GetAllLearnItemsUseCaseImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LearnItemViewModel @Inject constructor(
    private val getAllLearnItemsUseCaseImpl: GetAllLearnItemsUseCaseImpl,
    ) : ViewModel() {
    private val _itemView = MutableLiveData<ItemLearn>()
    val itemView: LiveData<ItemLearn>
        get() = _itemView

    private val _listIemView = MutableLiveData<List<LearnItemData>>()
    val listIemView: LiveData<List<LearnItemData>>
        get() = _listIemView



    fun getLearnItem(id: Int) {
//        viewModelScope.launch {
//            val item: ItemLearn = getLearnItemByIdUseCase.execute(id)
//
//            withContext(Dispatchers.Main) {
//                _itemView.value = item
//            }
//        }
    }

    fun nextItem() {
        //додати щод ящо по даному id нічого немає, передати Item з id 1
        getLearnItem(_itemView.value!!.id + 1)
    }

    fun prevItem() {
        //додати щод ящо ID = 0 то повернути останню позицію
        getLearnItem(_itemView.value!!.id - 1)
    }

//    fun getAllLearnItem(){
//        viewModelScope.launch {
//            _listIemView.value = getAllLearnItemsUseCaseImpl.getAll()
//        }
//    }
}
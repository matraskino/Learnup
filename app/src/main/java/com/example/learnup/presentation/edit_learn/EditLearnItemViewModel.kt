package com.example.learnup.presentation.edit_learn

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.example.learnup.domain.ItemLearn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class EditLearnItemViewModel @Inject constructor(
) : ViewModel() {
    private val _itemView = MutableLiveData<ItemLearn>()
    val itemView: LiveData<ItemLearn>
        get() = _itemView

    fun getLearnItem(id: Int) {
        viewModelScope.launch {
//            val item: ItemLearn = getLearnItemByIdUseCase.execute(id)
//
//            withContext(Dispatchers.Main) {
//                _itemView.value = item
//            }
        }
    }
//
//    fun saveLearnItem(
//        learnWord: String,
//        description: String,
//        link: String,
//        extraDescription: String,
//    ) {
//        val learnItem = ItemLearn(learnWord = learnWord,
//            description = description,
//            link = link,
//            extraDescription = extraDescription)
//        viewModelScope.launch {
//            saveLearnItemUseCase.execute(learnItem)
//        }
//    }

}
package com.example.learnup.presentation.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.learnup.domain.GetAllLearnItemsUseCase
import com.example.learnup.domain.GetLearnItemByIdUseCase
import com.example.learnup.domain.ItemLearn
import com.example.learnup.domain.SaveLearnItemUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EditLearnItemViewModel(
    private val saveLearnItemUseCase: SaveLearnItemUseCase,
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
    fun saveLearnItem(learnWord:String,description:String,link:String, extraDescription:String){
        val learnItem = ItemLearn(learnWord = learnWord, description = description, link = link, extraDescription = extraDescription)
        val coroutineExceptionHandler = CoroutineExceptionHandler{_, throwable ->
            throwable.printStackTrace()
        }
        viewModelScope.launch(Dispatchers.Default + coroutineExceptionHandler) {
            Log.d("test1","lanch from saveLoarnItem in EditLearItemViewModel")
            saveLearnItemUseCase.execute(learnItem)

        }
    }

}
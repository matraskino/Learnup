package com.example.learnup.presentation.viewModels

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.learnup.domain.GetLearnItemByIdUseCase
import com.example.learnup.domain.models.ItemLearn
import com.example.learnup.domain.SaveLearnItemUseCase
import com.example.learnup.presentation.App
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class EditLearnItemViewModel(application: Application,
):ViewModel() {


    @Inject
    lateinit var saveLearnItemUseCase: SaveLearnItemUseCase

    @Inject
    lateinit var getLearnItemByIdUseCase:GetLearnItemByIdUseCase

    init {
        (application as App).applicationComponent.inject(this)
    }


    private val _itemView = MutableLiveData<ItemLearn>()
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
    fun saveLearnItem(id:Int, learnWord:String,description:String,link:String, extraDescription:String){
        val learnItem = ItemLearn(id = id, learnWord = learnWord, description = description, link = link, extraDescription = extraDescription)
        val coroutineExceptionHandler = CoroutineExceptionHandler{_, throwable ->
            throwable.printStackTrace()
        }
        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            Log.d("test1","lanch from saveLoarnItem in EditLearItemViewModel")
            saveLearnItemUseCase.execute(learnItem)

        }
    }

}
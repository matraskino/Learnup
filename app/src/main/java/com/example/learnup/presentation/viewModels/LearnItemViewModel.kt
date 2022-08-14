package com.example.learnup.presentation.viewModels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.learnup.di.AppModule
import com.example.learnup.di.ApplicationComponent
import com.example.learnup.di.DaggerApplicationComponent
import com.example.learnup.domain.*
import com.example.learnup.domain.models.ItemLearn
import com.example.learnup.presentation.App
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LearnItemViewModel(
    private val application:Application
    ):ViewModel() {

    init {
        (application as App).applicationComponent.inject(this)
    }
    @Inject
    lateinit var getLearnItemByIdUseCase:GetLearnItemByIdUseCase
    @Inject
    lateinit var getLearnItemIdUseCase:GetLearnItemIdUseCase
    @Inject
    lateinit var appSettingsHolder: AppSettingsHolder
    @Inject
    lateinit var deleteLearnItemByIdUseCase:DeleteLearnItemByIdUseCase

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
        prevId.add(currentId)
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
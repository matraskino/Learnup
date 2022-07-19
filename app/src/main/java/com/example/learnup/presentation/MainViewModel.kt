package com.example.learnup.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.learnup.domain.GetAllLearnItemsUseCase
import com.example.learnup.domain.ItemLearn


class MainViewModel(

    private val getAllLearnItemsUseCase: GetAllLearnItemsUseCase
): ViewModel () {
/*
    */


    var dataToRecycl = MutableLiveData<List<ItemLearn>>()


//    fun fillArr(){
//
//        dataToRecycl.value = getAllLearnItemsUseCase.execute()
//
//    }

    fun testFillLiveData(){
        dataToRecycl.value = mutableListOf(ItemLearn(1,"test","test2",false),
            ItemLearn(1,"test","test2",false))
    }



    fun fillLiveData(list:List<ItemLearn>){

        dataToRecycl.value = list

    }






}
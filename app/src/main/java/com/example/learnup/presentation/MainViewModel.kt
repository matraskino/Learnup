package com.example.learnup.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.learnup.domain.GetAllLearnItemsUseCase
import com.example.learnup.domain.ItemLearn
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow


class MainViewModel(

    private val getAllLearnItemsUseCase: GetAllLearnItemsUseCase
): ViewModel () {
/*
    */


    var dataToRecycl = MutableLiveData<List<ItemLearn>>()





    fun fillArr(){
//        dataToRecycl.value = mutableListOf(ItemLearn(1,"test from fill arr", "defenition",false),ItemLearn(1,"test from fill arr", "defenition",false))
        viewModelScope.launch {

            val newData:Flow<List<ItemLearn>> =
                withContext(Dispatchers.IO){

                getAllLearnItemsUseCase.execute()
                }
            newData.collect{

            withContext(Dispatchers.Main){
                println(it.toString())
                dataToRecycl.postValue(it)
            }
            }
//        runBlocking {
//            withContext(Dispatchers.IO){
//
//            }
//            val deferred: Deferred<List<ItemLearn>> = async {
//                getAllLearnItemsUseCase.execute()
//            }
//            println("waiting...")
//
//
//                val dataReq = deferred.await()
//            withContext(Dispatchers.Main) {
//                dataToRecycl.value = dataReq
//                println(dataReq)
 //           }
        }

    }

    fun testFillLiveData(){
        dataToRecycl.value = mutableListOf(ItemLearn(1,"test","test2",false, "google.com.ua", "extra description about nothing"),
            ItemLearn(1,"test","test2",false,"google.com","test additional decrtiption"))
    }



    fun fillLiveData(list:List<ItemLearn>){

        dataToRecycl.value = list

    }






}
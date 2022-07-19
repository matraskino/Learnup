package com.example.learnup.data




import com.example.learnup.domain.ItemLearn
import kotlinx.coroutines.*





class LearnRepository(){


    suspend fun getAllLearnItems(): List<ItemLearn> {
            delay(1000)
           return listOf(ItemLearn(1,"test1","test descr",false),
               ItemLearn(1,"test1","test descr",false),
               ItemLearn(1,"test1","test descr",false))
        }







}




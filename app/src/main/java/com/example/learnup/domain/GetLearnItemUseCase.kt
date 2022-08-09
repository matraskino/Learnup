package com.example.learnup.domain


import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class GetLearnItemIdUseCase(private val learnRepository: LearnRepository) {
    val RANDOM = 0
    val NEXT = 1
    val PREVIOS = 2
    var prevId:MutableList<Int> = mutableListOf()
    val DEFAULT_ID = 1
    val allItemsMutableStateFlow = runBlocking {  withContext(Dispatchers.IO){ learnRepository.getAllLearnItems() }}


    fun getNextLearnItem(currentId:Int, checked2:Boolean, random2:Boolean):Int{
        val settings = learnRepository.getAppSettings().value
        val checked = settings.wordsFilter
        val random = settings.random
        val derection:Int = if(random){RANDOM}else{NEXT}
        prevId.add(currentId)
        return getLearnItem(currentId,checked, derection)
    }

    fun getPreviosLearnItem(currentId:Int, checked1:Boolean, random1:Boolean):Int{
        val settings = learnRepository.getAppSettings().value
        val checked = settings.wordsFilter
        val random = settings.random
        var id = DEFAULT_ID
        val derection:Int = if(random){RANDOM}else{PREVIOS}
        if(prevId.size == 0){
            id = getLearnItem(currentId,checked, derection)
        } else {
            id = prevId.last()
            prevId.removeLast()
        }
            return id
    }

    private fun getLearnItem(currentItemId:Int,checked:Boolean ,derection:Int):Int{
        val baseIdList:List<Int> = when(checked){
            true ->allItemsMutableStateFlow.value.filter { it.isChecked }.map { it.id }.filter { it!=currentItemId }
            false ->allItemsMutableStateFlow.value.map { it.id }.filter { it!=currentItemId }
        }
        when (derection){
            RANDOM -> return baseIdList.random()

            NEXT -> return baseIdList.firstOrNull { it > currentItemId } ?: baseIdList[0]

            PREVIOS -> return baseIdList.lastOrNull { it < currentItemId } ?: baseIdList.last()

        }
        return 1
    }
}
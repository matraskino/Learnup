package com.example.learnup.data




import android.app.Application
import android.content.Context
import android.util.Log
import com.example.learnup.data.api.ApiStorage
import com.example.learnup.data.local.AppDataBase
import com.example.learnup.data.local.DataBaseHandler
import com.example.learnup.data.model.LearnItemData
import com.example.learnup.domain.ItemLearn
import com.example.learnup.domain.ItemLearnToAdd
import com.example.learnup.domain.LearnRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*


class LearnRepositoryImpl(application: Application):LearnRepository{

    val dbRoom = AppDataBase.getInstance(application).dao()
    val db = AppDataBase.getInstance(application).dao()//DataBaseHandler
    var mutableStateFlow: MutableStateFlow<List<ItemLearn>> = MutableStateFlow(listOf<ItemLearn>(
        ItemLearn()
    ))

    init {
//        db = DataBaseHandler(application)
        GlobalScope.launch {
            updateFromApi()
        }
    }

    override suspend fun getAllLearnItems(): MutableStateFlow<List<ItemLearn>> {
        mutableStateFlow.value = db.getAllLearnItems().map { Mapper().LearnItemToDomain(it) }
        return mutableStateFlow
        }

    private suspend fun replaceAllInDb(list: List<LearnItemData>){
        var item = list[0]

        db.deleteAllFromLearnTable()
        list.forEach {
            Log.d("test1","shood add item to db with id ${it.id.toString()}")
            db.addLearnItem(it)
        }
    }

    suspend fun updateFromApi(){
        Log.d("test1","updateFromApi started ")
        try{
            GlobalScope.async {
                val apiList = ApiStorage().getAllLearnItems()

//                mutableStateFlow.value = apiList
                replaceAllInDb(apiList)
                mutableStateFlow.value = apiList.map { Mapper().LearnItemToDomain(it) }
                Log.d("test1","updateFromApi started inside async")
            }


        }catch (e:Exception){
            Log.d("test1","exeption дивись сюди $e")}
    }

    override suspend fun getLearnItemById(id: Int): ItemLearn {
        Log.d("test1", "getLearnItemById ${ id.toString() }")
        val item = mutableStateFlow.value.first {
            it.id == id
        }

        return item
    }


    override fun updateLearnItem(item: ItemLearn) {
        TODO("Not yet implemented")
    }

    override fun addLearnItem(item: ItemLearnToAdd) {
        TODO("Not yet implemented")
    }

    companion object{

        private var mInstance:LearnRepositoryImpl? = null
        fun getInstance(application: Application): LearnRepositoryImpl {
            if (mInstance == null){
                mInstance = LearnRepositoryImpl(application)

            }
            return mInstance!!
        }


    }





}




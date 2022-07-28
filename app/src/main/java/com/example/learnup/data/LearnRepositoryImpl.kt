package com.example.learnup.data




import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.learnup.data.api.ApiStorage
import com.example.learnup.data.local.DataBaseHandler
import com.example.learnup.domain.ItemLearn
import com.example.learnup.domain.ItemLearnToAdd
import com.example.learnup.domain.LearnRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*


class LearnRepositoryImpl(context: Context):LearnRepository{


    val db:DataBaseHandler
    var mutableStateFlow: MutableStateFlow<List<ItemLearn>> = MutableStateFlow(listOf<ItemLearn>(
        ItemLearn()
    ))

    init {
        db = DataBaseHandler(context)
        GlobalScope.launch {
            updateFromApi()
        }
    }

    override suspend fun getAllLearnItems(): MutableStateFlow<List<ItemLearn>> {
        mutableStateFlow.value = db.getAllLearnItems()
        return mutableStateFlow
        }

    suspend fun updateFromApi(){
        Log.d("test1","updateFromApi started ")
        try{
            GlobalScope.async {
                val apiList = ApiStorage().getAllLearnItems()

                mutableStateFlow.value = apiList
                db.updateLearnTable(apiList)
                mutableStateFlow.value = apiList
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
        fun getInstance(context: Context): LearnRepositoryImpl {
            if (mInstance == null){
                mInstance = LearnRepositoryImpl(context)

            }
            return mInstance!!
        }


    }





}




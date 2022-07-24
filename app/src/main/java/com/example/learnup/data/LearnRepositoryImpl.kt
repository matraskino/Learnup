package com.example.learnup.data




import android.content.Context
import android.util.Log
import com.example.learnup.data.api.ApiStorage
import com.example.learnup.data.local.DataBaseHandler
import com.example.learnup.domain.ItemLearn
import com.example.learnup.domain.ItemLearnToAdd
import com.example.learnup.domain.LearnRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class LearnRepositoryImpl(context: Context):LearnRepository{
    val db:DataBaseHandler

    lateinit var allLearnItems:Flow<List<ItemLearn>>

    init {
        db = DataBaseHandler(context)
    }


    override suspend fun getAllLearnItems(): Flow<List<ItemLearn>> {
        var list = db.getAllLearnItems()
        allLearnItems = flow{
            emit(list)
            try{
                val apiList = GlobalScope.async {
                    ApiStorage().getAllLearnItems()
                }.await()
                db.updateLearnTable(apiList)
                emit(apiList)


            }catch (e:Exception){
            Log.d("test","exeption дивись сюди $e")}
        }
        return allLearnItems
        }

    override suspend fun getLearnItemById(id: Int): ItemLearn {
        TODO("Not yet implemented")
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




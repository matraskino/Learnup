package com.example.learnup.data




import android.app.Application
import android.util.Log
import com.example.learnup.data.api.ApiBuilder
import com.example.learnup.data.local.AppDataBase
import com.example.learnup.data.model.LearnItemData
import com.example.learnup.domain.models.AppSettings
import com.example.learnup.domain.models.ItemLearn
import com.example.learnup.domain.LearnRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*


class LearnRepositoryImpl(val application: Application):LearnRepository{
    private val MODE_PRIVATE = 0;
    private val sharedPrefsName = "changetItemIds"
    private val SHARED_PREF_SETTINGS = "settings"
    private val WAY_LEARN = "wayOfLearn"
    private val FILTERED = "filtered"
    val db = AppDataBase.getInstance(application).dao()
    var mutableStateFlow: MutableStateFlow<MutableList<ItemLearn>> = MutableStateFlow(mutableListOf<ItemLearn>(
        ItemLearn()
    ))
    var settingsStateFlow:MutableStateFlow<AppSettings> = MutableStateFlow(getCurrentSettings())

    val api = ApiBuilder().build()

    init {
        GlobalScope.launch {
            updateFromApi()
        }
    }

    override suspend fun getAllLearnItems(): MutableStateFlow<MutableList<ItemLearn>> {
        mutableStateFlow.value = db.getAllLearnItems().map { Mapper().LearnItemToDomain(it) } as MutableList<ItemLearn>
//        updateFromApi()
        return mutableStateFlow
        }

    private fun replaceAllInDb(list: List<LearnItemData>){
//        db.deleteAllFromLearnTable()
        list.forEach {
            db.addLearnItem(it)
        }
            Log.d("test1","replaced ${list.size} items in db")
    }

    suspend fun updateFromApi(){
        Log.d("test1","updateFromApi started ")
        try{
            GlobalScope.async {
                val apiList = api.getAllLines()//ApiStorage().getAllLearnItems()
                replaceAllInDb(apiList)
                mutableStateFlow.value = apiList.map { Mapper().LearnItemToDomain(it) } as MutableList<ItemLearn>
                Log.d("test1","updateFromApi started inside async")
            }


        }catch (e:Exception){
            Log.d("test1","exeption дивись сюди $e")}
    }

    override suspend fun getLearnItemById(id: Int): ItemLearn {
        val item = mutableStateFlow.value.first {
            it.id == id
        }

        return item
    }

    override suspend fun saveLearnItem(item: ItemLearn) {
        val learnItemData = Mapper().LearnItemToData(item)
        val newId = db.addLearnItem(learnItemData).toInt()
        Log.d("test1","new id must be $newId")
        val shar = application.getSharedPreferences(sharedPrefsName,MODE_PRIVATE)
        shar.edit().putInt("id_of_${item.learnWord}", newId).commit()
        withContext(Dispatchers.IO) {
        Log.d("test1","before the synhronisation")
            synhronise()
            mutableStateFlow.value = db.getAllLearnItems().map { Mapper().LearnItemToDomain(it) } as MutableList<ItemLearn>
        }
    }

    override fun saveAppSettings(settings: AppSettings) {
        val shar = application.getSharedPreferences(SHARED_PREF_SETTINGS,MODE_PRIVATE)
        shar.edit().putInt(WAY_LEARN, settings.wayOfLearn).commit()
        shar.edit().putBoolean(FILTERED, settings.wordsFilter).commit()
        Log.d("test1", "saveAppSettings")
        settingsStateFlow.value = settings
    }

    private fun getCurrentSettings(): AppSettings{

        Log.d("test1", "get new APP SETTINGS")
        val shar = application.getSharedPreferences(SHARED_PREF_SETTINGS,MODE_PRIVATE)
        val wayOfLearn = shar.getInt(WAY_LEARN, AppSettings.SHOW_ALL)
        val wordsFilter = shar.getBoolean(FILTERED,true)
        return AppSettings(wayOfLearn = wayOfLearn, wordsFilter = wordsFilter)
    }

    override fun getAppSettings(): MutableStateFlow<AppSettings> {
        settingsStateFlow.value = getCurrentSettings()
        return settingsStateFlow
    }

    suspend fun synhronise(){
        val shar = application.getSharedPreferences(sharedPrefsName,MODE_PRIVATE)
        val all = shar.all
        Log.d("test1","need to synhronise ${all.size} items")
        all.forEach { (s, any) ->
            Log.d("test1","will get from db to synhronise item $s with id ${any.toString()}")
            val item = withContext(Dispatchers.IO){async {
                try {
                    db.getLearnItem(any as Int)
                }catch (e:Exception){
                    Log.d("test1","cannot synhronise item $s")
                    null
                }
            }.await()}
            if(item != null){
                Log.d("test1","will synhronise ${item?.learnWord} with id ${item.id}")
                api.saveLearnItem(item)
            }
            //TODO check if item saved on server, and after delete if from shared preference
        }
        shar.edit().clear().commit()//when check save on server will be implemented, delete this line
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




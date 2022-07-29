package com.example.learnup.data.local

import android.app.Application
import androidx.room.*
import com.example.learnup.data.model.LearnItemData

@Database(entities = [LearnItemData::class], version = 1, exportSchema = false)
abstract class AppDataBase: RoomDatabase() {

    abstract fun dao():Dao



    companion object{

        private var INSTANCE: AppDataBase? = null
        private val LOCK = Any()//для проверки нет ли уже такого-же
        private val DB_NAME = "LearnItemsDatabase"

        fun getInstance(application: Application): AppDataBase {
            INSTANCE?.let {
                return it
            }
            synchronized(LOCK){
                INSTANCE?.let{
                    return it
                }
                val db = Room.databaseBuilder(
                    application,
                    AppDataBase::class.java,
                    DB_NAME
                ).build()
                INSTANCE = db
            return INSTANCE!!
            }
        }
    }
}
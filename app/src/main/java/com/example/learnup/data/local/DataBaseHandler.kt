package com.example.learnup.data.local

import android.annotation.SuppressLint
import android.app.Application
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.learnup.domain.ItemLearn


class DataBaseHandler(context: Context): SQLiteOpenHelper(context, DATABASE_NAME,null,
    DATABASE_VERSION) {


    companion object{
        private const val DATABASE_NAME = "LearnItemsDatabase"
        private const val DATABASE_VERSION = 1
        private const val TABLE_LEARN_ITEMS = "LearnItemsTable"
        private const val KEY_ID = "_id"
        private const val KEY_WORD = "learnWord"
        private const val KEY_DEFINITION = "defenition"
        private const val KEY_TO_LEARN = "toLearn"
        private const val KEY_LINK = "link"
        private const val KEY_EXTRA_DESCRIPTION = "extraDescription"

    }

    override fun onCreate(db: SQLiteDatabase?) {
        Log.d("test1","Data Base on created started")
        val CREATE_LEARN_ITEMS_TABLE = ("CREATE TABLE $TABLE_LEARN_ITEMS " +
                "($KEY_ID INTEGER PRIMARY KEY, $KEY_WORD TEXT, " +
                "$KEY_DEFINITION TEXT, $KEY_TO_LEARN INTEGER, $KEY_LINK TEXT,$KEY_EXTRA_DESCRIPTION TEXT)")
        db?.execSQL(CREATE_LEARN_ITEMS_TABLE)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }

    fun updateLearnTable(list:List<ItemLearn>){
        Log.d("test1","Data Base updateLearnTable started")
        val db = this.writableDatabase
        db.execSQL("DELETE FROM $TABLE_LEARN_ITEMS")
        list.forEach {
            val contentValues = ContentValues()
            contentValues.put(KEY_WORD, it.learnWord)
            contentValues.put(KEY_ID, it.id)
            contentValues.put(KEY_DEFINITION, it.definition)
            contentValues.put(KEY_TO_LEARN, it.toLearn)
            contentValues.put(KEY_LINK, it.link)
            contentValues.put(KEY_EXTRA_DESCRIPTION, it.extraDescription)
            db.insert(TABLE_LEARN_ITEMS,null, contentValues)

        }
        db.close()
    }

    @SuppressLint("Range")
    fun getAllLearnItems():List<ItemLearn>{
        Log.d("test1","Data Base getAllLernItems started")
        var list = mutableListOf<ItemLearn>()
        val db = this.readableDatabase
        val selectQuery = "SELECT * FROM $TABLE_LEARN_ITEMS"
        var cursor: Cursor? = null

        try {
            cursor = db.rawQuery(selectQuery,null)
        }catch (e:SQLiteException){
            db.execSQL(selectQuery)
            return listOf()


        }
        var id:Int
        var learnWord:String
        var definition:String
        var toLearn:Boolean
        var link:String
        var extraDescription:String

        if (cursor.moveToFirst()){
            do {
                id = cursor.getInt(cursor.getColumnIndex(KEY_ID))
                learnWord = cursor.getString(cursor.getColumnIndex(KEY_WORD))
                definition = cursor.getString(cursor.getColumnIndex(KEY_DEFINITION))
                link = cursor.getString(cursor.getColumnIndex(KEY_LINK))
                extraDescription = cursor.getString(cursor.getColumnIndex(KEY_EXTRA_DESCRIPTION))
                list.add(ItemLearn(id,learnWord,definition,false,link,extraDescription))
            } while (cursor.moveToNext())
        }
        Log.d("test1","Data Base getAllLearnItems before return")
        return list
    }
}


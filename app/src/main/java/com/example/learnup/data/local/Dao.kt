package com.example.learnup.data.local

import androidx.room.*
import androidx.room.Dao
import com.example.learnup.data.model.LearnItemData

@Dao
interface Dao {

    @Query("SELECT * FROM LearnItemsTable")
    fun getAllLearnItems(): List<LearnItemData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addLearnItem(item:LearnItemData)

    @Query("DELETE FROM LearnItemsTable WHERE id=:id")
    fun deletLearnItem(id:Int)

    @Query("SELECT * FROM LearnItemsTable WHERE id=:id LIMIT 1")
    fun getLearnItem(id:Int):LearnItemData

    @Query("DELETE FROM LearnItemsTable")
    fun deleteAllFromLearnTable()



}
package com.example.learnup.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "LearnItemsTable")
data class LearnItemData(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val learnWord:String,
    val description:String,
    val isChecked:Boolean,
    val link:String,
    val extraDescription:String
)

package com.example.learnup.data.model



data class LearnItemApi(

    val id:Int,
    val learnWord:String,
    val description:String,
    val isChecked:Boolean,
    val link:String,
    val extraDescription:String
)

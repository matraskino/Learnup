package com.example.learnup.data.model

data class LearnResponse(
    val id:Int,
    val word:String,
    val description:String,
    val isChecked:Boolean,
    val link:String,
    val extraDescription:String
)

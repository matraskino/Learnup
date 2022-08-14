package com.example.learnup.domain.models


class AppSettings(
    val wayOfLearn:Int,
    val wordsFilter: Boolean,
    val random:Boolean


){


    companion object{
        val SHOW_WORD = 1
        val SHOW_DESCRIPTION = 2
        val SHOW_ALL = 0


    }
}


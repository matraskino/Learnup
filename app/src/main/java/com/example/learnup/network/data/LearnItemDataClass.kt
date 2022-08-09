package com.example.learnup.network.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class LearnItemDataClass(
    val id:Int,
    val learnWord:String,
    val description:String,
    val isChecked:Boolean,
    val link:String,
    val extraDescription:String
): Parcelable

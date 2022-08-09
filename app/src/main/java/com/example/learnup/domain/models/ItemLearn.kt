package com.example.learnup.domain.models

data class ItemLearn(
    val id: Int = 0,
    val learnWord: String = "default word",
    val description: String = "defaul description",
    val isChecked: Boolean = false,
    val link: String = "www.default.com",
    val extraDescription: String = " default extra data bout dord",
    val topic:String = "other"
)

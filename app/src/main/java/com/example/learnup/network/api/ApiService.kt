package com.example.learnup.network.api

import com.example.learnup.data.model.LearnItemData
import com.example.learnup.network.data.LearnItemDataClass
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @POST("exec/saveLearnItem")//saveLearnItem")
    suspend fun test(@Body body: LearnItemData):String

    @GET("exec?action=getAllLines")//saveLearnItem")
    suspend fun getAllLines():List<LearnItemDataClass>
}
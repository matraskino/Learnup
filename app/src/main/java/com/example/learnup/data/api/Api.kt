package com.example.learnup.data.api

import com.example.learnup.data.model.LearnItemApi
import com.example.learnup.data.model.LearnItemData
import com.squareup.moshi.Moshi
import kotlinx.coroutines.runBlocking
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import java.util.concurrent.TimeUnit


interface Api {
    @POST("exec?action=saveLearnItem")
    suspend fun saveLearnItem(@Body body: LearnItemApi):String

    @GET("exec?action=getAllLines")
    suspend fun getAllLines():List<LearnItemData>

}

class ApiBuilder() {

    fun build():Api {
        val logginInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        var urlOfServer =
            "https://script.google.com/macros/s/AKfycbwrgF_bbr-qX08qYNIUHCC94urjeQvj9SIEgtxDsf1tv47VrbUPQFEnN8VYzyOBaVg7/"
        val client = OkHttpClient.Builder()
            .addInterceptor(logginInterceptor)
            .connectTimeout(20, TimeUnit.SECONDS)
            .build()
        val moshi = Moshi.Builder().build()
        val moshiConverterFactory = MoshiConverterFactory.create(moshi)
        val retrofit = Retrofit.Builder()
            .baseUrl(urlOfServer)
            .client(client)
            .addConverterFactory(moshiConverterFactory)
            .build()
        return retrofit.create(Api::class.java)
    }
}




package com.example.learnup.data.api


import android.util.Log
import com.example.learnup.data.model.LearnItemData
import com.example.learnup.domain.ItemLearn
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

class ApiStorage {

    suspend fun getAllLearnItems(): List<LearnItemData> {
        Log.d("test1","API Storage started")
        val urlOfServer = "https://script.google.com/macros/s/AKfycbwBFONX-b46MymX-p0LQxmd0MxdmJJVPrk8j3kxCRItPHRd91Ncp55zdr_RhpNEzs37/exec?action=getAllLines"
        val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder()/*.addInterceptor(loggingInterceptor)*/.connectTimeout(25
            , TimeUnit.SECONDS).build()
        val gson = Gson()
        val request = Request
            .Builder()
            .get()
            .url(urlOfServer)
            .build()
//        var learnList:List<LearnItemData> = listOf(LearnItemData(1,"test","test",true,"twet","trwst"))

        val call =
            withContext(Dispatchers.IO){

            client.newCall(request)
            }

        val response = call.execute()
        val stringResponse = response.body!!.string()
        val sType = object : TypeToken<List<LearnItemData>>() { }.type


        var learnList = gson.fromJson<List<LearnItemData>>(stringResponse,sType)

//        }

        println(learnList)
        Log.d("test1","API Storage before return")
        return learnList!!


    }
}

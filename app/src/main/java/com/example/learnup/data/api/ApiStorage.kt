package com.example.learnup.data.api


import com.example.learnup.data.model.LearnResponse
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

    suspend fun getAllLearnItems(): List<ItemLearn> {
        val urlOfServer = "https://script.google.com/macros/s/AKfycbwBFONX-b46MymX-p0LQxmd0MxdmJJVPrk8j3kxCRItPHRd91Ncp55zdr_RhpNEzs37/exec?action=getAllLines"
        val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder().addInterceptor(loggingInterceptor).connectTimeout(25
            , TimeUnit.SECONDS).build()
        val gson = Gson()
        val request = Request
            .Builder()
            .get()
            .url(urlOfServer)
            .build()
        var learnListMap:List<ItemLearn> = listOf(ItemLearn(1,"test","test",true,"twet","trwst"))

        val call =
            withContext(Dispatchers.IO){

            client.newCall(request)
            }

        val response = call.execute()
        val stringResponse = response.body!!.string()
        val sType = object : TypeToken<List<LearnResponse>>() { }.type


        val    learnList = gson.fromJson<List<LearnResponse>>(stringResponse,sType)

//        }
         learnListMap= learnList.map { (
                ItemLearn(it.id,it.word,it.description,it.isChecked,it.link,it.extraDescription)
        ) }
        println(learnListMap)

        return learnListMap!!


    }
}

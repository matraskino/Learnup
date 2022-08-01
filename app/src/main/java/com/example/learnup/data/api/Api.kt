package com.example.learnup.data.api

import com.example.learnup.data.model.LearnItemData
import com.squareup.moshi.Moshi
import kotlinx.coroutines.runBlocking
import okhttp3.CertificatePinner
import okhttp3.CipherSuite.Companion.TLS_DHE_RSA_WITH_AES_128_GCM_SHA256
import okhttp3.CipherSuite.Companion.TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256
import okhttp3.CipherSuite.Companion.TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256
import okhttp3.ConnectionSpec
import okhttp3.OkHttpClient
import okhttp3.TlsVersion
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import java.security.cert.CertificateFactory
import java.util.*
import java.util.concurrent.TimeUnit


interface Api {
    @POST("exec/saveLearnItem")//saveLearnItem")
    suspend fun test(@Body body: LearnItemData):String

    @GET("exec?action=getAllLines")//saveLearnItem")
    suspend fun testGet()

}

fun main() = runBlocking{
    val logginInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    var urlOfServer = "https://script.google.com/macros/s/AKfycbwyL3p4DhEXgy7ioo6rZ9MprvtxPxLECo7Jbkx5eOwas2bGCaU0n4Cqof-6yCsTbXRK/"
    val testUrl = "https://script.google.com/macros/s/AKfycbyJmsBQJRK3MHhPnm5mQkU8l5cRX0eXL8-cQQypF_g/"
//    urlOfServer = "https://testpavellearnup.free.beeceptor.com/"

    val spec: ConnectionSpec = ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
        .tlsVersions(TlsVersion.TLS_1_2)
        .cipherSuites(
            TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256,
            TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256,
            TLS_DHE_RSA_WITH_AES_128_GCM_SHA256
        )
        .build()
    CertificateFactory.getInstance("X.509")
    val client = OkHttpClient.Builder()
        .addInterceptor(logginInterceptor)
        .certificatePinner(
            CertificatePinner.Builder().add("").build()
        )
        .connectTimeout(20, TimeUnit.SECONDS)
//        .connectionSpecs(listOf(spec))
        .build()
    val moshi = Moshi.Builder().build()
    val moshiConverterFactory = MoshiConverterFactory.create(moshi)
    val retrofit = Retrofit.Builder()
        .baseUrl(urlOfServer)
        .client(client)
        .addConverterFactory(moshiConverterFactory)
        .build()

    val api = retrofit.create(Api::class.java)
//    api.test(LearnItemData(1,"testWord","testDesc",false,"asdfasdf.com"," addithional description"))
    var request = api.testGet()
    println(request.toString())
println("test")
}
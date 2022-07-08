package com.dnc.kt.quantumassignment

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface RetrofitService {

    @GET("top-headlines?country=in&apiKey=ce2ccca8217e422c821a9612bd1f6370")
    fun getHeadlines(): Call<TopNews>
//    @GET("top-headlines?apiKey=${BuildConfig.NEWS_API_KEY}&category=technology")
//    fun gettemp(): Call<temp>


    companion object{
        var retrofitService:RetrofitService?=null

        fun getInstance():RetrofitService{
            if (retrofitService==null){
                val retrofit= Retrofit.Builder()
                    .baseUrl("https://newsapi.org/v2/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                retrofitService=retrofit.create(RetrofitService::class.java)
            }
            return retrofitService!!
        }
    }
}
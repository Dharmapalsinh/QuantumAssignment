package com.dnc.kt.quantumassignment

import retrofit2.Call

class News_Repository (private val retrofitService: RetrofitService) {

    //     fun getNewsHeadlines()=retrofitService.getHeadlines()
    fun getNewsHeadlines(): Call<TopNews> =retrofitService.getHeadlines()
}
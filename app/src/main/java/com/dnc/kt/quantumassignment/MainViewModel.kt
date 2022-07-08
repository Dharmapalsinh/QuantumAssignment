package com.dharmapal.internship_assignment

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.dnc.kt.quantumassignment.News
import com.dnc.kt.quantumassignment.News_Repository
import com.dnc.kt.quantumassignment.TopNews
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(private val repository: News_Repository, application: Application): AndroidViewModel(
    application
) {

    val newArticle = MutableLiveData<List<News>>()
    val errorMessage = MutableLiveData<String>()



    fun getNews() {
        val response = repository.getNewsHeadlines()
        response.enqueue(object : Callback<TopNews> {
            override fun onResponse(
                call: Call<TopNews>,
                response: Response<TopNews>
            ) {
                newArticle.postValue(response.body()!!.articles)
                Log.d("tagged",response.body().toString())
            }

            override fun onFailure(call: Call<TopNews>, t: Throwable) {
                errorMessage.postValue(t.message)
            }
        })
    }

    class ViewModelFactory(val repository: News_Repository,val application: Application): ViewModelProvider.Factory{


        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MainViewModel(repository,application) as T
        }
    }
}
package com.dnc.kt.quantumassignment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.SearchView
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Observer
import com.dharmapal.internship_assignment.MainAdapter
import com.dharmapal.internship_assignment.MainViewModel
import com.dnc.kt.quantumassignment.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    val adapter= MainAdapter()
    val repository=News_Repository(RetrofitService.getInstance())
    val viewModel: MainViewModel by viewModels(){MainViewModel.ViewModelFactory(repository,application)}
    var templist:List<News> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recycler.adapter = adapter

        viewModel.newArticle.observe(this){

            binding.genresSearch.addTextChangedListener( object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun afterTextChanged(p0: Editable?) {
                    if (p0!!.isEmpty()){
                        adapter.submitList(it)
                    }
                    else{
                        templist=it.filter {
                            it.title!!.contains(p0)
                        }
                        adapter.submitList(templist)
                    }
                }

            })
        }

//        adapter.submitList(templist)
        viewModel.newArticle.observe(this, Observer {
            Log.d("tagged", "onCreate: $it")
            adapter.submitList(it)
        })
        viewModel.errorMessage.observe(this, Observer {
            Log.d("tagged", it)
        })
        viewModel.getNews()
    }
}


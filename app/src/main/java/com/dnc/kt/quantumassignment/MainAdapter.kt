package com.dharmapal.internship_assignment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dnc.kt.quantumassignment.News
import com.dnc.kt.quantumassignment.databinding.ItemNewsBinding
import com.dnc.kt.quantumassignment.diffutill

class MainAdapter: ListAdapter<News, MainViewHolder>(diffutill) {
//    var news= mutableListOf<News_Model>()
//    fun setMovieList(Breeds: temp) {
////        this.news = Breeds.tempHeadlines as MutableList<News_Model>
//        Breeds.tempHeadlines=this.news
//        notifyDataSetChanged()
//    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val inflater= LayoutInflater.from(parent.context)
        val binding= ItemNewsBinding.inflate(inflater,parent,false)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val movie:News = getItem(position)
//        Glide.with(holder.itemView.context).load(movie.urltoImage).into(holder.binding.imageview)
        holder.binding.title.text=movie.title
        holder.binding.desc.text=movie.description
        holder.binding.publishat.text=movie.publishedAt
//        Glide.with(holder.itemView.context).load(movie.urlToImg).into(holder.binding.img)

    }
}

class MainViewHolder(val binding: ItemNewsBinding): RecyclerView.ViewHolder(binding.root)

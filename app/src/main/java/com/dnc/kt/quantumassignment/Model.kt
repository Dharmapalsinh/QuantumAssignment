package com.dnc.kt.quantumassignment

import androidx.recyclerview.widget.DiffUtil



data class News (
        val id:Int,
        val title:String?,val description:String?,val publishedAt:String?,val urlToImg:String?
        ){
        data class Source(val id:String?,val name:String?)
}


data class TopNews
        (var id: Int,
        val articles:List<News>)

object diffutill: DiffUtil.ItemCallback<News>() {
        override fun areItemsTheSame(oldItem: News, newItem: News): Boolean {
                return oldItem.title==newItem.title
        }

        override fun areContentsTheSame(oldItem: News, newItem: News): Boolean {
                return oldItem==newItem
        }
}

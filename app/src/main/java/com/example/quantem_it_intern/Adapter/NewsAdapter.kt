package com.example.quantem_it_intern.Adapter

import android.content.Context
import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.quantem_it_intern.Model.Article
import com.example.quantem_it_intern.R

class NewsAdapter(val context: Context?, val article: List<Article>):RecyclerView.Adapter<NewsAdapter.NewsAdapterViewHolder>(){

    class NewsAdapterViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val newsName =itemView.findViewById<TextView>(R.id.tvTitle)
        val newsImage = itemView.findViewById<ImageView>(R.id.ivArticleImage)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsAdapterViewHolder {
        val view =LayoutInflater.from(context).inflate(R.layout.item_article,parent,false)
        return NewsAdapterViewHolder(view)
    }

    override fun getItemCount(): Int {
        return  article.size
    }

    override fun onBindViewHolder(holder: NewsAdapterViewHolder, position: Int) {
        val articles = article[position]
        holder.newsName.text = articles.title
        if (context != null) {
            Glide.with(context).load(articles.urlToImage).into(holder.newsImage)
        }
        holder.itemView.setOnClickListener{
            Toast.makeText(context,articles.title,Toast.LENGTH_SHORT).show()
        }
    }
}
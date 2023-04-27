package com.example.quantem_it_intern

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quantem_it_intern.Adapter.NewsAdapter
import com.example.quantem_it_intern.Api.NewsSrevice
import com.example.quantem_it_intern.Fragment.LoginFrag
import com.example.quantem_it_intern.Model.Article
import com.example.quantem_it_intern.Model.News
import com.example.quantem_it_intern.databinding.ActivityHomeBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class Home_Activity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    lateinit var adapter: NewsAdapter
    private var articles = mutableListOf<Article>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        adapter = NewsAdapter(this, articles)
        binding.newsList.adapter = adapter
        binding.newsList.layoutManager = LinearLayoutManager(this)
        getNews()
    }

    private fun getNews() {
        val news: Call<News> = NewsSrevice.newsInstance.getHeadlines("in", 1)
        news.enqueue(object : Callback<News> {
            override fun onResponse(call: Call<News>, response: Response<News>) {
                val news: News? = response.body()
                if (news != null) {
                    Log.d("pawan", news.toString())
                    articles.addAll(news.articles)
                    adapter.notifyDataSetChanged()


                }
            }

            override fun onFailure(call: Call<News>, t: Throwable) {
                Log.d("Pawan", "Error featching News")
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.Logout -> {
                startActivity(Intent(this, LoginFrag::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.log, menu)
        return true
    }
}
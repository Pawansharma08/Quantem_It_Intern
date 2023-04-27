package com.example.quantem_it_intern.Api

import com.example.quantem_it_intern.Model.News
import com.example.quantem_it_intern.Utils.Constants.Companion.API_KEY
import com.example.quantem_it_intern.Utils.Constants.Companion.BASE_URL
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    @GET("v2/top-headlines?apiKey=$API_KEY")
    fun getHeadlines(@Query("country") country: String, @Query("page") page: Int): Call<News>
}

object NewsSrevice{
    val newsInstance : NewsApi
    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        newsInstance = retrofit.create(NewsApi::class.java)
    }
}

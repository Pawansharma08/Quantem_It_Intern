package com.example.quantem_it_intern.Api

import com.example.quantem_it_intern.Utils.Constants.Companion.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NewsInstance {
    companion object{
        val newsInstancess : NewsApi
        init {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            newsInstancess = retrofit.create(NewsApi::class.java)
        }
    }
}
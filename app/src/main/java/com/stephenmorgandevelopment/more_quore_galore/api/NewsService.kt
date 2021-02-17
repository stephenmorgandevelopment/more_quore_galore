package com.stephenmorgandevelopment.more_quore_galore.api

import com.stephenmorgandevelopment.more_quore_galore.models.Article
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Singleton


//https://newsapi.org/v2/top-headlines?country=us&apiKey=53baf97b616143159bdaa266d1716ca2
@Singleton
interface NewsService {
    @GET("v2/top-headlines")
    fun getRecentHeadlines(
        @Query("country") country: String,
        @Query("apiKey") apiKey: String
    ) : Call<List<Article>>
}
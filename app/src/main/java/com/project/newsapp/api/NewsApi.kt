package com.project.newsapp.api

import com.project.newsapp.models.NewsResponse
import com.project.newsapp.util.Constants.Companion.API_KEY
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {



//search for top news{headlines}
   /* @GET("/v2/top-headlines")
    suspend fun getBaseNews(
        @Query("country") countryCode:String="us",
        @Query("page") pageNumber:Int=1,
        @Query("apiKey") apiKey:String=API_KEY

    ):NewsResponse*/

    //search for top news{headlines}
    @GET("/v2/top-headlines")
    suspend fun getBaseNews(
        @Query("country") countryCode:String="us",
        @Query("pageSize") pageSize:Int,
        @Query("page") pageNumber:Int,
        @Query("apiKey") apiKey:String=API_KEY

    ):NewsResponse




    // search for top news by refresh
   /* @GET("/v2/top-headlines")
    suspend fun getBaseNewsRefresh(
        @Query("country") countryCode:String="us",
        @Query("page") page:Int, // TODO : FIX THIS
        @Query("apiKey") apiKey:String=API_KEY

    ):NewsResponse*/

    @GET("/v2/top-headlines")
    suspend fun getBaseNewsRefresh(
        @Query("category") category: String,
        @Query("pageSize") pageSize:Int,
        @Query("country") countryCode:String="us",
        @Query("page") page:Int, // TODO : FIX THIS
        @Query("apiKey") apiKey:String=API_KEY

    ):NewsResponse



    @GET("/v2/top-headlines")
    suspend fun getNewsCategory(
        @Query("country") countryCode:String="us",
        @Query("category") category:String,
        @Query("page") pageNumber:Int=1,
        @Query("apiKey") apiKey:String=API_KEY
    ):NewsResponse



    //search for everything
    @GET("/v2/everything")
    suspend fun searchNews(
        @Query("q") searchQuery:String,  //pass query here from the search input
        @Query("page") pageNumber:Int=1,
        @Query("apiKey") apiKey: String=API_KEY
    ):NewsResponse


    // search news by filtering by date,relevancy,popularity
    @GET("/v2/everything")
    suspend fun searchNewsSorting(
        @Query("q") searchQuery: String,
        @Query("sortBy") sortBy: String,  // Default to 'publishedAt'
        @Query("page") pageNumber: Int = 1,
        @Query("apiKey") apiKey: String = API_KEY
    ): NewsResponse
}
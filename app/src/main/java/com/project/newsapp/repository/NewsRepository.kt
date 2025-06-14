package com.project.newsapp.repository

import com.project.newsapp.api.ArticleDatabase
import com.project.newsapp.api.RetrofitInstance
import com.project.newsapp.models.Article
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


// val database is passed  to access functions of our database
class NewsRepository(private val database: ArticleDatabase) {

    //get base news from the api
    suspend fun getBaseNews(country: String, page: Int, pageSize: Int) =
        RetrofitInstance.api.getBaseNews(country, pageNumber = page, pageSize = pageSize)

    // get base news form the api on refresh
    suspend fun getBaseNewsRefresh(
        categorySearch: String,
        pageSize: Int,
        country: String,
        pageNumber: Int
    ) = RetrofitInstance.api.getBaseNewsRefresh(
        category = categorySearch,
        pageSize = pageSize,
        country,
        page = pageNumber
    )

    suspend fun getNewsCategory(country: String, category: String, pageNumber: Int) =
        RetrofitInstance.api.getNewsCategory(country, category, pageNumber)

    // function to search for news
    suspend fun searchNews(searchQuery: String, pageNumber: Int) =
        RetrofitInstance.api.searchNews(searchQuery, pageNumber)

    // function to search news sorting
    suspend fun searchNewsSorting(searchQuery: String, sortBy: String, pageNumber: Int) =
        RetrofitInstance.api.searchNewsSorting(searchQuery, sortBy, pageNumber)


    //insert news article
    suspend fun insertArticle(article: Article) {
        withContext(Dispatchers.IO) {
            database.getArticleDao().upsert(article)
        }
    }

    //delete news article
    suspend fun deleteArticle(article: Article) {
        withContext(Dispatchers.IO) {
            database.getArticleDao().deleteArticle(article)
        }

    }

    //get all news articles
   suspend fun getAllArticles(): List<Article> {
        return withContext(Dispatchers.IO) {
            database.getArticleDao().getAllArticles()
        }

   }




}
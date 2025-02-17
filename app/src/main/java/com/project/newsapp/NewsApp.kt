package com.project.newsapp

import android.app.Application
import com.project.newsapp.api.ArticleDatabase
import com.project.newsapp.repository.NewsRepository

class NewsApp: Application()  {
   // val database by lazy { ArticleDatabase(this) }
    val database: ArticleDatabase by lazy {
        ArticleDatabase.createDatabase(this)
    }
   // val database: ArticleDatabase by lazy { ArticleDatabase(this) }
    val newsRepository by lazy { NewsRepository(database) }



}
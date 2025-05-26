package com.project.newsapp

import android.app.Application
import com.project.newsapp.api.ArticleDatabase


//initialization class for shared services like firebase,database
class NewsApp: Application()  {



    val database: ArticleDatabase by lazy {
        ArticleDatabase.invoke(this)
    }


}





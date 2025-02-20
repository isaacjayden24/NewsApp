package com.project.newsapp.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.project.newsapp.models.Article


@Dao
interface ArticleDao {
    //function to save an article in Room
    @Insert(onConflict = OnConflictStrategy.REPLACE) // the article that exist can be replaced
    suspend fun upsert(article: Article): Long


    //function to get all articles in Room
    @Query("SELECT * FROM articles")
    suspend fun getAllArticles(): List<Article>

    //function to delete an article in Room
    @Delete
    suspend fun deleteArticle(article: Article)




}
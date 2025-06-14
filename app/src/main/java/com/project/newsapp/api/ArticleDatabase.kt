package com.project.newsapp.api

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.project.newsapp.db.ArticleDao
import com.project.newsapp.db.Converters
import com.project.newsapp.models.Article

@Database( entities = [Article::class], version = 2, exportSchema = false)

@TypeConverters(Converters::class)

 abstract class ArticleDatabase : RoomDatabase() {

        abstract fun getArticleDao(): ArticleDao

        companion object {
            @Volatile
            private var instance: ArticleDatabase? = null
            private val LOCK = Any()

         operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
          instance ?: createDatabase(context).also { instance = it }
         }

            fun createDatabase(context: Context) =
          Room.databaseBuilder(
           context.applicationContext,
           ArticleDatabase::class.java,
           "article_db.db"
          ).fallbackToDestructiveMigration() //TODO: Remove this before production
              .build()



        }

}

package com.project.newsapp.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.project.newsapp.db.SourceConverter
import java.io.Serializable


//make this an Entity for Room
@Entity(
    tableName = "articles"
)

@TypeConverters(SourceConverter::class)
data class Article(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    val author: String?,
    val content: String?,
    val description: String?,
    val publishedAt: String?,
    val source: Source?,
    val title: String?,
    val url: String?,
    val urlToImage: String?
):Serializable
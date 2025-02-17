package com.project.newsapp.db

import androidx.room.TypeConverter
import com.project.newsapp.models.Source

class Converters {

    // Typeconverter for source class

    @TypeConverter
    fun fromSource(source: Source): String {
        return source.name
    }

    @TypeConverter
    fun toSource(name: String): Source {
        return Source(name, name)
    }


}
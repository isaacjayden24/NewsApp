package com.project.newsapp.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.project.newsapp.models.Source

class SourceConverter {

    @TypeConverter
    fun fromSource(source: Source): String {
        return Gson().toJson(source)
    }

    @TypeConverter
    fun toSource(sourceString: String): Source {
        val type = object : TypeToken<Source>() {}.type
        return Gson().fromJson(sourceString, type)
    }
}
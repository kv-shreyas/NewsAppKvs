package com.shreyasDev.newsapp.db

import androidx.room.TypeConverter
import com.shreyasDev.newsapp.models.Source

class Converters {

    @TypeConverter
    fun fromSource(source: Source):String{
        return source.name.toString()
    }

    @TypeConverter
    fun toSource(name:String): Source {
        return Source(name,name)
    }
}
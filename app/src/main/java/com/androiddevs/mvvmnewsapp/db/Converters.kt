package com.androiddevs.mvvmnewsapp.db

import androidx.room.TypeConverter
import com.androiddevs.mvvmnewsapp.models.Source

// we are using converts because we need to convert non primitive data type to primitive DT such that our DAO will
// understand this

class Converters {

    @TypeConverter
    fun fromSource(source : Source) : String? = source.name

    @TypeConverter
    fun toSource(name : String) : Source = Source(name,name)


}
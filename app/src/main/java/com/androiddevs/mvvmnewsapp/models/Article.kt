package com.androiddevs.mvvmnewsapp.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

// we are declaring this class as serializable because it enables us to pass ti through different fragments
// so that we can save the articles later on in other fragment(DAO)

@Entity(tableName = "articles")
data class Article(

    @PrimaryKey(autoGenerate = true)
    var id : Int ?= null,
    val author: String?,
    val content: String?,
    val description: String?,
    val publishedAt: String?,
    val source: Source,
    val title: String?,
    val url: String?,
    val urlToImage: String?
) : Serializable
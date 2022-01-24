package com.androiddevs.mvvmnewsapp.models

import androidx.lifecycle.LiveData
import androidx.room.*
import com.androiddevs.mvvmnewsapp.adapters.Article

@Dao
interface ArticleDao {

    // upsert means update and insert in DB
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(article : Article) : Long

    @Query("SELECT * FROM articles ORDER BY id ASC")
    fun getAllArticles() : LiveData<List<Article>>

    @Delete
    suspend fun deleteArticle(article: Article)



}
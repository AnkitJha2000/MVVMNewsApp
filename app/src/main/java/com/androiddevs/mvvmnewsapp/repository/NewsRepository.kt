package com.androiddevs.mvvmnewsapp.repository

import com.androiddevs.mvvmnewsapp.api.RetrofitInstance
import com.androiddevs.mvvmnewsapp.db.ArticleDatabase
import com.androiddevs.mvvmnewsapp.models.Article

class NewsRepository(private val db : ArticleDatabase) {

    suspend fun getAllArticles(countryCode : String, pageNumber : Int) =
        RetrofitInstance.api.getBreakingNews(countryCode,pageNumber)

    suspend fun searchArticles(query : String,pageNumber: Int) =
        RetrofitInstance.api.searchForNews(query,pageNumber)

    suspend fun upsert(article : Article) = db.getAllArticleDao().upsert(article)

    fun getSavedNews() = db.getAllArticleDao().getAllArticles()

    suspend fun deleteArticle(article: Article) = db.getAllArticleDao().deleteArticle(article)


}
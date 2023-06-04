package com.androiddevs.newsapp.repository

import com.androiddevs.newsapp.api.RetrofitInstance
import com.androiddevs.newsapp.db.ArticleDatabase
import com.androiddevs.newsapp.models.Article
import javax.inject.Inject

class NewsRepository @Inject constructor(var db: ArticleDatabase) {

    suspend fun getBreakingNews(countryCode: String, pageNumber: Int) =
        RetrofitInstance.api.getBreakingNews(countryCode, pageNumber)

    suspend fun searchQuery(searchQuery: String, pageNumber: Int) =
        RetrofitInstance.api.searchForNews(searchQuery, pageNumber)

    suspend fun upsert(article: Article) = db.getArticleDao().upsert(article)

    fun getSavedNews() = db.getArticleDao().getAllArticles()

    suspend fun deleteArticle(article: Article) = db.getArticleDao().deleteArticle(article)
}
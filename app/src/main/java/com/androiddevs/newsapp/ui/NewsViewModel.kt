package com.androiddevs.newsapp.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.androiddevs.newsapp.models.Article
import com.androiddevs.newsapp.models.NewsResponse
import com.androiddevs.newsapp.repository.NewsRepository
import com.androiddevs.newsapp.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class NewsViewModel(var newsRepository: NewsRepository) : ViewModel() {

    val breakingNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData();
    var breakingNewsPage = 1;

    val searchNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData();
    var searchNewsPage = 1;

    init {
        getBreakingNews("us")
    }

    fun getBreakingNews(countryCode: String) = viewModelScope.launch {
        breakingNews.postValue(Resource.Loading())
        val response = newsRepository.getBreakingNews(countryCode, breakingNewsPage);
        breakingNews.postValue(handleBreakingNewsResponse(response))

    }

    fun getSearchNews(countryCode: String) = viewModelScope.launch {
        searchNews.postValue(Resource.Loading())
        val response = newsRepository.searchQuery(countryCode, searchNewsPage);
        searchNews.postValue(handleSearchNewsResponse(response))

    }

    private fun handleBreakingNewsResponse(response: Response<NewsResponse>): Resource<NewsResponse> {
        breakingNews.postValue(Resource.Loading())
        if (response.isSuccessful) {
            response.body()?.let { newsResponse ->
                return Resource.Success(newsResponse)
            }
        }
        return Resource.Error(response.message())
    }

    private fun handleSearchNewsResponse(response: Response<NewsResponse>): Resource<NewsResponse> {
        searchNews.postValue(Resource.Loading())
        if (response.isSuccessful) {
            response.body()?.let { newsResponse ->
                return Resource.Success(newsResponse)
            }
        }
        return Resource.Error(response.message())
    }

    fun saveArticle(article: Article) = viewModelScope.launch {
        newsRepository.upsert(article)
    }

    fun getSavedNews() = newsRepository.getSavedNews()

    fun deleteArticle(article: Article) = viewModelScope.launch {
        newsRepository.deleteArticle(article)
    }
}
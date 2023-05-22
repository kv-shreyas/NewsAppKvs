package com.androiddevs.newsapp.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.androiddevs.newsapp.models.Article
import com.androiddevs.newsapp.models.NewsResponse
import com.androiddevs.newsapp.repository.NewsRepository
import com.androiddevs.newsapp.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException

class NewsViewModel(app: Application, var newsRepository: NewsRepository) : AndroidViewModel(app) {

    val breakingNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData();
    var breakingNewsPage = 1;
    var breakingNewsResponse: NewsResponse? = null

    val searchNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData();
    var searchNewsPage = 1;
    var searchNewsResponse: NewsResponse? = null


    init {
        getBreakingNews("us")
    }

    fun getBreakingNews(countryCode: String) = viewModelScope.launch {
        breakingNews.postValue(Resource.Loading())

        try {
            if (hasInternetConnection()) {
                val response = newsRepository.getBreakingNews(countryCode, breakingNewsPage);
                breakingNews.postValue(handleBreakingNewsResponse(response))
            } else {
                breakingNews.postValue(Resource.Error("No Internet Connection"))
            }
        } catch (e: Exception) {
            when (e) {
                is IOException -> breakingNews.postValue(Resource.Error("Network Failure"))
                else -> breakingNews.postValue(Resource.Error("Conversion Failure"))
            }
        }


    }

    fun getSearchNews(searchQuery: String) = viewModelScope.launch {
        searchNews.postValue(Resource.Loading())
        try {
            if (hasInternetConnection()) {
                val response = newsRepository.searchQuery(searchQuery, searchNewsPage);
                searchNews.postValue(handleSearchNewsResponse(response))
            } else {
                searchNews.postValue(Resource.Error("No Internet Connection"))
            }
        } catch (e: Exception) {
            when (e) {
                is IOException -> searchNews.postValue(Resource.Error("Network Failure"))
                else -> searchNews.postValue(Resource.Error("Conversion Failure"))
            }
        }

    }

    private fun handleBreakingNewsResponse(response: Response<NewsResponse>): Resource<NewsResponse> {
        breakingNews.postValue(Resource.Loading())
        if (response.isSuccessful) {
            response.body()?.let { newsResponse ->
                breakingNewsPage++
                if (breakingNewsResponse == null) {
                    breakingNewsResponse = newsResponse
                } else {
                    //adding new response articles to the present response
                    breakingNewsResponse?.articles?.addAll(newsResponse.articles)
                }
                return Resource.Success(breakingNewsResponse ?: newsResponse)
            }
        }
        return Resource.Error(response.message())
    }

    private fun handleSearchNewsResponse(response: Response<NewsResponse>): Resource<NewsResponse> {
        searchNews.postValue(Resource.Loading())
        if (response.isSuccessful) {
            response.body()?.let { newsResponse ->
                searchNewsPage++
                if (searchNewsResponse == null) {
                    searchNewsResponse = newsResponse
                } else {
                    //adding new response articles to the present response
                    searchNewsResponse?.articles?.addAll(newsResponse.articles)
                }
                return Resource.Success(searchNewsResponse ?: newsResponse)
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

    private fun hasInternetConnection(): Boolean {
        /* val connectivityManager =
             getApplication<NewsApplication>().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
         if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
             val activeNetwork = connectivityManager.activeNetwork ?: return false
             val capabilities =
                 connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
             return when {
                 capabilities.hasCapability(NetworkCapabilities.TRANSPORT_WIFI) -> true
                 capabilities.hasCapability(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                 capabilities.hasCapability(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                 else -> false
             }
         } else {
             connectivityManager.activeNetworkInfo?.run {
                 return when (type) {
                     TYPE_WIFI -> true
                     TYPE_MOBILE -> true
                     TYPE_ETHERNET -> true
                     else -> false
                 }
             }
         }
         return false*/

        return true
    }
}
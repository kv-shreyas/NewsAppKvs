package com.androiddevs.newsapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.androiddevs.newsapp.repository.NewsRepository

class NewsViewModelProviderFactory(val newsRepository: NewsRepository):ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NewsViewModel(newsRepository) as T
    }


    //    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
//        return NewsViewModel(newsRepository) as T
//    }
  /*  override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewsViewModel::class.java)) {
            return NewsViewModel(newsRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }*/
}
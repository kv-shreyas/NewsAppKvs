package com.shreyasDev.newsapp.ui

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.shreyasDev.newsapp.repository.NewsRepository

class NewsViewModelProviderFactory(val app: Application, val newsRepository: NewsRepository) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NewsViewModel(app, newsRepository) as T
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
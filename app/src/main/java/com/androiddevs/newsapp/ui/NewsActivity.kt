package com.androiddevs.newsapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.androiddevs.newsapp.R
import com.androiddevs.newsapp.databinding.ActivityNewsBinding
import com.androiddevs.newsapp.db.ArticleDatabase
import com.androiddevs.newsapp.repository.NewsRepository

class NewsActivity : AppCompatActivity() {

    lateinit var viewModel: NewsViewModel
    private lateinit var binding: ActivityNewsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val newsRepository = NewsRepository(ArticleDatabase(this))
        val viewModelProviderFactory = NewsViewModelProviderFactory(newsRepository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(NewsViewModel::class.java)
//        binding.bottomNavigationView.setupWithNavController(binding.newsNavHostFragment.findNavController())

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.newsNavHostFragment) as NavHostFragment
//        val navHostFragment = binding.newsNavHostFragment as NavHostFragment
        val navController = navHostFragment.navController
        navController.setGraph(R.navigation.news_nav_graph)
        binding.bottomNavigationView.setupWithNavController(navController)

    }
}

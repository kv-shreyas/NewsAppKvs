package com.androiddevs.newsapp.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.androiddevs.newsapp.R
import com.androiddevs.newsapp.databinding.FragmentSearchNewsBinding
import com.androiddevs.newsapp.ui.NewsActivity
import com.androiddevs.newsapp.ui.NewsViewModel

class SearchNewsFragment : Fragment(R.layout.fragment_search_news) {

    lateinit var viewModel: NewsViewModel
    private lateinit var binding: FragmentSearchNewsBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSearchNewsBinding.inflate(layoutInflater)
        viewModel = (activity as NewsActivity).viewModel

    }
}
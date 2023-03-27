package com.androiddevs.newsapp.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.androiddevs.newsapp.R
import com.androiddevs.newsapp.adapters.NewsAdapter
import com.androiddevs.newsapp.databinding.FragmentBreakingNewsBinding
import com.androiddevs.newsapp.ui.NewsActivity
import com.androiddevs.newsapp.ui.NewsViewModel
import com.androiddevs.newsapp.util.Resource

class BreakingNewsFragment : Fragment(R.layout.fragment_breaking_news) {

    lateinit var viewModel: NewsViewModel
    lateinit var newsAdapter: NewsAdapter
    private val TAG :String = "BreakingNewsFragment"
    private lateinit var binding: FragmentBreakingNewsBinding


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as NewsActivity).viewModel
        setUpRecyclerView()
        viewModel.breakingNews.observe(viewLifecycleOwner, Observer { response->
            when(response){
                is Resource.Success->{
                    hideProgressBar()
                    response.data?.let { newsResponse->
                        newsAdapter.differ.submitList(newsResponse.articles)
                    }
                }
                is Resource.Error->{
                    hideProgressBar()
                    response.message?.let { message->
                        Log.i(TAG, "An Error Occured:  $message")
                    }
                }
                is Resource.Loading->{
                    showProgressBar()
                }
            }
        })

    }
    private fun hideProgressBar(){
        binding.paginationProgressBar.visibility = View.INVISIBLE
    }

    private fun showProgressBar(){
        binding.paginationProgressBar.visibility = View.VISIBLE
    }
    private fun setUpRecyclerView(){
        newsAdapter=NewsAdapter()
        binding.rvBreakingNews.apply {
            adapter= newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }

    }
}
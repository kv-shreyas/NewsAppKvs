package com.androiddevs.newsapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androiddevs.newsapp.R
import com.androiddevs.newsapp.adapters.NewsAdapter
import com.androiddevs.newsapp.databinding.FragmentSavedNewsBinding
import com.androiddevs.newsapp.ui.NewsActivity
import com.androiddevs.newsapp.ui.NewsViewModel
import com.google.android.material.snackbar.Snackbar

class SavedNewsFragment : Fragment() {
    lateinit var viewModel: NewsViewModel
    private lateinit var binding: FragmentSavedNewsBinding
    private lateinit var newsAdapter: NewsAdapter
    private val TAG: String = this.javaClass.name.toString()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSavedNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as NewsActivity).viewModel

        setUpRecyclerView()
        newsAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("article", it)
            }
            findNavController().navigate(R.id.action_savedNewsFragment_to_articleFragment, bundle)
        }

        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val article = newsAdapter.differ.currentList[position]
                viewModel.deleteArticle(article)
                Snackbar.make(view, "Successfully deleted", Snackbar.LENGTH_SHORT).apply {
                    setAction("undo") {
                        viewModel.saveArticle(article)
                    }
                    show()
                }
            }

        }

        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(binding.rvSavedNews)
        }
        viewModel.getSavedNews().observe(viewLifecycleOwner) { articles ->
            newsAdapter.differ.submitList(articles)
        }

    }

    private fun setUpRecyclerView() {
        newsAdapter = NewsAdapter()
        binding.rvSavedNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

}
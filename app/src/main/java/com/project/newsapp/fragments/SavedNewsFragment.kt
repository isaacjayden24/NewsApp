package com.project.newsapp.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.coroutineScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.project.newsapp.R
import com.project.newsapp.adapter.SavedNewsAdapter
import com.project.newsapp.models.Article
import com.project.newsapp.ui.NewsViewModel
import kotlinx.coroutines.launch



class SavedNewsFragment : Fragment() {

    private lateinit var savedNewsAdapter: SavedNewsAdapter
    private lateinit var recyclerView: RecyclerView




    private val newsViewModel: NewsViewModel by viewModels {
        ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
    }




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_saved, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.savedNewsRecyclerView)
        setUpRecyclerView()


        // when news item is clicked it will open on an external browser
        savedNewsAdapter.setOnItemClickListener { article ->
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(article.url))
            startActivity(intent)
        }


        // when news item is long pressed it deletes the news item
        savedNewsAdapter.setOnItemLongClickListener { article ->
            deleteArticle(article)
        }

        //observe live data for all articles and pass to recyclerview
        newsViewModel.allArticles.observe(viewLifecycleOwner) { articles ->
            savedNewsAdapter.submitList(articles)
        }

        //get all articles from a coroutine

        lifecycle.coroutineScope.launch {
            getAllArticles()
        }



    }





    //function to set up recyclerView
    private fun setUpRecyclerView() {
        savedNewsAdapter = SavedNewsAdapter()
        recyclerView.adapter = savedNewsAdapter
        recyclerView.layoutManager = LinearLayoutManager(context)
    }

    //function to get all articles
   suspend  fun getAllArticles() {
        newsViewModel.getAllArticles()
    }


    // function to delete an article
    private fun deleteArticle(article: Article) {
        newsViewModel.deleteArticle(article)
    }

}
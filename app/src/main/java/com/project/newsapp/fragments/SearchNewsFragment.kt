package com.project.newsapp.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.project.newsapp.NewsApp
import com.project.newsapp.R
import com.project.newsapp.adapter.NewsAdapter
import com.project.newsapp.ui.NewsViewModel
import com.project.newsapp.ui.NewsViewModelFactory
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch



/**
 * A simple [Fragment] subclass.
 * Use the [SearchNewsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SearchNewsFragment : Fragment() {

    private lateinit var newsAdapter: NewsAdapter // Declare the news  adapter here
    private  lateinit var recyclerView: RecyclerView // Declare the recylerview here
    private lateinit var searchEditText: EditText // Declare the search edit text here
    private var debounceJob: Job? = null

    private val newsViewModel: NewsViewModel by viewModels {
        NewsViewModelFactory((requireActivity().application as NewsApp).newsRepository)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        searchEditText=view.findViewById(R.id.searchNews)
        recyclerView = view.findViewById(R.id.searchRecyclerView)


        val spinner: Spinner = view.findViewById(R.id.spinner)

        // Set up adapter
        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line,
            newsViewModel.categories
        )
        spinner.adapter = adapter

        // Handle item selection
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedItem = parent.getItemAtPosition(position).toString()
               searchNewsFilter(selectedItem)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Handle case where nothing is selected
            }
        }


        setUpRecyclerView()




        // Add text watcher to implement live search
        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                debounceJob?.cancel() // Cancel any ongoing debounce
                debounceJob = lifecycleScope.launch {
                    delay(300) // Debounce delay (300ms)
                    val query = s.toString().trim()
                    if (query.isNotEmpty()) {
                        newsViewModel.searchNews(query) // Trigger search
                    } else {
                       newsAdapter.submitList(emptyList()) // Clear results for empty input
                    }
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })





        newsViewModel.searchNews.observe(viewLifecycleOwner) { newsResponse ->
            newsAdapter.submitList(newsResponse.articles)
        }

    }

    private fun setUpRecyclerView() {
        newsAdapter = NewsAdapter()
        recyclerView.adapter = newsAdapter
        recyclerView.layoutManager = LinearLayoutManager(context)
    }


    private fun searchNewsFilter(selectedItem: String){
        val searchQuery = searchEditText.text.toString()
        newsViewModel.searchNewsSorting(searchQuery , sortBy = selectedItem)



    }

}
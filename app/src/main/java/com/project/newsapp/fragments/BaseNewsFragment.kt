package com.project.newsapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.tabs.TabLayout
import com.project.newsapp.NewsApp
import com.project.newsapp.R
import com.project.newsapp.adapter.NewsAdapter
import com.project.newsapp.ui.NewsViewModel
import com.project.newsapp.ui.NewsViewModelFactory


/**
 * A simple [Fragment] subclass.
 * Use the [BaseNewsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BaseNewsFragment : Fragment() {


    // initialize adapter
    private lateinit var newsAdapter: NewsAdapter // Declare the news  adapter here
    private  lateinit var recyclerView: RecyclerView // Declare the recylerview here
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout //declare the swiperefreshLayout

    private lateinit var tabLayout: TabLayout  //Declare the tablayout here






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
        return inflater.inflate(R.layout.fragment_basenews, container, false)
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.newsRecyclerView)
        tabLayout = view.findViewById(R.id.tabLayout)
        swipeRefreshLayout=view.findViewById(R.id.swipeRefreshLayout)


        setUpRecyclerView()


       // setupSwipeRefresh()
        swipeRefreshLayout.setOnRefreshListener {
            val currentCategory = tabLayout.getTabAt(tabLayout.selectedTabPosition)?.text.toString()
            newsViewModel.getBaseNewsRefresh(currentCategory, "us", isRefreshing = true)
        }

        //observe base news categories for refreshing
        newsViewModel.baseNewsRefresh.observe(viewLifecycleOwner) { articles ->
            newsAdapter.submitList(articles) // TODO : Added code here

        }


         //  TODO : Added code here
        newsViewModel.isRefreshing.observe(viewLifecycleOwner) { isRefreshing ->
            swipeRefreshLayout.isRefreshing = isRefreshing == true // Stop animation
        }

        // launch base news on opening the app
        newsViewModel.getBaseNews("us")

        // observe the base news on launching the app
        newsViewModel.baseNews.observe(viewLifecycleOwner){articles ->
            articles?.let {
                newsAdapter.submitList(it)
            }
        }


        // recycler view pagination when user scrolls at the bottom
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val lastVisibleItem = layoutManager.findLastVisibleItemPosition()
                val totalItemCount = layoutManager.itemCount

                if (lastVisibleItem >= totalItemCount - 5 && !newsViewModel.isLoading) {
                    // Load more news when user is close to the bottom
                    newsViewModel.getBaseNews("us")
                }
            }
        })




        //observe the base news category selection
        newsViewModel.baseNewsCategory.observe(viewLifecycleOwner) { newsResponse ->
            newsAdapter.submitList(newsResponse.articles)

        }



        // handle tablayout selection
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                // Get the tab's text or position
                when (tab?.text) {
                    "All" -> newsViewModel.getBaseNews("us")
                    "Sports" -> performAction("Sports")
                    "Entertainment" -> performAction("Entertainment")
                    "Business" -> performAction("Business")
                    "Health" -> performAction("Health")
                    "Technology" -> performAction("Technology")
                    "Science" -> performAction("Science")
                }

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                // Handle unselected state if needed
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                // Handle reselection if needed
            }
        })

    }






    private fun setUpRecyclerView() {
        newsAdapter = NewsAdapter()
        recyclerView.adapter = newsAdapter
        recyclerView.layoutManager = LinearLayoutManager(context)



    }


    // function initialized on category selection
    private fun performAction(category: String) {
        newsViewModel.getBaseNewsCategory("us", category)
    }


}
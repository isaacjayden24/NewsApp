package com.project.newsapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.project.newsapp.models.Article
import com.project.newsapp.models.NewsResponse
import com.project.newsapp.repository.NewsRepository
import kotlinx.coroutines.launch


class NewsViewModel(val newsRepository: NewsRepository):ViewModel() {

    //map to hold list of categories by sortBy
    val categories = listOf("publishedAt", "relevancy", "popularity")

    // live data for the NewsResponse baseNews Fragment
    private val _baseNews = MutableLiveData<List<Article>>()
    val baseNews: LiveData<List<Article>> get() = _baseNews

    // live data for the  search  category BaseNews Fragment
    private val _baseNewsCategory = MutableLiveData<NewsResponse>()
    val baseNewsCategory: LiveData<NewsResponse> get() = _baseNewsCategory

    //live data for the search news fragment
    private val _searchNews = MutableLiveData<NewsResponse>()
    val searchNews: LiveData<NewsResponse> get() = _searchNews

    //live data for news base refreshing
    private val _baseNewsRefresh = MutableLiveData<List<Article>>()
    val baseNewsRefresh: LiveData<List<Article>> = _baseNewsRefresh

    //live data for fetching all articles from database
    private  val _allArticles=MutableLiveData<List<Article>>()
    val allArticles:LiveData<List<Article>>
        get()= _allArticles


    private val _isRefreshing = MutableLiveData<Boolean>()
    val isRefreshing: LiveData<Boolean> = _isRefreshing



    // base news variables for pagination
    var baseNewsPageNumber = 1 // increase count up to 3
    var baseNewsPageSize=20 //remain constant for all request
    var isLoading = false
    private var currentArticles = mutableListOf<Article>()

    private var currentArticlesRefresh= mutableListOf<Article>()



    var baseNewsPage = 1







    // function to fetch base news from API

    fun getBaseNews(countryCode: String) {

        if (isLoading) return

        isLoading = true
        viewModelScope.launch {
            try {
                val response = newsRepository.getBaseNews(countryCode, baseNewsPageNumber, baseNewsPageSize)

                // Append new articles to the existing list
                currentArticles.addAll(response.articles)
                _baseNews.postValue(currentArticles)

                baseNewsPageNumber++ // Increase page number for next request
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                isLoading = false
            }
        }
    }


    // function to fetch base news with refresh from API
    fun getBaseNewsRefresh(categorySearch: String, countryCode: String, isRefreshing: Boolean = false) {
        if (isLoading) return

        isLoading = true
        _isRefreshing.postValue(isRefreshing) //TODO : Added code here

        viewModelScope.launch {
            try {
                if (isRefreshing) {
                    baseNewsPageNumber =1 // Reset page number when refreshing
                }

                val response = newsRepository.getBaseNewsRefresh(categorySearch, baseNewsPageSize, countryCode, baseNewsPageNumber)

                val updatedArticles = if (isRefreshing) {
                    response.articles // Replace with fresh data
                } else {
                    currentArticlesRefresh + response.articles // Append to existing list
                }

                _baseNewsRefresh.postValue(updatedArticles) // Post new list to LiveData
                currentArticlesRefresh = updatedArticles.toMutableList() // Update stored list

                baseNewsPageNumber++ // Increase page number for next request
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                isLoading = false
                _isRefreshing.postValue(false) //TODO : Added code here
            }
        }
    }




    //function to fetch base news with category from API
    fun getBaseNewsCategory(countryCode: String,category:String) {
        viewModelScope.launch {
            try {
                val response = newsRepository.getNewsCategory(countryCode, category, baseNewsPage)
                _baseNewsCategory.postValue(response)
            } catch (e: Exception) {
                // Handle errors, e.g., no network or API issues

                e.printStackTrace()
            }


        }
    }

    //function to fetch the search news results  from API
    fun searchNews(searchQuery:String) {
        viewModelScope.launch {
            try {
                val response = newsRepository.searchNews(searchQuery, baseNewsPage)
                _searchNews.postValue(response)
            } catch (e: Exception) {
                // Handle errors, e.g., no network or API issues
                e.printStackTrace()
            }
        }
    }

    //function to fetch search news sorting from API
    fun searchNewsSorting(searchQuery: String, sortBy: String){
        viewModelScope.launch {
            try {
                val response = newsRepository.searchNewsSorting(searchQuery, sortBy, baseNewsPage)
                _searchNews.postValue(response)
            } catch (e: Exception) {
                // Handle errors, e.g., no network or API issues
                e.printStackTrace()
            }
            }
    }




    // function to insert news article
    fun insertArticle(article: Article) {
        viewModelScope.launch {
            newsRepository.insertArticle(article)
        }
    }

    //function to delete news article
    fun deleteArticle(article: Article) {
        viewModelScope.launch {
            newsRepository.deleteArticle(article)
            getAllArticles()
        }

    }

    //function to get all news articles
    suspend fun getAllArticles() {
        val response= newsRepository.getAllArticles()
        _allArticles.postValue(response)

    }









}



class NewsViewModelFactory(private val newsRepository: NewsRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NewsViewModel(newsRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
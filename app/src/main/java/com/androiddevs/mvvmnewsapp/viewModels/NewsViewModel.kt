package com.androiddevs.mvvmnewsapp.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.androiddevs.mvvmnewsapp.models.Article
import com.androiddevs.mvvmnewsapp.models.NewsResponse
import com.androiddevs.mvvmnewsapp.repository.NewsRepository
import com.androiddevs.mvvmnewsapp.utils.Resource

import kotlinx.coroutines.launch
import retrofit2.Response

class NewsViewModel(val repository : NewsRepository) : ViewModel() {

    val breakingNews : MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var breakingNewsPageNumber = 1
    var breakingNewsResponse : NewsResponse ? = null
    val searchNews : MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var searchNewsPageNumber = 1
    var searchNewsResponse : NewsResponse ? = null
    // we are not creating suspend fun cause to call it in breaking ews fragment
    // we have to use suspend fun and we should not use suspend fun in fragment
    // so to avoid this we use viewModelScope.launch to make it suspend

    init {
        getBreakingNews("in")
    }

    fun getBreakingNews(countryCode : String) = viewModelScope.launch {
        // here we are emitting the values to breaking news that our data is loading
        breakingNews.postValue(Resource.Loading())

        val response = repository.getAllArticles(countryCode,breakingNewsPageNumber)
        breakingNews.postValue(handleBreakingNewsResponse(response))

    }

    fun searchNews(searchQuery : String) = viewModelScope.launch {
        searchNews.postValue(Resource.Loading())

        val response = repository.searchArticles(searchQuery,searchNewsPageNumber)
        searchNews.postValue(handleSearchNewsResponse(response))
    }

    // here we have added pagination in it because if not, it will put too much pressure on main thread
    // we have done pagination in viewModel because it will remain as it is even if the phone is rotated
    fun handleBreakingNewsResponse(response : Response<NewsResponse>) : Resource<NewsResponse>{
        if(response.isSuccessful)
        {
            response.body()?.let { resultResponse ->
                breakingNewsPageNumber++
                // we incremented the breakingNewsPageNumber
                // and we check whether it is null or not
                // if it is null we will simply assign it the response that came from api
                // and if not null then we append the lists and add them at end
                // at last we return breakingNewsResponse if it is not null otherwise resultResponse
                if(breakingNewsResponse == null) {
                    breakingNewsResponse = resultResponse
                }
                else
                {
                    val newList = resultResponse.articles
                    val oldList = breakingNewsResponse?.articles

                    oldList?.addAll(newList)
                }

                return Resource.Success(breakingNewsResponse ?: resultResponse)
            }
        }
        return Resource.Error(response.message())

    }

    fun handleSearchNewsResponse(response : Response<NewsResponse>) : Resource<NewsResponse>{
        if(response.isSuccessful)
        {
            response.body()?.let { resultResponse ->
                searchNewsPageNumber++
                // we incremented the breakingNewsPageNumber
                // and we check whether it is null or not
                // if it is null we will simply assign it the response that came from api
                // and if not null then we append the lists and add them at end
                // at last we return breakingNewsResponse if it is not null otherwise resultResponse
                if(searchNewsResponse == null) {
                    searchNewsResponse = resultResponse
                }
                else
                {
                    val newList = resultResponse.articles
                    val oldList = searchNewsResponse?.articles

                    oldList?.addAll(newList)
                }

                return Resource.Success(searchNewsResponse ?: resultResponse)
            }
        }
        return Resource.Error(response.message())

    }

    fun saveArticle(article:Article) = viewModelScope.launch {
        repository.upsert(article)
    }

    fun getSavedArticle() = repository.getSavedNews()

    fun deleteArticle(article: Article) = viewModelScope.launch {
        repository.deleteArticle(article)
    }

}
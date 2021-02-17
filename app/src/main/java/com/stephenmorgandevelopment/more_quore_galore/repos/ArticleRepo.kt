package com.stephenmorgandevelopment.more_quore_galore.repos

import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.util.Log
import androidx.lifecycle.LiveData
import com.stephenmorgandevelopment.more_quore_galore.doas.ClientDao
import com.stephenmorgandevelopment.more_quore_galore.models.Client
import com.stephenmorgandevelopment.more_quore_galore.models.SimpleClient
import com.stephenmorgandevelopment.more_quore_galore.api.ClientService
import com.stephenmorgandevelopment.more_quore_galore.api.NewsService
import com.stephenmorgandevelopment.more_quore_galore.doas.ArticlesDao
import com.stephenmorgandevelopment.more_quore_galore.models.Article
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ArticleRepo @Inject constructor(
    val connectivityManager: ConnectivityManager,
    val newsService: NewsService?,
    val articlesDao: ArticlesDao
) {
    private var lastFetchSaved: Long = -1
    private val apiKey = "53baf97b616143159bdaa266d1716ca2"

    suspend fun getAll(
        forceUpdate: Boolean = false
    ): LiveData<List<Article>> {

        if(forceUpdate) {
            lastFetchSaved = -1
        }

        if(newsService != null) {
            refreshList()
        }

        return articlesDao.loadAll()
    }

    @Suppress("BlockingMethodInNonBlockingContext")
    private suspend fun refreshList() {
        if (needsRefreshed() && isConnected()) {
            withContext(Dispatchers.IO) {
                val response =
                    newsService!!.getRecentHeadlines("us", apiKey).execute()

                processResponse(response)
                lastFetchSaved = System.currentTimeMillis()
            }
        }
    }

    private suspend fun processResponse(response: Response<List<Article>>) {
        if (response.isSuccessful) {
            articlesDao.updateDatabase(response.body()!!)
        } else {
            val error = response.errorBody()
            Log.d("ClientRepo", error.toString())
        }
    }

    private fun needsRefreshed(): Boolean {
        val timeout: Long = 300000
        return ((System.currentTimeMillis() - lastFetchSaved) > timeout)
    }

    private fun isConnected(): Boolean {
        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
        return activeNetwork?.isConnectedOrConnecting == true
    }
}
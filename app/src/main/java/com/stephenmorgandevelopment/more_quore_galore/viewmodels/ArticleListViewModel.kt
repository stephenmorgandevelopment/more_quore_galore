package com.stephenmorgandevelopment.more_quore_galore.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.stephenmorgandevelopment.more_quore_galore.models.Article
import com.stephenmorgandevelopment.more_quore_galore.repos.ArticleRepo

class ArticleListViewModel @ViewModelInject constructor(
    private val articleRepo: ArticleRepo
) : ViewModel() {
    private var _articles: LiveData<List<Article>>
    val articles : LiveData<List<Article>> get() = _articles

    init {
        _articles = liveData {
            emitSource(articleRepo.getAll())
        }
    }

    suspend fun refreshList() {
        _articles = liveData {
            emitSource(articleRepo.getAll(true))
        }
    }

}
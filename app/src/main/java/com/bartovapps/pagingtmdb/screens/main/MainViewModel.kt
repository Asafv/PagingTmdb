package com.bartovapps.pagingtmdb.screens.main

import androidx.lifecycle.*
import androidx.paging.PagedList
import com.bartovapps.pagingtmdb.data.Repository
import timber.log.Timber

class MainViewModel(private val repository: Repository) : ViewModel() , LifecycleObserver {

    val moviesPagedList : LiveData<PagedList<MovieListItem>> = repository.moviesList

    init {
        loadMovies()
    }

    fun refreshMovies(){
        repository.refreshMovies()
    }


    private fun loadMovies(){
        repository.loadMovies()
    }

    override fun onCleared() {
        super.onCleared()
        Timber.i("onCleared: ")
        repository.dispose()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume(){
        Timber.i("onResume")
    }
}
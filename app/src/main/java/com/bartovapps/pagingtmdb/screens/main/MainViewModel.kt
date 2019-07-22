package com.bartovapps.pagingtmdb.screens.main

import android.arch.lifecycle.*
import android.arch.paging.PagedList
import com.bartovapps.pagingtmdb.data.Repository
import com.bartovapps.pagingtmdb.network.model.response.Movie
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
package com.bartovapps.pagingtmdb.screens.main

import androidx.lifecycle.*
import androidx.paging.PagedList
import com.bartovapps.pagingtmdb.data.Repository
import com.bartovapps.pagingtmdb.network.model.response.Movie
import timber.log.Timber

class MainViewModel(val repository: Repository) : ViewModel(), LifecycleObserver {

    val moviesPagedList: LiveData<PagedList<Movie>> = repository.moviesList


}
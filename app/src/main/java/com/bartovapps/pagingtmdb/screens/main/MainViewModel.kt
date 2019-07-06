package com.bartovapps.pagingtmdb.screens.main

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import android.arch.paging.PagedList
import com.bartovapps.pagingtmdb.data.Repository
import com.bartovapps.pagingtmdb.network.model.response.Movie

class MainViewModel(repository: Repository) : ViewModel() {

    val moviesPagedList : LiveData<PagedList<MovieListItem>> = repository.moviesList
}
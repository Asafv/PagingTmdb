package com.bartovapps.pagingtmdb.screens.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.bartovapps.pagingtmdb.data.Repository
import com.bartovapps.pagingtmdb.network.model.response.Movie

class MainViewModel(val repository: Repository) : ViewModel() {

    val moviesPagedList : LiveData<PagedList<Movie>> = repository.moviesList

}
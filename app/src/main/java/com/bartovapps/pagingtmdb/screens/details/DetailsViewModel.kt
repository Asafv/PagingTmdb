package com.bartovapps.pagingtmdb.screens.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.bartovapps.pagingtmdb.data.Repository
import com.bartovapps.pagingtmdb.network.model.response.Movie

class DetailsViewModel(private val repository: Repository, id: Int) : ViewModel() {

    var detailsLiveData : LiveData<Movie> = repository.getMovieById(id)

    fun loadMovieDetails(id: Int) {

    }

}
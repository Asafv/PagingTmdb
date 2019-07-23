package com.bartovapps.pagingtmdb.data

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.bartovapps.pagingtmdb.network.ApiService
import com.bartovapps.pagingtmdb.network.apis.TmdbEndpoint
import com.bartovapps.pagingtmdb.network.model.response.DetailsApiResponse
import com.bartovapps.pagingtmdb.network.model.response.Movie
import io.reactivex.Single

object Repository {
    private val endpoint : TmdbEndpoint = ApiService.getEndPoint()
    val moviesList: LiveData<PagedList<Movie>>

    init {
        val config = PagedList.Config.Builder()
            .setPageSize(20).
                setEnablePlaceholders(true).setPrefetchDistance(5).setInitialLoadSizeHint(1).build()

        moviesList = LivePagedListBuilder(TmdbDsFactory(endpoint), config).build()
    }


    fun getMovieById(id: Int): Single<DetailsApiResponse> {
        return endpoint.getMovieDetails(id)
    }
}
package com.bartovapps.pagingtmdb.data

import android.arch.lifecycle.LiveData
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import com.bartovapps.pagingtmdb.network.apis.TmdbEndpoint
import com.bartovapps.pagingtmdb.network.model.response.ApiResponse
import com.bartovapps.pagingtmdb.network.model.response.Movie
import io.reactivex.Flowable

class Repository(endpoint: TmdbEndpoint) {

    val moviesList: LiveData<PagedList<Movie>>

    init {
        val config = PagedList.Config.Builder()
            .setPageSize(20).
                setEnablePlaceholders(true).
                setPrefetchDistance(5).
                setInitialLoadSizeHint(1).build()

        moviesList = LivePagedListBuilder(TmdbDsFactory(endpoint), config).build()
    }

}
package com.bartovapps.pagingtmdb.data

import android.arch.lifecycle.LiveData
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import com.bartovapps.pagingtmdb.data.persistance.MoviesDao
import com.bartovapps.pagingtmdb.network.apis.TmdbEndpoint
import com.bartovapps.pagingtmdb.screens.main.MovieListItem

class Repository(private val endpoint: TmdbEndpoint, private val dao : MoviesDao?) {

    lateinit var moviesList: LiveData<PagedList<MovieListItem>>
    companion object {
        const val PAGE_SIZE = 20
    }

    init {
        val config = PagedList.Config.Builder()
            .setPageSize(PAGE_SIZE).
                setEnablePlaceholders(true).
                setPrefetchDistance(5).
                setInitialLoadSizeHint(1).build()


        dao?.let {
            val dataSourceFactory = dao.allItemsName().
                mapByPage { input -> input.map { it -> MovieListItem(it.id, it.title, it.voteAverage, it.posterPath, it.page) } }
            moviesList = LivePagedListBuilder(dataSourceFactory, config).setBoundaryCallback(AppBoundaryCallback(endpoint, dao)).build()
        }
    }

}
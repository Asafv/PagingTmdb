package com.bartovapps.pagingtmdb.data

import android.arch.lifecycle.LiveData
import android.arch.paging.DataSource
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import com.bartovapps.pagingtmdb.data.persistance.MoviesDao
import com.bartovapps.pagingtmdb.network.apis.TmdbEndpoint
import com.bartovapps.pagingtmdb.screens.main.MovieListItem
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class Repository(private val endpoint: TmdbEndpoint, private val dao : MoviesDao?) {

    lateinit var moviesList: LiveData<PagedList<MovieListItem>>
    lateinit var boundaryCallback: AppBoundaryCallback
    private val disposables = CompositeDisposable()
    companion object {
        const val PAGE_SIZE = 10
    }

    init {
        val config = PagedList.Config.Builder()
            .setPageSize(PAGE_SIZE).
                setEnablePlaceholders(true).
                setPrefetchDistance(2).
                setInitialLoadSizeHint(1).build()


        dao?.let {
            boundaryCallback = AppBoundaryCallback(endpoint, dao)
            val  dataSourceFactory = dao.allItemsName().
                mapByPage { input -> input.map { it -> MovieListItem(it.id, it.title, it.voteAverage, it.posterPath, it.page, it.releaseDate) } }
            moviesList = LivePagedListBuilder(dataSourceFactory, config).setBoundaryCallback(boundaryCallback).build()
        }
    }

    fun dispose(){
        boundaryCallback.dispose()
        disposables.clear()
    }


    fun refreshMovies(){
        clearCache()
    }

    private fun clearCache(){
        val  disposable = Observable.fromCallable{
            dao?.deleteAll()
        }.subscribeOn(Schedulers.io()).subscribe()

        disposables.add(disposable)
    }
}
package com.bartovapps.pagingtmdb.data

import android.arch.paging.PagedList
import com.bartovapps.pagingtmdb.data.persistance.MoviesDao
import com.bartovapps.pagingtmdb.network.apis.TmdbEndpoint
import com.bartovapps.pagingtmdb.network.model.response.ApiResponse
import com.bartovapps.pagingtmdb.network.model.response.Movie
import com.bartovapps.pagingtmdb.screens.main.MovieListItem
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class AppBoundaryCallback(private val endpoint: TmdbEndpoint, private val dao: MoviesDao) : PagedList.BoundaryCallback<MovieListItem>() {

    private val disposables = CompositeDisposable()
    companion object {
        const val  INITIAL_PAGE = 1
    }
    override fun onZeroItemsLoaded() {
        Timber.i("onZeroItemsLoaded: Loading first page..")
        super.onZeroItemsLoaded()
        val disposable = endpoint.getTopRatedMovies(INITIAL_PAGE).subscribeOn(Schedulers.io()).subscribe({ it ->
            it.results.forEach { it.page = INITIAL_PAGE }
            insertItemsToDatabase(it)
        }, {
            Timber.e(it.cause)
        })

        disposables.add(disposable)

    }

    override fun onItemAtEndLoaded(itemAtEnd: MovieListItem) {
        super.onItemAtEndLoaded(itemAtEnd)
        Timber.i("onItemAtEndLoaded: lastItemPage ${itemAtEnd.page}")
        val pageToLoad = itemAtEnd.page + 1

        val disposable = endpoint.getTopRatedMovies(pageToLoad).subscribeOn(Schedulers.io()).subscribe({ it ->
            it.results.forEach { it.page = pageToLoad }
            insertItemsToDatabase(it)
        }, {
            Timber.e(it.cause)
        })

        disposables.add(disposable)
    }

    private fun insertItemsToDatabase(it: ApiResponse) {
        Timber.i("insertItemsToDatabase: insert ${it.results.size} items, from page: ${it.results[0].page}")
        dao.insert(it.results)
    }

    fun dispose(){
        disposables.clear()
    }
}
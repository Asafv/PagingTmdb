package com.bartovapps.pagingtmdb.data

import android.nfc.tech.MifareUltralight.PAGE_SIZE
import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.bartovapps.pagingtmdb.data.persistance.AppDatabase
import com.bartovapps.pagingtmdb.data.persistance.MoviesDao
import com.bartovapps.pagingtmdb.network.ApiService
import com.bartovapps.pagingtmdb.network.apis.TmdbEndpoint
import com.bartovapps.pagingtmdb.network.model.response.DetailsApiResponse
import com.bartovapps.pagingtmdb.network.model.response.Movie
import com.bartovapps.pagingtmdb.screens.main.MovieListItem
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.util.function.Consumer

class Repository private constructor(private val dao: MoviesDao) {
    private val endpoint : TmdbEndpoint = ApiService.getEndPoint()
    private var boundaryCallback: AppBoundaryCallback
    private val disposables = CompositeDisposable()
    lateinit var moviesList: LiveData<PagedList<MovieListItem>>

    companion object {
        private var INSTANCE: Repository? = null
        fun getInstance(moviesDao: MoviesDao): Repository {
            if (INSTANCE == null) {
                INSTANCE = Repository(
                    dao = moviesDao)
            }
            return INSTANCE as Repository
        }

        const val PAGE_SIZE = 10

    }

    init {
        val config = PagedList.Config.Builder()
            .setPageSize(PAGE_SIZE).
                setEnablePlaceholders(true).
                setPrefetchDistance(2).
                setInitialLoadSizeHint(2).build()


        dao.let {
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


    //This for explicit loading to load when opening the screen
    fun loadMovies(){
        val disposable = endpoint.getTopRatedMovies(1).subscribeOn(Schedulers.io()).subscribe({
            dao.insert(it.results)
        }, {
            Timber.e(it.cause)
        })

        disposables.add(disposable)
    }

    private fun clearCache(){
        val  disposable = Observable.fromCallable{
            dao.deleteAll()
        }.subscribeOn(Schedulers.io()).subscribe()

        disposables.add(disposable)
    }



    fun getMovieById(id: Int): LiveData<Movie> {
        return dao.getItemById(id).also {
            endpoint.getMovieDetails(id).map { t: DetailsApiResponse ->
                it.value?.backdrop_path = t.backdrop_path
                it.value?.runtime = t.runtime
                it.value
            }.subscribeOn(Schedulers.io()).subscribe ({ t ->
                t?.let {
                    dao.update(it)
                }
            }, {
                Timber.e(it.cause)
            })
        }
    }
}
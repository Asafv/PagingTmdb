package com.bartovapps.pagingtmdb.data

import androidx.paging.PageKeyedDataSource
import com.bartovapps.pagingtmdb.network.apis.TmdbEndpoint
import com.bartovapps.pagingtmdb.network.model.response.Movie
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class TmdbDataSource(val endpoint: TmdbEndpoint) : PageKeyedDataSource<Int, Movie>() {

    val dispsables = CompositeDisposable()

    companion object {
        const val FIRST_PAGE = 1
    }

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Movie>
    ) {
        val disposable = endpoint.getTopRatedMovies(FIRST_PAGE).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe({ apiResponse ->
                Timber.i("loadInitial: got: ${apiResponse.results.size} results")
                callback.onResult(apiResponse.results, FIRST_PAGE, FIRST_PAGE + 1)
            }, { e ->
                Timber.e("There was an error: ${e.message}, ${e.cause}")
            })

        dispsables.add(disposable)
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        Timber.i("loadAfter: page: ${params.key}")
        val disposable = endpoint.getTopRatedMovies(params.key).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe({ apiResponse ->
                Timber.i("loadAfter: got: ${apiResponse.results.size} results")
                callback.onResult(apiResponse.results, params.key + 1)
            }, { e ->
                Timber.e("There was an error: ${e.message}, ${e.cause}")
            })

        dispsables.add(disposable)
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        Timber.i("loadBefore: page: ${params.key}")

        val disposable = endpoint.getTopRatedMovies(params.key).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe({ apiResponse ->
                Timber.i("loadBefore: got: ${apiResponse.results.size} results")
                val nextKey =
                    if (params.key == FIRST_PAGE) {
                        null
                    } else {
                        params.key - 1
                    }

                callback.onResult(apiResponse.results, nextKey)
            }, { e ->
                Timber.e("There was an error: ${e.message}, ${e.cause}")
            })

        dispsables.add(disposable)
    }
}
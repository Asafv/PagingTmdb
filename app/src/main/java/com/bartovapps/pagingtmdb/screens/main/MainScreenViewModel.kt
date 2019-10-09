package com.bartovapps.pagingtmdb.screens.main

import androidx.lifecycle.LifecycleObserver
import com.bartovapps.pagingtmdb.data.Repository
import com.bartovapps.pagingtmdb.mvvm_core.BaseViewModel
import com.bartovapps.pagingtmdb.network.model.response.ApiResponse
import com.bartovapps.pagingtmdb.network.model.response.Movie
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class MainScreenViewModel(private val repository: Repository) :
    BaseViewModel<MainScreenViewModel.MainScreenState, MainScreenViewModel.MainScreenEvent>(),
    LifecycleObserver {

    override fun handleInputEvent(event: MainScreenEvent) {
        Timber.i("handleInputEvent: ${event.javaClass.simpleName}")
        when (event) {
            is MainScreenEvent.LoadTopRatedMovies -> {
                loadTopRatedMovies()
            }
            is MainScreenEvent.OnMovieItemClicked -> {
                handleMovieItemClicked(event.id)
            }
        }
    }

    private fun handleMovieItemClicked(id: Int) {
        onNext(MainScreenState.NavigateToDetails(id))
    }

    private fun loadTopRatedMovies() {
        val disposable = repository.getTopRatedMovies().subscribeOn(Schedulers.io()).doOnSubscribe {
            onLoading()
        }.observeOn(AndroidSchedulers.mainThread())
            .subscribe({ handleApiResponse(it) },
                { onError(it) })
        disposables.add(disposable)
    }

    private fun handleApiResponse(response: ApiResponse?) {
        response?.let {
            onNext(MainScreenState.OnMoviesLoaded(movies = it.results))
        }
    }


    sealed class MainScreenState {
        class OnMoviesLoaded(val movies: List<Movie>) : MainScreenState()
        class NavigateToDetails(val movieId : Int) : MainScreenState()
    }

    sealed class MainScreenEvent {
        object LoadTopRatedMovies : MainScreenEvent()
        class OnMovieItemClicked(val id: Int) : MainScreenEvent()
    }
}

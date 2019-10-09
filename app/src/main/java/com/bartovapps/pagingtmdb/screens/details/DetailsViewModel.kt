package com.bartovapps.pagingtmdb.screens.details

import com.bartovapps.pagingtmdb.data.Repository
import com.bartovapps.pagingtmdb.mvvm_core.BaseViewModel
import com.bartovapps.pagingtmdb.network.model.response.DetailsApiResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class DetailsViewModel(private val repository: Repository) :
    BaseViewModel<DetailsViewModel.DetailsScreenState, DetailsViewModel.DetailsScreenEvent>() {


    override fun handleInputEvent(event: DetailsScreenEvent) {
        when (event) {
            is DetailsScreenEvent.LoadMovieDetails -> {
                loadMovieDetails(event.movieId)
            }
        }
    }

    private fun loadMovieDetails(id: Int) {
        val disposable =
            repository.getMovieById(id).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).doOnSubscribe {
                    onLoading()
                }
                .subscribe({ t ->
                    onNext(DetailsScreenState.DetailsLoaded(t))
                },
                    {
                        onError(it)
                    })

        disposables.add(disposable)
    }


    sealed class DetailsScreenState {
        class DetailsLoaded(val details: DetailsApiResponse) : DetailsScreenState()
    }

    sealed class DetailsScreenEvent {
        class LoadMovieDetails(val movieId: Int) : DetailsScreenEvent()
    }
}
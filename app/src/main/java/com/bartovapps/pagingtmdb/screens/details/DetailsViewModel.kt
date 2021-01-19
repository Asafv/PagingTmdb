package com.bartovapps.pagingtmdb.screens.details

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bartovapps.pagingtmdb.data.Repository
import com.bartovapps.pagingtmdb.network.model.response.DetailsApiResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class DetailsViewModel(private val repository: Repository) : ViewModel() {

    val detailsLiveData = MutableLiveData<DetailsApiResponse?>()

    private val disposables = CompositeDisposable()

    fun loadMovieDetails(id: Int) {
        val disposable =
            repository.getMovieById(id).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread())
                .subscribe({ t ->
                    detailsLiveData.postValue(t)
                    Timber.i("Got movie details: $t")
                },
                    {
                        Timber.e("There was an error: ${it.cause}")
                    })

        disposables.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}
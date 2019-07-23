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
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers

class DetailsViewModel(private val repository: Repository) : ViewModel() {

    private val detailsLiveData = MutableLiveData<DetailsApiResponse?>()
    private val data: MutableLiveData<DetailsApiResponse?>
    get() {
        return detailsLiveData
    }
    private val disposables = CompositeDisposable()

    init {

    }

    fun loadMovieDetails(id: Int){
        val disposable = repository.getMovieById(id).
            subscribeOn(Schedulers.io()).
            observeOn(AndroidSchedulers.mainThread()).
            subscribe(Consumer<DetailsApiResponse> { t -> detailsLiveData.postValue(t) }, object : Consumer<Throwable>{
                override fun accept(t: Throwable?) {
                }
            })

        disposables.add(disposable)
    }

}
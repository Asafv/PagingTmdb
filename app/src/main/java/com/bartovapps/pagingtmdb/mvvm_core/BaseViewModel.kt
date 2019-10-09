package com.bartovapps.pagingtmdb.mvvm_core

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject

abstract class BaseViewModel<T, R> : ViewModel() {

    private var eventsSubscription : Disposable? = null
    protected val disposables : CompositeDisposable = CompositeDisposable()

    init {
        initEventsDispatcher()
    }

    private fun initEventsDispatcher() {
        eventsSubscription?.let {
            if(!it.isDisposed){
                it.dispose()
            }
            eventsSubscription = null
        }

    }

    private val state = MutableLiveData<MvvmState<T>>().apply {
        value = MvvmState.Init
    }

    val stateStream : LiveData<MvvmState<T>>
    get() = state


    protected fun onInit(){
        publish(MvvmState.Init)
    }

    protected fun onNext(value : T){
        publish(MvvmState.Next(data = value))
    }

    protected fun onLoading(){
        publish(newState = MvvmState.Loading)
    }

    protected fun onError(e : Throwable){
        publish(newState = MvvmState.Error(e))
    }

    protected fun onComplete(){
        publish(newState = MvvmState.Completed)
    }

    private fun publish(newState : MvvmState<T>){
        state.postValue(newState)
    }


    abstract fun handleInputEvent(event : R)


    sealed class MvvmState<out T>{
        object Init : MvvmState<Nothing>()
        object Loading : MvvmState<Nothing>()
        class Next<T>(val data : T) : MvvmState<T>()
        class Error(val e : Throwable) : MvvmState<Nothing>()
        object Completed : MvvmState<Nothing>()
    }
}
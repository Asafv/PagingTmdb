package com.bartovapps.pagingtmdb.mvvm_core

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject

abstract class MvvmBaseViewModel<T, R> : ViewModel() {

    private val eventsDispatcher : PublishSubject<R> = PublishSubject.create()
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

        eventsSubscription = eventsDispatcher.subscribeOn(Schedulers.io()).filter { it != null }.
            observeOn(AndroidSchedulers.mainThread()).
            doOnNext {
                clearEventDispatcher()
            }.
            subscribe{
            handleInputEvent(it)
        }

    }

    private fun clearEventDispatcher(){
//        eventsDispatcher.onNext(null as R)
    }

    private val state = MutableLiveData<MvvmState<T>>().apply {
        value = MvvmState.Init
    }

    val stateStream : LiveData<MvvmState<T>>
    get() = state


    fun dispatchInputEvent(event : R){
        eventsDispatcher.onNext(event)
    }

    protected fun publish(newState : MvvmState<T>){
        state.postValue(newState)
    }


    protected abstract fun handleInputEvent(event : R)


    sealed class MvvmState<out T>{
        object Init : MvvmState<Nothing>()
        object Loading : MvvmState<Nothing>()
        class Next<T>(val data : T) : MvvmState<T>()
        class Error(val e : Throwable) : MvvmState<Nothing>()
        object Completed : MvvmState<Nothing>()
    }
}
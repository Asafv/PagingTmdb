package com.bartovapps.pagingtmdb.mvvm_core

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel<T, R> : ViewModel() {

    protected val disposables : CompositeDisposable = CompositeDisposable()

    private val state = MutableLiveData<State<T>>().apply {
        value = State.Init
    }

    val stateStream : LiveData<State<T>>
    get() = state


    protected fun onInit(){
        publish(State.Init)
    }

    protected fun onNext(value : T){
        publish(State.Next(data = value))
    }

    protected fun onLoading(){
        publish(newState = State.Loading)
    }

    protected fun onError(e : Throwable){
        publish(newState = State.Error(e))
    }

    protected fun onComplete(){
        publish(newState = State.Completed)
    }

    private fun publish(newState : State<T>){
        state.postValue(newState)
    }


    abstract fun handleInputEvent(event : R)


    sealed class State<out T>{
        object Init : State<Nothing>()
        object Loading : State<Nothing>()
        class Next<T>(val data : T) : State<T>()
        class Error(val e : Throwable) : State<Nothing>()
        object Completed : State<Nothing>()
    }
}
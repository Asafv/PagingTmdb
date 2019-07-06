package com.bartovapps.pagingtmdb.screens.main

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.content.Context
import com.bartovapps.pagingtmdb.data.Repository
import com.bartovapps.pagingtmdb.network.ApiService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import com.bartovapps.pagingtmdb.data.persistance.AppDatabase
import com.bartovapps.pagingtmdb.network.model.response.ApiResponse


class ViewModelFactory(val context: Context) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
         if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
             ApiService.newInstance()?.let {
                 return MainViewModel(Repository(it.getEndPoint(), AppDatabase.getInstance(context = context)?.moviesDao())) as T
             }
        }
        throw IllegalArgumentException("Unknown ViewModel class");
    }
}
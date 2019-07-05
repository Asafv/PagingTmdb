package com.bartovapps.pagingtmdb.screens.main

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.bartovapps.pagingtmdb.data.Repository
import com.bartovapps.pagingtmdb.network.ApiService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import com.bartovapps.pagingtmdb.network.model.response.ApiResponse


class ViewModelFactory : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
         if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
             ApiService.newInstance()?.let {
                 return MainViewModel(Repository(it.getEndPoint())) as T
             }
        }
        throw IllegalArgumentException("Unknown ViewModel class");
    }
}
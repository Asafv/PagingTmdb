package com.bartovapps.pagingtmdb

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bartovapps.pagingtmdb.data.Repository
import com.bartovapps.pagingtmdb.data.persistance.AppDatabase
import com.bartovapps.pagingtmdb.network.ApiService
import com.bartovapps.pagingtmdb.screens.details.DetailsViewModel
import com.bartovapps.pagingtmdb.screens.main.MainViewModel


class ViewModelFactory(private val context: Context) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
         if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
             return MainViewModel(Repository.getInstance(AppDatabase.invoke(context).moviesDao())) as T

        }

        if (modelClass.isAssignableFrom(DetailsViewModel::class.java)) {
            return DetailsViewModel(Repository.getInstance(AppDatabase.invoke(context).moviesDao())) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class");
    }
}
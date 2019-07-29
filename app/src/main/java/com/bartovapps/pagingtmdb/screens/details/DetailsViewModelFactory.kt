package com.bartovapps.pagingtmdb.screens.details

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bartovapps.pagingtmdb.data.Repository
import com.bartovapps.pagingtmdb.data.persistance.AppDatabase
import com.bartovapps.pagingtmdb.screens.details.DetailsViewModel
import com.bartovapps.pagingtmdb.screens.main.MainViewModel


class DetailsViewModelFactory(private val context: Context, val id: Int) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(DetailsViewModel::class.java)) {
            return DetailsViewModel(Repository.getInstance(AppDatabase.invoke(context).moviesDao()), id) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
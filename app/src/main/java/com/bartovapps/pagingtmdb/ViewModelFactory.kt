package com.bartovapps.pagingtmdb

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bartovapps.pagingtmdb.data.Repository
import com.bartovapps.pagingtmdb.screens.details.DetailsViewModel
import com.bartovapps.pagingtmdb.screens.main.MainScreenViewModel


class ViewModelFactory : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
         if (modelClass.isAssignableFrom(MainScreenViewModel::class.java)) {
             return MainScreenViewModel(Repository) as T

        }

        if (modelClass.isAssignableFrom(DetailsViewModel::class.java)) {
            return DetailsViewModel(Repository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class");
    }
}
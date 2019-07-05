package com.bartovapps.pagingtmdb.data

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.PageKeyedDataSource
import com.bartovapps.pagingtmdb.network.apis.TmdbEndpoint
import com.bartovapps.pagingtmdb.network.model.response.Movie
import javax.sql.DataSource



class TmdbDsFactory(endpoint: TmdbEndpoint) : android.arch.paging.DataSource.Factory<Int, Movie>() {

    private val moviesLiveData = MutableLiveData<PageKeyedDataSource<Int, Movie>>()
    private val dataSource = TmdbDataSource(endpoint)


    override fun create(): android.arch.paging.DataSource<Int, Movie> {
        moviesLiveData.postValue(dataSource)
        return dataSource
    }
}
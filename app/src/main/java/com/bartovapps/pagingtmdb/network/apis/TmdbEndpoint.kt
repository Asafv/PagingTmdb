package com.bartovapps.pagingtmdb.network.apis

import com.bartovapps.pagingtmdb.network.model.response.ApiResponse
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Query


interface TmdbEndpoint {
    @GET("movie/top_rated/")
    fun getTopRatedMovies(@Query("page") page: Int) : Flowable<ApiResponse>
}
package com.bartovapps.pagingtmdb.network.apis

import com.bartovapps.pagingtmdb.network.model.response.ApiResponse
import com.bartovapps.pagingtmdb.network.model.response.DetailsApiResponse
import io.reactivex.Flowable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface TmdbEndpoint {
    @GET("movie/top_rated/")
    fun getTopRatedMovies(@Query("page") page: Int) : Flowable<ApiResponse>

    @GET("movie/{movieId}")
    fun getMovieDetails(@Path("movieId") movieId: Int) : Single<DetailsApiResponse>
}
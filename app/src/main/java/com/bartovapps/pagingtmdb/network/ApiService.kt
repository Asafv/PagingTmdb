package com.bartovapps.pagingtmdb.network

import android.net.Uri
import com.bartovapps.pagingtmdb.network.apis.TmdbEndpoint
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.URI

object ApiService {
    const val TMDB_BASE_URL = "https://api.themoviedb.org/3/"
    const val API_KEY = "b331218ddcbd128634135abf7673fab5"
    const val TMDB_IMAGE_AUTHORITY = "image.tmdb.org/t/p/w500"


    fun buildImageUrl(imagePath: String): String {
        return "https://$TMDB_IMAGE_AUTHORITY$imagePath"
    }


    private val retrofit: Retrofit
    private val client: OkHttpClient

    init {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        client = OkHttpClient()
        val client: OkHttpClient =
            OkHttpClient.Builder().addInterceptor(loggingInterceptor).addInterceptor { chain ->
                val original = chain.request()
                val orgUrl = original.url()
                val newUrl = orgUrl.newBuilder().addQueryParameter("api_key", API_KEY).build()

                val reqBuilder = original.newBuilder().url(newUrl)
                chain.proceed(reqBuilder.build())
            }.build()


        retrofit = Retrofit.Builder().baseUrl(TMDB_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).client(client).build()
    }


    fun getEndPoint(): TmdbEndpoint {
        return retrofit.create(TmdbEndpoint::class.java)
    }
}
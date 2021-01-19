package com.bartovapps.pagingtmdb.network.model.response

data class DetailsApiResponse(
    val id: Int,
    val adult: Boolean = false,
    val budget: Int = 0,
    val homepage: String? = null,
    val backdrop_path: String? = null,
    val name: String,
    val imdb_id: String? = null,
    val original_language: String,
    val original_title: String,
    val overview: String? = null,
    val popularity: Double,
    val poster_path: String? = null,
    val origin_country: String,
    val release_date: String,
    val revenue: Int,
    val runtime: Int,
    val title: String,
    val vote_average: Double,
    val vote_count: Int
)
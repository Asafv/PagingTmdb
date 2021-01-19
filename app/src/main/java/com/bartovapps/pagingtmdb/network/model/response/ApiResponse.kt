package com.bartovapps.pagingtmdb.network.model.response

import com.google.gson.annotations.SerializedName

data class ApiResponse(
    @SerializedName("page") val page: Int,
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("results") val results: List<Movie>
)
package com.bartovapps.pagingtmdb.network.model.response

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "movies")
data class Movie(@PrimaryKey @ColumnInfo(name = "id") @SerializedName("id") val id: Int,
                 @ColumnInfo(name = "title") @SerializedName("title") val title : String,
                 @ColumnInfo(name = "overview") @SerializedName("overview") val overview : String,
                 @ColumnInfo(name = "release_date") @SerializedName("release_date") val releaseDate : String,
                 @ColumnInfo(name = "vote_average") @SerializedName("vote_average") val voteAverage : Double,
                 @ColumnInfo(name = "vote_count") @SerializedName("vote_count") val voteCount : Long,
                 @ColumnInfo(name = "poster_path") @SerializedName("poster_path") val posterPath : String,
                 @ColumnInfo(name = "adult") @SerializedName("adult") val adult : Boolean) {
    override fun equals(other: Any?): Boolean {
        if(other == null || other !is Movie) return false
        if(this.id != other.id) return false
        if(this.title != other.title) return false
        if(this.overview != other.overview) return false
        if(this.releaseDate != other.releaseDate) return false
        if(this.voteAverage != other.voteAverage) return false
        if(this.voteCount != other.voteCount) return false
        if(this.adult != other.adult) return false
        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + title.hashCode()
        result = 31 * result + overview.hashCode()
        result = 31 * result + releaseDate.hashCode()
        result = 31 * result + voteAverage.toInt()
        result = 31 * result + voteCount.hashCode()
        result = 31 * result + posterPath.hashCode()
        result = 31 * result + adult.hashCode()
        return result
    }


}

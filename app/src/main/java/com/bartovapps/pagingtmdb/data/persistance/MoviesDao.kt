package com.bartovapps.pagingtmdb.data.persistance

import android.arch.paging.DataSource
import android.arch.persistence.room.*
import com.bartovapps.pagingtmdb.network.model.response.Movie

@Dao
interface MoviesDao {


    @Query("SELECT * FROM movies ORDER BY page ASC")
    fun allItemsName(): DataSource.Factory<Int, Movie>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(items: List<Movie>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: Movie)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(item: Movie)

    @Delete
    fun delete(item: Movie)

    @Query("DELETE FROM movies")
    fun deleteAll(): Int

}
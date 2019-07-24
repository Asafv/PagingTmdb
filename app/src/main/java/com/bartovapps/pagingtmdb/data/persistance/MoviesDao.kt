package com.bartovapps.pagingtmdb.data.persistance

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import com.bartovapps.pagingtmdb.network.model.response.Movie

@Dao
interface MoviesDao {

    @Query("SELECT * FROM movies ORDER BY page ASC")
    fun allItemsName(): DataSource.Factory<Int, Movie>

    @Query("SELECT * FROM movies WHERE id LIKE :id")
    fun getItemById(id : Int) : LiveData<Movie>

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
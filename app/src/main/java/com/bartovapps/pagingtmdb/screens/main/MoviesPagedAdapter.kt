package com.bartovapps.pagingtmdb.screens.main

import android.arch.paging.PagedListAdapter
import android.net.Uri
import android.support.v4.app.ActivityCompat
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bartovapps.pagingtmdb.R
import com.bartovapps.pagingtmdb.R.id.movieImage
import com.bartovapps.pagingtmdb.network.ApiService
import com.bartovapps.pagingtmdb.network.model.response.Movie
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.movie_item.movieImage
import kotlinx.android.synthetic.main.movie_item.view.*
import timber.log.Timber

class MoviesPagedAdapter : PagedListAdapter<Movie, RecyclerView.ViewHolder>(MoviesPagedAdapter.MovieDiffUtilCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false)
        return MoviesViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as MoviesViewHolder).bind(getItem(position))
    }



    class MoviesViewHolder(viewItem : View) : RecyclerView.ViewHolder(viewItem){
        val options = RequestOptions().apply(
            RequestOptions.placeholderOf(ActivityCompat.getDrawable(viewItem.context, R.drawable.loading_drawable))
        ).apply(
            RequestOptions.fitCenterTransform()
        )
        fun bind(item : Movie?){
            if(item != null){
                val uri = ApiService.buildImageUrl(item.posterPath)
                val path = "https://${ApiService.TMDB_IMAGE_AUTHORITY}${item.posterPath}"
                Timber.i("Image Uri: $path" )
                Glide.with(itemView.context).
                    load(path).
                    apply(options).
                    into(itemView.movieImage)
            }
        }
    }

     class MovieDiffUtilCallback : DiffUtil.ItemCallback<Movie>(){
        override fun areItemsTheSame(oldMovie: Movie, newMovie: Movie): Boolean {
            return oldMovie.id == newMovie.id
        }

        override fun areContentsTheSame(oldMovie:  Movie, newMovie: Movie): Boolean {
            return oldMovie == newMovie
        }
    }
}
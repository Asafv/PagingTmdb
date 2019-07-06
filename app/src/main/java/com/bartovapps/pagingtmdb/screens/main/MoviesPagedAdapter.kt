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
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import android.text.format.DateUtils


class MoviesPagedAdapter : PagedListAdapter<MovieListItem, RecyclerView.ViewHolder>(MoviesPagedAdapter.MovieDiffUtilCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false)
        return MoviesViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        getItem(position)
        (holder as MoviesViewHolder).bind(getItem(position))
    }



    class MoviesViewHolder(viewItem : View) : RecyclerView.ViewHolder(viewItem){
        private val apiDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        private val viewDateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())

        private val options = RequestOptions().apply(
            RequestOptions.placeholderOf(ActivityCompat.getDrawable(viewItem.context, com.bartovapps.pagingtmdb.R.drawable.loading_drawable))
        ).apply(
            RequestOptions.fitCenterTransform()
        )

        fun bind(item : MovieListItem?){
            val path = "https://${ApiService.TMDB_IMAGE_AUTHORITY}${item?.posterPath}"
            Timber.i("Image Uri: $path" )
            Glide.with(itemView.context).
                load(path).
                apply(options).
                into(itemView.movieImage)

            itemView.movie_title.text = item?.title
            itemView.movie_rating.text = "${item?.voteAverage}/10"

            item?.releaseDate?.let {
                val date = apiDateFormat.parse(it)
                itemView.release_date.text = viewDateFormat.format(date)
            }

        }
    }

     class MovieDiffUtilCallback : DiffUtil.ItemCallback<MovieListItem>(){
        override fun areItemsTheSame(oldMovie: MovieListItem, newMovie: MovieListItem): Boolean {
            return oldMovie.id == newMovie.id
        }

        override fun areContentsTheSame(oldMovie:  MovieListItem, newMovie: MovieListItem): Boolean {
            return oldMovie == newMovie
        }
    }
}
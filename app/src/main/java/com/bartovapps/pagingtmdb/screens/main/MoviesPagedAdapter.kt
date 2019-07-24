package com.bartovapps.pagingtmdb.screens.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bartovapps.pagingtmdb.R
import com.bartovapps.pagingtmdb.network.ApiService
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.movie_item.view.*
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*
import androidx.core.app.ActivityCompat
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView


class MoviesPagedAdapter(val adapterClickListener: AdapterClickListener) : PagedListAdapter<MovieListItem, RecyclerView.ViewHolder>(MoviesPagedAdapter.MovieDiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false)
        return MoviesViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        getItem(position)
        (holder as MoviesViewHolder).bind(getItem(position)!!)
    }


    inner class MoviesViewHolder(viewItem : View) : RecyclerView.ViewHolder(viewItem){
        private val apiDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        private val viewDateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())

        private val options = RequestOptions().apply(
            RequestOptions.placeholderOf(ActivityCompat.getDrawable(viewItem.context, com.bartovapps.pagingtmdb.R.drawable.loading_drawable))
        ).apply(
            RequestOptions.fitCenterTransform()
        )

        fun bind(item : MovieListItem){
            val path = "https://${ApiService.TMDB_IMAGE_AUTHORITY}${item?.posterPath}"
            Timber.i("Image Uri: $path" )
            Glide.with(itemView.context).
                load(path).
                apply(options).
                into(itemView.movieImage)

            itemView.movie_title.text = item.title
            itemView.movie_rating.text = "${item.voteAverage}/10"

            item.releaseDate.let {
                val date = apiDateFormat.parse(it)
                itemView.release_date.text = viewDateFormat.format(date)
            }

            itemView.setOnClickListener {
                adapterClickListener.onItemClicked(item.id)
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


    interface AdapterClickListener{
        fun onItemClicked(id: Int)
    }
}
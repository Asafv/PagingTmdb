package com.bartovapps.pagingtmdb.screens.details


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs

import com.bartovapps.pagingtmdb.R
import com.bartovapps.pagingtmdb.TmdbApplication
import com.bartovapps.pagingtmdb.ViewModelFactory
import com.bartovapps.pagingtmdb.network.ApiService
import com.bartovapps.pagingtmdb.network.model.response.DetailsApiResponse
import com.bartovapps.pagingtmdb.network.model.response.Movie
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.fragment_details_page.*
import kotlinx.android.synthetic.main.movie_item.view.*
import timber.log.Timber

/**
 * A simple [Fragment] subclass.
 *
 */
class DetailsPage : Fragment() {

    private lateinit var viewModel : DetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setViewModel()
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_details_page, container, false)
    }

    private fun setViewModel() {
        val args : DetailsPageArgs by navArgs()
        val viewModelFactory = ViewModelFactory(activity?.application as TmdbApplication, args.id)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(DetailsViewModel::class.java)
        viewModel.detailsLiveData.observe(this,
            Observer<Movie> { t ->
                t?.let {
                    refreshUi(it)
                }
            })
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }


    private fun refreshUi(response: Movie) {

        val options = RequestOptions().apply(
            RequestOptions.placeholderOf(ActivityCompat.getDrawable(this.context!!, R.drawable.loading_drawable))
        ).apply(
            RequestOptions.fitCenterTransform()
        )


        response.backdrop_path?.let {
            Glide.with(this.context).
                load(ApiService.buildImageUrl(it)).
                apply(options).
                into(detailsPoster)
        }

        Glide.with(this.context).
            load(ApiService.buildImageUrl(response.posterPath)).
            apply(options).
            into(mainPoster)

        movieTitle.text = response.title
        rate.text = "${response.voteAverage} / 10"
        releaseDate.text = response.releaseDate
        overview.text = response.overview

    }



}

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
import com.bartovapps.pagingtmdb.ViewModelFactory
import com.bartovapps.pagingtmdb.mvvm_core.MvvmBaseViewModel
import com.bartovapps.pagingtmdb.network.ApiService
import com.bartovapps.pagingtmdb.network.model.response.DetailsApiResponse
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

    lateinit var viewModel : DetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setViewModel()
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_details_page, container, false)
    }

    private fun setViewModel() {
        val viewModelFactory = ViewModelFactory()
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(DetailsViewModel::class.java)
        viewModel.stateStream.observe(this,
            Observer<MvvmBaseViewModel.MvvmState<DetailsViewModel.DetailsScreenState>> { t ->
                t?.let {
                    handleNewState(t)
                }
            })
    }

    private fun handleNewState(t: MvvmBaseViewModel.MvvmState<DetailsViewModel.DetailsScreenState>) {
        when(t){
            is MvvmBaseViewModel.MvvmState.Init -> {

            }
            is MvvmBaseViewModel.MvvmState.Loading -> {

            }
            is MvvmBaseViewModel.MvvmState.Next<DetailsViewModel.DetailsScreenState> -> {
                handleNext(t.data)
            }
            is MvvmBaseViewModel.MvvmState.Error -> {

            }
        }

    }

    private fun handleNext(data: DetailsViewModel.DetailsScreenState) {
        when(data){
            is DetailsViewModel.DetailsScreenState.DetailsLoaded -> {
                refreshUi(data.details)
            }
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args : DetailsPageArgs by navArgs()
        viewModel.loadMovieDetails(args.id)
    }


    private fun refreshUi(response: DetailsApiResponse) {

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
            load(ApiService.buildImageUrl(response.poster_path!!)).
            apply(options).
            into(mainPoster)

        movieTitle.text = response.title
        rate.text = "${response.vote_average} / 10"
        releaseDate.text = response.release_date
        overview.text = response.overview

    }



}

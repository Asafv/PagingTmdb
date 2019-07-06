package com.bartovapps.pagingtmdb.screens.main

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.arch.paging.PagedList
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import com.bartovapps.pagingtmdb.R
import com.bartovapps.pagingtmdb.network.model.response.Movie
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var viewModel : MainViewModel
    lateinit var adapter: MoviesPagedAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        configureScreen()
        setViewModel()
    }

    private fun configureScreen() {
        adapter = MoviesPagedAdapter()
        moviesList.layoutManager = GridLayoutManager(this, 2)
        moviesList.adapter = adapter
    }

    private fun setViewModel() {
        viewModel = ViewModelProviders.of(this, ViewModelFactory(applicationContext)).get(MainViewModel::class.java)
        viewModel.moviesPagedList.observe(this,
            Observer<PagedList<MovieListItem>> { t -> adapter.submitList(t) })
    }
}

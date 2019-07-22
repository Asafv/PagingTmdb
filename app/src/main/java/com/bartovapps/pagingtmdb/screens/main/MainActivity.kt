package com.bartovapps.pagingtmdb.screens.main

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import androidx.paging.PagedList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
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
        moviesList.layoutManager = androidx.recyclerview.widget.GridLayoutManager(this, 2)
        moviesList.adapter = adapter
    }

    private fun setViewModel() {
        viewModel = ViewModelProviders.of(this, ViewModelFactory()).get(MainViewModel::class.java)
        viewModel.moviesPagedList.observe(this,
            Observer<PagedList<Movie>> { t -> adapter.submitList(t) })
    }
}

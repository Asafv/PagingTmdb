package com.bartovapps.pagingtmdb.screens.details


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.bartovapps.pagingtmdb.R
import com.bartovapps.pagingtmdb.ViewModelFactory

/**
 * A simple [Fragment] subclass.
 *
 */
class DetailsPage : Fragment() {

    lateinit var viewModel : DetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_details_page, container, false)
    }

    private fun setViewModel() {
        val viewModelFactory = ViewModelFactory()

    }

}

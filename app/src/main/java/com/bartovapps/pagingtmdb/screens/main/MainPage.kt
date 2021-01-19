package com.bartovapps.pagingtmdb.screens.main

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.paging.PagedList
import androidx.recyclerview.widget.RecyclerView

import com.bartovapps.pagingtmdb.R
import com.bartovapps.pagingtmdb.ViewModelFactory
import com.bartovapps.pagingtmdb.network.model.response.Movie
import com.bartovapps.pagingtmdb.screens.details.DetailsPageArgs
import kotlinx.android.synthetic.main.fragment_main_page.*
import timber.log.Timber

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [mainPage.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [mainPage.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class MainPage : Fragment(), MoviesPagedAdapter.AdapterClickListener {

    lateinit var viewModel: MainViewModel
    lateinit var adapter: MoviesPagedAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.i("onViewCreated: $savedInstanceState")
        configureScreen()
    }

    private fun configureScreen() {
        adapter = MoviesPagedAdapter(this)
        moviesList.layoutManager = androidx.recyclerview.widget.GridLayoutManager(
            this.context,
            2
        ) as RecyclerView.LayoutManager?
        moviesList.adapter = adapter
    }

    private fun setViewModel() {
        viewModel = ViewModelProviders.of(this, ViewModelFactory()).get(MainViewModel::class.java)
        lifecycle.addObserver(viewModel)
    }

    override fun onResume() {
        super.onResume()
        viewModel.moviesPagedList.observe(this,
            Observer<PagedList<Movie>> { t ->
                adapter.submitList(t)
                Timber.i("onChanged: ")
            })
    }

    override fun onItemClicked(item: Movie) {
        findNavController().navigate(MainPageDirections.actionMainPageToDetailsPage(item.id, item.title))
    }
}

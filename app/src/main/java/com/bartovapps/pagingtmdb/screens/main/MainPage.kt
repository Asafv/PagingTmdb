package com.bartovapps.pagingtmdb.screens.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView

import com.bartovapps.pagingtmdb.R
import com.bartovapps.pagingtmdb.ViewModelFactory
import com.bartovapps.pagingtmdb.mvvm_core.BaseViewModel
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
class MainPage : Fragment(), MoviesAdapter.AdapterClickListener {

    lateinit var viewModel : MainScreenViewModel
    lateinit var adapter: MoviesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setViewModel()
        adapter = MoviesAdapter(this)
        viewModel.handleInputEvent(MainScreenViewModel.MainScreenEvent.LoadTopRatedMovies)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) : View? {
        return inflater.inflate(R.layout.fragment_main_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.i("onViewCreated: ${savedInstanceState}")
        configureScreen()
    }

    private fun configureScreen() {
        moviesList.layoutManager = androidx.recyclerview.widget.GridLayoutManager(this.context, 2) as RecyclerView.LayoutManager?
        moviesList.adapter = adapter
    }

    private fun setViewModel() {
        viewModel = ViewModelProviders.of(this, ViewModelFactory()).get(MainScreenViewModel::class.java)
        lifecycle.addObserver(viewModel)
    }

    override fun onItemClicked(id: Int) {
        findNavController().navigate(MainPageDirections.actionMainPageToDetailsPage(id))

    }

    override fun onResume() {
        super.onResume()
        viewModel.stateStream.observe(this,
            Observer<BaseViewModel.State<MainScreenViewModel.MainScreenState>> { newState -> handleNewState(newState) })

    }

    private fun handleNewState(newState : BaseViewModel.State<MainScreenViewModel.MainScreenState>?){
        Timber.i("handleNewState: ${newState?.javaClass?.simpleName}")
        newState?.let {
            when(it){
                is BaseViewModel.State.Init -> {
                }
                is BaseViewModel.State.Loading -> {
                    Timber.i("Loading movies...")
                    progressView.visibility = View.VISIBLE
                }
                is BaseViewModel.State.Next<MainScreenViewModel.MainScreenState> -> {
                    progressView.visibility = View.GONE
                    handleNextState(it)
                }
                is BaseViewModel.State.Error -> {
                    progressView.visibility = View.GONE
                    handleError(it.e)
                }
            }
        }
    }

    private fun handleError(e: Throwable) {
        Timber.i("There was an error: ${e.message}")
    }

    private fun handleNextState(it: BaseViewModel.State.Next<MainScreenViewModel.MainScreenState>) {
        Timber.i("handleNextState: ${it.data.javaClass.simpleName}")

        when(it.data){
            is MainScreenViewModel.MainScreenState.OnMoviesLoaded -> {
                adapter.submitList(it.data.movies)
            }
            is MainScreenViewModel.MainScreenState.NavigateToDetails -> {
            }
        }
    }
}

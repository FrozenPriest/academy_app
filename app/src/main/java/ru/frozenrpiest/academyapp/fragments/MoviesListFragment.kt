package ru.frozenrpiest.academyapp.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import ru.frozenrpiest.academyapp.R
import ru.frozenrpiest.academyapp.adapters.ItemAdapterMovie
import ru.frozenrpiest.academyapp.adapters.OnMovieClicked
import ru.frozenrpiest.academyapp.data.Movie
import ru.frozenrpiest.academyapp.data.Repository
import ru.frozenrpiest.academyapp.fragments.viewmodels.LoadingState
import ru.frozenrpiest.academyapp.fragments.viewmodels.MoviesListViewModel
import ru.frozenrpiest.academyapp.fragments.viewmodels.MoviesListViewModelFactory


class MoviesListFragment : Fragment(R.layout.fragment_movies_list) {
    private lateinit var repository: Repository

    private val viewModel by viewModels<MoviesListViewModel> {
        MoviesListViewModelFactory()
    }

    private lateinit var recyclerView: RecyclerView
    private lateinit var loadingBar: SwipeRefreshLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        repository = Repository()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.recyclerViewMoviesList)
        loadingBar = view.findViewById(R.id.swipeRefreshLayout)

        setupRecyclerView()

        viewModel.moviesList.observe(
            requireActivity(),
            { repository.loadMoviesIntoAdapter(it, recyclerView.adapter as ItemAdapterMovie) })

        viewModel.loadingState.observe(requireActivity(), {
            setLoadingState(it)
        })
        loadingBar.setOnRefreshListener { refreshData() }
    }

    private fun setLoadingState(loading: LoadingState) = when(loading) {
        LoadingState.SUCCESS -> loadingBar.isRefreshing = false
        LoadingState.LOADING -> loadingBar.isRefreshing = true
        LoadingState.ERROR -> {
            loadingBar.isRefreshing = false
            Toast.makeText(context, R.string.error_loading, Toast.LENGTH_SHORT).show()
        }
    }

    private fun refreshData() {
        viewModel.reloadMovies()
    }

    private fun setupRecyclerView() {
        recyclerView.apply {
            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
            adapter = ItemAdapterMovie(context, emptyList(), clickListener)
        }
    }

    private val clickListener = object : OnMovieClicked {
        override fun onClick(movie: Movie) {
            openMovieDetails(movie)
        }
    }

    private fun openMovieDetails(movie: Movie) {
        requireActivity().supportFragmentManager.beginTransaction().apply {
            add(R.id.fragmentContainer, MovieDetailsFragment.newInstance(movie))
            addToBackStack(null)
        }.commit()
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            MoviesListFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}
package ru.frozenrpiest.academyapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.frozenrpiest.academyapp.MovieViewModel
import ru.frozenrpiest.academyapp.MovieViewModelFactory
import ru.frozenrpiest.academyapp.R
import ru.frozenrpiest.academyapp.adapters.ItemAdapterMovie
import ru.frozenrpiest.academyapp.adapters.OnMovieClicked
import ru.frozenrpiest.academyapp.data.Movie
import ru.frozenrpiest.academyapp.data.Repository


class MoviesListFragment : Fragment() {
    private lateinit var repository:Repository

    private val viewModel by viewModels<MovieViewModel> {
        MovieViewModelFactory(requireActivity().application)
    }

    private lateinit var recyclerView: RecyclerView
    private lateinit var loadingBar: ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
        repository = Repository()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movies_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.recyclerViewMoviesList)
        loadingBar = view.findViewById(R.id.loadingBar)

        setupRecyclerView()
        viewModel.moviesList.observe(requireActivity(), { repository.loadMoviesIntoAdapter(it, recyclerView.adapter as ItemAdapterMovie) })
        viewModel.loadingState.observe(requireActivity(), {
            if(it) {
                recyclerView.visibility = View.GONE
                loadingBar.visibility = View.VISIBLE
            }else{
                recyclerView.visibility = View.VISIBLE
                loadingBar.visibility = View.GONE
            }
        })
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
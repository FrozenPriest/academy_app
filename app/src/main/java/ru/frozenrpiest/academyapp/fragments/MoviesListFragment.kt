package ru.frozenrpiest.academyapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.frozenrpiest.academyapp.R
import ru.frozenrpiest.academyapp.adapters.ItemAdapterMovie
import ru.frozenrpiest.academyapp.adapters.OnMovieClicked
import ru.frozenrpiest.academyapp.data.Movie
import ru.frozenrpiest.academyapp.data.Repository


class MoviesListFragment : Fragment() {
    lateinit var repository:Repository

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
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        view?.let {
            it.findViewById<RecyclerView>(R.id.recyclerViewMoviesList).apply {
                layoutManager = GridLayoutManager(it.context, 2, GridLayoutManager.VERTICAL, false)
                adapter = ItemAdapterMovie(it.context, emptyList(), clickListener)
                repository.loadMoviesIntoAdapter(it.context, this@apply.adapter as ItemAdapterMovie)
            }
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
package ru.frozenrpiest.academyapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.frozenrpiest.academyapp.adapters.ItemAdapterMovie
import ru.frozenrpiest.academyapp.adapters.OnMovieClicked
import ru.frozenrpiest.academyapp.dataclasses.Movie


class MoviesListFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true;
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
        val recyclerViewCast = view?.findViewById<RecyclerView>(R.id.recyclerViewMoviesList)!!
        recyclerViewCast.layoutManager = GridLayoutManager(view?.context, 2, GridLayoutManager.VERTICAL, false)

        recyclerViewCast.adapter = ItemAdapterMovie(view?.context!!, DataUtils.retrieveMovies(), clickListener)
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
package ru.frozenrpiest.academyapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.frozenrpiest.academyapp.adapters.ItemAdapterCast
import ru.frozenrpiest.academyapp.adapters.ItemAdapterMovie
import ru.frozenrpiest.academyapp.adapters.LinearLayoutPagerManager


class MoviesListFragment : Fragment() {

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

        recyclerViewCast.adapter = ItemAdapterMovie(view?.context!!, DataUtils.retrieveMovies())
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
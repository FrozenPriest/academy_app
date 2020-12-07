package ru.frozenrpiest.academyapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.frozenrpiest.academyapp.adapters.ItemAdapterCast
import ru.frozenrpiest.academyapp.adapters.LinearLayoutPagerManager


private const val ARG_MOVIE = "movie"

class MovieDetailsFragment : Fragment() {
    private var movie: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            movie = it.getString(ARG_MOVIE)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movie_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupCast()
    }


    private fun setupCast() {
        val recyclerViewCast = view?.findViewById<RecyclerView>(R.id.recyclerViewCast)!!
        recyclerViewCast.layoutManager = LinearLayoutPagerManager(view?.context!!, LinearLayoutManager.HORIZONTAL, false, 4)
        recyclerViewCast.adapter = ItemAdapterCast(view?.context!!, DataUtils.retrieveCast())
    }

    companion object {

        @JvmStatic
        fun newInstance(movie: String) =
            MovieDetailsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_MOVIE, movie)
                }
            }
    }
}
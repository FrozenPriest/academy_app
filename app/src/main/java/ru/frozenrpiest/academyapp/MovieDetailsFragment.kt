package ru.frozenrpiest.academyapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.frozenrpiest.academyapp.adapters.ItemAdapterCast
import ru.frozenrpiest.academyapp.adapters.LinearLayoutPagerManager
import ru.frozenrpiest.academyapp.dataclasses.Movie


private const val ARG_MOVIE = "movie"

class MovieDetailsFragment : Fragment() {
    private lateinit var movie: Movie

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
        arguments?.let {
            movie = it.getParcelable<Movie>(ARG_MOVIE)!!
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
        savedInstanceState?.let {
            movie = it.getParcelable<Movie>(ARG_MOVIE)!!
        }
        setupView()

        view.findViewById<Button>(R.id.buttonBack).setOnClickListener{onClickBack()}
    }

    private fun onClickBack() {
        val supportFragmentManager = requireActivity().supportFragmentManager
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(ARG_MOVIE, movie)
    }



    private fun setupView() {
        view?.findViewById<TextView>(R.id.movie_name)?.text = movie.name
        view?.findViewById<TextView>(R.id.textViewAgeRestriction)?.text = movie.ageRestriction
        view?.findViewById<TextView>(R.id.textViewGenres)?.text = DataUtils.formatGenres(movie.genres)
        view?.findViewById<TextView>(R.id.textViewReviews)?.text =
            context?.resources?.getString(R.string.count_reviews)?.let {
                String.format(it, movie.reviewCount)
            }
        view?.findViewById<RatingBar>(R.id.ratingBar)?.rating = movie.rating
        //TODO storyline
        //TODO background image
        setupCast()
    }


    private fun setupCast() {
        view?.let{
            it.findViewById<RecyclerView>(R.id.recyclerViewCast).apply{
                layoutManager = LinearLayoutPagerManager(view?.context!!, LinearLayoutManager.HORIZONTAL, false, 4)
                adapter = ItemAdapterCast(it.context, DataUtils.retrieveCast().shuffled().take(5))
            }
        }

    }

    companion object {

        @JvmStatic
        fun newInstance(movie: Movie) =
            MovieDetailsFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_MOVIE, movie)
                }
            }
    }
}
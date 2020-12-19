package ru.frozenrpiest.academyapp

import android.opengl.Visibility
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.frozenrpiest.academyapp.adapters.ItemAdapterActors
import ru.frozenrpiest.academyapp.adapters.LinearLayoutPagerManager
import ru.frozenrpiest.academyapp.data.Movie


private const val ARG_MOVIE = "movie"

class MovieDetailsFragment : Fragment() {
    private lateinit var movie: Movie

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
        arguments?.let {
            movie = it.getParcelable(ARG_MOVIE)!!
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
            movie = it.getParcelable(ARG_MOVIE)!!
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
        view?.findViewById<TextView>(R.id.movie_name)?.text = movie.title
        view?.findViewById<TextView>(R.id.textViewAgeRestriction)?.text =
        context?.resources?.getString(R.string.age_restriction)?.let {
            String.format(it, movie.minimumAge)
        }
        view?.findViewById<TextView>(R.id.textViewGenres)?.text = DataUtils.formatGenres(movie.genres)
        view?.findViewById<TextView>(R.id.textViewReviews)?.text =
            context?.resources?.getString(R.string.count_reviews)?.let {
                String.format(it, movie.numberOfRatings)
            }
        view?.findViewById<RatingBar>(R.id.ratingBar)?.rating = DataUtils.roundRating(movie.ratings)
        view?.findViewById<TextView>(R.id.textViewStorylineContent)?.text = movie.overview
        context?.let {
            val backdrop = view?.findViewById<ImageView>(R.id.movie_poster)
            backdrop?.let {
                Glide.with(it).load(movie.backdrop).into(it)
            }
        }

        setupCast()
    }


    private fun setupCast() {
        if(movie.actors.isEmpty()) {
            view?.findViewById<TextView>(R.id.textViewCast)?.visibility = View.GONE
            view?.findViewById<RecyclerView>(R.id.recyclerViewCast)?.visibility = View.GONE

        } else {
            view?.let {
                it.findViewById<RecyclerView>(R.id.recyclerViewCast).apply {
                    layoutManager = LinearLayoutPagerManager(
                        it.context,
                        LinearLayoutManager.HORIZONTAL,
                        false,
                        4
                    )
                    adapter = ItemAdapterActors(it.context, movie.actors)
                }
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
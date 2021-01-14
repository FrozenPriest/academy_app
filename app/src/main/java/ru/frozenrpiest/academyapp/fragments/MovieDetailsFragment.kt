package ru.frozenrpiest.academyapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import ru.frozenrpiest.academyapp.DataUtils
import ru.frozenrpiest.academyapp.R
import ru.frozenrpiest.academyapp.adapters.ItemAdapterActors
import ru.frozenrpiest.academyapp.adapters.LinearLayoutPagerManager
import ru.frozenrpiest.academyapp.data.Movie
import ru.frozenrpiest.academyapp.fragments.viewmodels.MovieViewModel
import ru.frozenrpiest.academyapp.fragments.viewmodels.MovieViewModelFactory


private const val ARG_MOVIE = "movie"

class MovieDetailsFragment : Fragment() {

    private val viewModel by viewModels<MovieViewModel> {
        MovieViewModelFactory()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
        arguments?.let {
            viewModel.reloadMovie(it.getParcelable(ARG_MOVIE)!!)
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

        viewModel.movieData.observe(this.viewLifecycleOwner, this::setupView)
        savedInstanceState?.let {
            viewModel.reloadMovie(it.getParcelable(ARG_MOVIE)!!)
        }

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
        outState.putParcelable(ARG_MOVIE, viewModel.movieData.value)
    }



    private fun setupView(movie: Movie) {
        view?.findViewById<TextView>(R.id.movie_name)?.text = movie.title
        view?.findViewById<TextView>(R.id.textViewAgeRestriction)?.text =
        context?.resources?.getString(R.string.age_restriction)?.let {
            String.format(it, movie.minimumAge)
        }
        view?.findViewById<TextView>(R.id.textViewGenres)?.text =
            DataUtils.formatGenres(movie.genres)
        view?.findViewById<TextView>(R.id.textViewReviews)?.text =
            context?.resources?.getQuantityString(R.plurals.count_reviews, movie.numberOfRatings, movie.numberOfRatings)
        view?.findViewById<RatingBar>(R.id.ratingBar)?.rating = DataUtils.roundRating(movie.ratings / 2)
        context?.let { context ->
            view?.findViewById<TextView>(R.id.textViewStorylineContent)?.text = movie.overview?: context.getString(R.string.overview_missing)
            val backdrop = view?.findViewById<ImageView>(R.id.movie_poster)
            backdrop?.let {
                Glide.with(context)
                        .load(movie.backdrop)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .placeholder(R.drawable.no_image)
                        .error(R.drawable.no_image)
                        .centerCrop()
                        .into(it)
            }
        }

        setupCast(movie)
    }


    private fun setupCast(movie: Movie) {
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
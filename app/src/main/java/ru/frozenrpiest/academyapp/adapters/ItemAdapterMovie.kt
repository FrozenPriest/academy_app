package ru.frozenrpiest.academyapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import ru.frozenrpiest.academyapp.utils.DataUtils
import ru.frozenrpiest.academyapp.R
import ru.frozenrpiest.academyapp.data.Movie


class ItemAdapterMovie(
    private val context: Context,
    var items: List<Movie>,
    private val clickListener: OnMovieClicked
) :
    RecyclerView.Adapter<ItemAdapterMovie.ViewHolder>() {

    fun bindMovies(newMovies: List<Movie>) {
        println("Binding movies")
        items = newMovies

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(
            R.layout.view_holder_movie,
            parent,
            false
        )
        return ViewHolder(view).apply {
            textViewName = view.findViewById(R.id.textViewMovieName)
            textViewDuration = view.findViewById(R.id.textViewDuration)
            textViewReviews = view.findViewById(R.id.textViewReviews)
            textViewAgeRestriction = view.findViewById(R.id.textViewAgeRestriction)
            ratingBar = view.findViewById(R.id.ratingBar)
            textViewGenres = view.findViewById(R.id.textViewGenres)
            imageViewPoster = view.findViewById(R.id.imageViewPoster)

        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.textViewName.text = item.title
        holder.textViewDuration.text = String.format(
            context.resources.getString(R.string.count_min),
            item.runtime
        )
        holder.textViewReviews.text = context.resources.getQuantityString(
            R.plurals.count_reviews,
            item.numberOfRatings,
            item.numberOfRatings
        )
        holder.textViewAgeRestriction.text = String.format(
            context.resources.getString(R.string.age_restriction),
            item.minimumAge
        )
        holder.ratingBar.rating = DataUtils.roundRating(item.ratings / 2)
        holder.textViewGenres.text = DataUtils.formatGenres(item.genres)
        Glide.with(context)
            .load(item.poster)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(R.drawable.no_image)
            .error(R.drawable.no_image)
            .into(holder.imageViewPoster)

        holder.itemView.setOnClickListener {
            clickListener.onClick(items[position])
        }

    }

    override fun getItemCount(): Int {
        return items.size
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        lateinit var textViewName: TextView
        lateinit var textViewDuration: TextView
        lateinit var textViewReviews: TextView
        lateinit var textViewAgeRestriction: TextView
        lateinit var ratingBar: RatingBar
        lateinit var textViewGenres: TextView
        lateinit var imageViewPoster: ImageView


    }

}

interface OnMovieClicked {
    fun onClick(movie: Movie)
}
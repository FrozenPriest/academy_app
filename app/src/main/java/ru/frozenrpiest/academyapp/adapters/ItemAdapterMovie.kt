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
import ru.frozenrpiest.academyapp.DataUtils
import ru.frozenrpiest.academyapp.R
import ru.frozenrpiest.academyapp.dataclasses.Movie

class ItemAdapterMovie (val context: Context, val items: List<Movie>, private val clickListener: OnMovieClicked) :
    RecyclerView.Adapter<ItemAdapterMovie.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =  LayoutInflater.from(context).inflate(
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
        holder.textViewName.text = item.name
        holder.textViewDuration.text = String.format(context.resources.getString(R.string.count_min), item.duration)
        holder.textViewReviews.text = String.format(context.resources.getString(R.string.count_reviews), item.reviewCount)
        holder.textViewAgeRestriction.text = item.ageRestriction
        holder.ratingBar.rating = item.rating
        holder.textViewGenres.text = DataUtils.formatGenres(item.genres)
        Glide.with(context).load(item.posterPreview).into(holder.imageViewPoster)

        holder.itemView.setOnClickListener {
            clickListener.onClick(items[position])
        }

    }
    override fun getItemCount(): Int {
        return items.size
    }


    class ViewHolder(view: View) :RecyclerView.ViewHolder(view) {
        lateinit var textViewName:TextView
        lateinit var textViewDuration:TextView
        lateinit var textViewReviews:TextView
        lateinit var textViewAgeRestriction:TextView
        lateinit var ratingBar: RatingBar
        lateinit var textViewGenres:TextView
        lateinit var imageViewPoster:ImageView


    }

}

interface OnMovieClicked {
    fun onClick(movie: Movie)
}
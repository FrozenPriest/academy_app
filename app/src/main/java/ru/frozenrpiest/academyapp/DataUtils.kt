package ru.frozenrpiest.academyapp

import android.content.Context
import ru.frozenrpiest.academyapp.data.Genre
import ru.frozenrpiest.academyapp.data.Movie
import ru.frozenrpiest.academyapp.data.Actor
import java.lang.Math.round
import java.lang.StringBuilder
import kotlin.math.roundToInt

object DataUtils {

/*

    fun retrieveMovies(): List<Movie> {
        val movie = Movie("Avengers: End Game", duration = 137, ageRestriction = "13+",
                rating = 3.5f, reviewCount = 135, posterPreview = R.drawable.poster_avengers,
                genres = listOf("Action", "Adventure", "Drama"))

        return listOf(
                movie,
                movie.copy(name = "Avongers", rating = 2.5f, genres = listOf("Actions, not Adventure"), posterPreview = R.drawable.poster_tenet),
                movie.copy(name = "New name", rating = 5.0f, reviewCount = 45, duration = 555, ageRestriction = "16+", posterPreview = R.drawable.poster_tenet)
        )
    }*/

    fun formatGenres(genres: List<Genre>): CharSequence? {
        val result = StringBuilder()
        for (i in genres.indices) {
                result.append(genres[i].name)
            if(i < genres.size-1) result.append(",")
        }
        return result.toString()
    }

    fun roundRating(rating: Float) : Float {
//        val correction = if(rating >= 0) 0.5 else -0.5
        return ((rating / 0.5).roundToInt() * 0.5).toFloat()
    }
}
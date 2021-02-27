package ru.frozenrpiest.academyapp.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
        val id: Int,
        val title: String,
        val overview: String?,
        val poster: String,
        val backdrop: String,
        val ratings: Float,
        val numberOfRatings: Int,
        val minimumAge: Int,
        val runtime: Int,
        val genres: List<Genre>,
        val actors: List<Actor>
) : Parcelable

object MovieSample {
        val defaultMovie = Movie(
                id = -1,
                title = "Title",
                overview = "Overview",
                poster = "",
                backdrop = "",
                ratings = 5f,
                numberOfRatings = 345,
                minimumAge = 13,
                runtime = 111,
                genres = emptyList(),
                actors = emptyList()
        )
}
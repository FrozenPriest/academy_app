package ru.frozenrpiest.academyapp

import android.content.Context
import androidx.appcompat.content.res.AppCompatResources
import ru.frozenrpiest.academyapp.dataclasses.Movie
import ru.frozenrpiest.academyapp.dataclasses.Person

object DataUtils {

    fun retrieveCast(context: Context): List<Person> {
        val per = Person("Robert Downey Jr.", AppCompatResources.getDrawable(context, R.drawable.movie_1_)!!)
        return listOf(per, per.copy(name = "Chris Evans"), per.copy(name = "Mark Ruffalo"), per.copy(name = "Chris Hemsworth"), per.copy(name = "Chris Evans11"), per.copy(name = "Mark Ruffalo111"), per.copy(name = "Chris Hemsworth26262626"),)
    }

    fun retrieveMovies(): List<Movie> {
        val movie = Movie("Avengers: End Game")
        return listOf(movie, movie.copy(name = "Avongirs"), movie.copy(name = "Ovangers"), movie.copy(name = "Avolngirs"), movie.copy(name = "Afgegrgirs"), movie.copy(name = "Avonewryw"))
    }
}
package ru.frozenrpiest.academyapp.data.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.frozenrpiest.academyapp.BuildConfig
import ru.frozenrpiest.academyapp.data.Actor
import ru.frozenrpiest.academyapp.data.Genre
import ru.frozenrpiest.academyapp.data.Movie
import java.util.*


internal suspend fun loadMoviesNetwork(): List<Movie> = withContext(Dispatchers.IO) {
    val moviesId = RetrofitModule.moviesApi.getPopularMoviesId(
        language = Locale.getDefault().toLanguageTag(),
        page = 1
    )
    println("Popular movies id loaded")

    val movies = mutableListOf<Movie>()

    moviesId.results.forEach{
        println("Movie: id = ${it.id}")
        val movieDetails = RetrofitModule.moviesApi.getMovieInfo(
            it.id,
            language = Locale.getDefault().toLanguageTag()
        )
        println("MovieDetails loaded ")
        val movieCrewNetwork = RetrofitModule.moviesApi.getMovieCrew(
            it.id,
            language = Locale.getDefault().toLanguageTag()
        )
        println("Crew loaded")
        val cast = parseActorsNetwork(movieCrewNetwork)
        val genres = parseGenresNetwork(movieDetails.genres)
        movies.add(
            Movie(
                id = movieDetails.id,
                title = movieDetails.title,
                overview = movieDetails.overview,
                poster =  BuildConfig.BASE_URL_POSTER + (movieDetails.posterPath ?: ""),
                backdrop =  BuildConfig.BASE_URL_BACKDROP + (movieDetails.backdropPath ?: ""),
                ratings = movieDetails.voteAverage,
                numberOfRatings = movieDetails.voteCount,
                minimumAge = if(movieDetails.adult) 16 else 13,
                runtime = movieDetails.runtime?:0,
                genres = genres,
                actors = cast
            )
        )
    }
    movies
}

private fun parseGenresNetwork(genresNetwork: List<GenresItem>): List<Genre> {
    val genres = mutableListOf<Genre>()

    genresNetwork.forEach {
        genres.add(
            Genre(
                id = it.id,
                name = it.name
            )
        )
    }
    return genres
}

private fun parseActorsNetwork(movieCrewNetwork: ResponseCrew): List<Actor> {
    val cast = mutableListOf<Actor>()
    movieCrewNetwork.cast.forEach {
        cast.add(
            Actor(
                id = it.id,
                name = it.name,
                picture = BuildConfig.BASE_URL_POSTER + (it.profilePath?:"")
            )
        )
    }
    return cast
}

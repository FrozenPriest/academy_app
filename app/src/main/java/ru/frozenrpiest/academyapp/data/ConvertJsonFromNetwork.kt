package ru.frozenrpiest.academyapp.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.frozenrpiest.academyapp.BuildConfig
import ru.frozenrpiest.academyapp.data.network.GenresItem
import ru.frozenrpiest.academyapp.data.network.ResponseCrew
import ru.frozenrpiest.academyapp.data.network.RetrofitModule

private val lang = "en-US"

internal suspend fun loadMoviesNetwork(): List<Movie> = withContext(Dispatchers.IO) {
    val moviesId = RetrofitModule.moviesApi.getPopularMoviesId(language = lang, page = 1)
    println("Popular movies id loaded")

    val movies = mutableListOf<Movie>()

    moviesId.results.forEach{
        println("Movie: id = ${it.id}")
        val movieDetails = RetrofitModule.moviesApi.getMovieInfo(it.id, language = lang)
        println("MovieDetails loaded ")
        val movieCrewNetwork = RetrofitModule.moviesApi.getMovieCrew(it.id, language = lang)
        println("Crew loaded")
        val cast = parseActorsNetwork(movieCrewNetwork)
        val genres = parseGenresNetwork(movieDetails.genres)
        movies.add(
            Movie(
                id = movieDetails.id,
                title = movieDetails.title,
                overview = movieDetails.overview ?: "Overview is missing!", //todo resource
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

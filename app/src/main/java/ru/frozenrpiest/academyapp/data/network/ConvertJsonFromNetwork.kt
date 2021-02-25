package ru.frozenrpiest.academyapp.data.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.frozenrpiest.academyapp.App
import ru.frozenrpiest.academyapp.BuildConfig
import ru.frozenrpiest.academyapp.data.Actor
import ru.frozenrpiest.academyapp.data.Genre
import ru.frozenrpiest.academyapp.data.Movie
import ru.frozenrpiest.academyapp.data.local.entities.CastEntity
import ru.frozenrpiest.academyapp.data.local.entities.GenreEntity
import ru.frozenrpiest.academyapp.data.local.entities.MovieEntity
import ru.frozenrpiest.academyapp.data.local.entities.MovieWithActorsGenres
import java.util.*

internal suspend fun loadMoviesLocal(): List<Movie> = withContext(Dispatchers.IO) {
    val moviesFromDb = App.database.moviesDao().getBestMovies()

    moviesFromDb.asSequence().map {
        Movie(
            id = it.movie.id,
            title = it.movie.title,
            overview = it.movie.overview,
            poster = it.movie.poster,
            backdrop = it.movie.backdrop,
            ratings = it.movie.ratings,
            numberOfRatings = it.movie.numberOfRatings,
            minimumAge = it.movie.minimumAge,
            runtime = it.movie.runtime,
            genres = it.genres.map { genreEntity ->
                Genre(
                    id = genreEntity.id,
                    name = genreEntity.name
                )
            }.toList(),
            actors = it.actors.map { castEntity ->
                Actor(
                    id = castEntity.id,
                    name = castEntity.name,
                    picture = castEntity.picture
                )
            }.toList(),
        )

    }.toList()
}

internal suspend fun loadMoviesNetwork(): List<Movie> = withContext(Dispatchers.IO) {
    val moviesId = RetrofitModule.moviesApi.getPopularMoviesId(
        language = Locale.getDefault().toLanguageTag(),
        page = 1
    )

    val movies = mutableListOf<Movie>()

    moviesId.results.forEach {
        val movieDetails = RetrofitModule.moviesApi.getMovieInfo(
            it.id,
            language = Locale.getDefault().toLanguageTag()
        )
        val movieCrewNetwork = RetrofitModule.moviesApi.getMovieCrew(
            it.id,
            language = Locale.getDefault().toLanguageTag()
        )
        val cast = parseActorsNetwork(movieCrewNetwork)
        val genres = parseGenresNetwork(movieDetails.genres)
        movies.add(
            Movie(
                id = movieDetails.id,
                title = movieDetails.title,
                overview = movieDetails.overview,
                poster = BuildConfig.BASE_URL_POSTER + (movieDetails.posterPath ?: ""),
                backdrop = BuildConfig.BASE_URL_BACKDROP + (movieDetails.backdropPath ?: ""),
                ratings = movieDetails.voteAverage,
                numberOfRatings = movieDetails.voteCount,
                minimumAge = if (movieDetails.adult) 16 else 13,
                runtime = movieDetails.runtime ?: 0,
                genres = genres,
                actors = cast
            )
        )
    }
    movies
}

internal suspend fun getMovieById(id: Int): Movie = withContext(Dispatchers.IO) {
    val movieDetails = RetrofitModule.moviesApi.getMovieInfo(
        id,
        language = Locale.getDefault().toLanguageTag()
    )
    val movieCrewNetwork = RetrofitModule.moviesApi.getMovieCrew(
        id,
        language = Locale.getDefault().toLanguageTag()
    )
    val cast = parseActorsNetwork(movieCrewNetwork)
    val genres = parseGenresNetwork(movieDetails.genres)
    Movie(
        id = movieDetails.id,
        title = movieDetails.title,
        overview = movieDetails.overview,
        poster = BuildConfig.BASE_URL_POSTER + (movieDetails.posterPath ?: ""),
        backdrop = BuildConfig.BASE_URL_BACKDROP + (movieDetails.backdropPath ?: ""),
        ratings = movieDetails.voteAverage,
        numberOfRatings = movieDetails.voteCount,
        minimumAge = if (movieDetails.adult) 16 else 13,
        runtime = movieDetails.runtime ?: 0,
        genres = genres,
        actors = cast
    )
}

internal suspend fun loadIntoLocalDatabase(movies: List<Movie>) = withContext(Dispatchers.IO) {

    val moviesConverted = movies.mapIndexed { pos, it ->
        MovieWithActorsGenres(
            movie = MovieEntity(
                id = it.id,
                title = it.title,
                overview = it.overview,
                poster = it.poster,
                backdrop = it.backdrop,
                ratings = it.ratings,
                numberOfRatings = it.numberOfRatings,
                minimumAge = it.minimumAge,
                runtime = it.runtime,
                position = pos
            ),
            genres = it.genres.map { genre ->
                GenreEntity(
                    id = genre.id,
                    name = genre.name
                )
            },
            actors = it.actors.map { actor ->
                CastEntity(
                    id = actor.id,
                    name = actor.name,
                    picture = actor.picture
                )
            }
        )
    }
    App.database.moviesDao().insertAll(moviesConverted)
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
                picture = BuildConfig.BASE_URL_POSTER + (it.profilePath ?: "")
            )
        )
    }
    return cast
}

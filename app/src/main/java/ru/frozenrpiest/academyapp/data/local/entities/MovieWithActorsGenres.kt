package ru.frozenrpiest.academyapp.data.local.entities

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class MovieWithActorsGenres(
    @Embedded val movie: MovieEntity,
    @Relation(
        parentColumn = "movie_id",
        entityColumn = "genre_id",
        associateBy = Junction(
            GenresMovieCrossRef::class,
            parentColumn = "movieId",
            entityColumn = "genreId"
        )
    )
    val genres: List<GenreEntity>,
    @Relation(
        parentColumn = "movie_id",
        entityColumn = "cast_id",
        associateBy = Junction(
            CastMovieCrossRef::class,
            parentColumn = "movieId",
            entityColumn = "castId"
        )
    )
    val actors: List<CastEntity>
)
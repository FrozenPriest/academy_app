package ru.frozenrpiest.academyapp.data.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    primaryKeys = ["movieId", "castId"],
    foreignKeys = [
        ForeignKey(
            entity = MovieEntity::class,
            parentColumns = ["movie_id"],
            childColumns = ["movieId"]
        ),
        ForeignKey(
            entity = CastEntity::class,
            parentColumns = ["cast_id"],
            childColumns = ["castId"]
        )
    ]
)
data class CastMovieCrossRef(
    val movieId: Long,
    val castId: Long
)
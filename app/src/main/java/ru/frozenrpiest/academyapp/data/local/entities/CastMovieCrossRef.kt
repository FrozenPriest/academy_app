package ru.frozenrpiest.academyapp.data.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    primaryKeys = ["movieId", "castId"],
    foreignKeys = [
        ForeignKey(
            entity = MovieEntity::class,
            parentColumns = ["movie_id"],
            childColumns = ["movieId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = CastEntity::class,
            parentColumns = ["cast_id"],
            childColumns = ["castId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class CastMovieCrossRef(
    val movieId: Int,
    val castId: Int
)
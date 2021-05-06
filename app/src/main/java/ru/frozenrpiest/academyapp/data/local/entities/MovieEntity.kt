package ru.frozenrpiest.academyapp.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies_table")
data class MovieEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "movie_id")
    val id: Int,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "overview")
    val overview: String?,
    @ColumnInfo(name = "poster_link")
    val poster: String,
    @ColumnInfo(name = "backdrop_link")
    val backdrop: String,
    @ColumnInfo(name = "rating")
    val ratings: Float,
    @ColumnInfo(name = "rating_count")
    val numberOfRatings: Int,
    @ColumnInfo(name = "age_restriction")
    val minimumAge: Int,
    @ColumnInfo(name = "runtime")
    val runtime: Int,
    @ColumnInfo(name = "position")
    val position: Int
)
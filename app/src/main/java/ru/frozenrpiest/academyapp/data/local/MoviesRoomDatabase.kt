package ru.frozenrpiest.academyapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.frozenrpiest.academyapp.data.local.dao.MoviesDao
import ru.frozenrpiest.academyapp.data.local.entities.*

@Database(
    entities = [MovieEntity::class, CastEntity::class, GenreEntity::class, CastMovieCrossRef::class, GenresMovieCrossRef::class],
    version = 1,
    exportSchema = false
)
abstract class MoviesRoomDatabase: RoomDatabase() {
    abstract fun moviesDao(): MoviesDao


    companion object {
        const val DATABASE_NAME = "movies_database"
    }
}
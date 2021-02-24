package ru.frozenrpiest.academyapp.data.local.dao

import androidx.room.*
import ru.frozenrpiest.academyapp.data.local.entities.MovieEntity
import ru.frozenrpiest.academyapp.data.local.entities.MovieWithActorsGenres

@Dao
interface MoviesDao {

    @Transaction
    @Query("select * from movies_table order by rating limit 30")
    fun getBestMovies(): List<MovieWithActorsGenres>

    @Transaction
    @Query("SELECT * from movies_table where movie_id =:id")
    fun getMovieById(id: Long): MovieWithActorsGenres

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: MovieEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(entities: List<MovieEntity>)

    @Update
    fun update(entity: MovieEntity)

    @Update
    fun updateAll(entities: List<MovieEntity>)

    @Delete
    fun delete(entity: MovieEntity)

    @Delete
    fun delete(entities: List<MovieEntity>)

    @Query("delete from movies_table")
    fun deleteAll()
}
package ru.frozenrpiest.academyapp.data.local.dao

import androidx.room.*
import ru.frozenrpiest.academyapp.data.local.entities.*

@Dao
interface MoviesDao {

   // @Transaction
    @Query("select * from movies_table order by position ASC limit 30")
    fun getBestMovies(): List<MovieWithActorsGenres>

    @Transaction
    @Query("SELECT * from movies_table where movie_id =:id")
    fun getMovieById(id: Long): MovieWithActorsGenres

    @Transaction
    fun insertAll(entities: List<MovieWithActorsGenres>) {
        val movies = entities.map { it.movie }

        val actors = mutableSetOf<CastEntity>()
        entities.forEach { actors.addAll(it.actors) }

        val genres = mutableSetOf<GenreEntity>()
        entities.forEach { genres.addAll(it.genres) }

        val movieCastList = mutableListOf<CastMovieCrossRef>()
        val movieGenreList = mutableListOf<GenresMovieCrossRef>()
        entities.forEach {
            it.actors.map { actor ->
                movieCastList.add(
                    CastMovieCrossRef(it.movie.id, actor.id)
                )
            }
            it.genres.map { genre ->
                movieGenreList.add(
                    GenresMovieCrossRef(it.movie.id, genre.id)
                )
            }
        }

        insertAllMovies(movies)
        insertAllCast(actors.toList())
        insertAllGenres(genres.toList())
        insertAllMovieCast(movieCastList)
        insertAllMovieGenre(movieGenreList)

    }
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAllMovieGenre(toList: List<GenresMovieCrossRef>)
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAllMovieCast(toList: List<CastMovieCrossRef>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllCast(map: List<CastEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllGenres(map: List<GenreEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllMovies(entities: List<MovieEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: MovieEntity)


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
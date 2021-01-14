package ru.frozenrpiest.academyapp.data.network

import okhttp3.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.frozenrpiest.academyapp.data.Movie

interface MoviesApi {

    @GET("configuration?")
    suspend fun getConfig(@Query("api_key") api_key: String): Response

    @GET("movie/popular?&")
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int,
    ):MovieNetwork

    @GET("movie/{id}?")
    suspend fun getMovieInfo(
        @Path("id") movieId: Int,
        @Query("api-key") api_key: String,
        //todo language??
    )

    @GET("movie/{id}/credits?")
    suspend fun getCast(
        @Path("id") movieId: Int,
        @Query("api_key") api_key: String,
        @Query("language") language: String
    )

    fun getMoviesApiKey()
}

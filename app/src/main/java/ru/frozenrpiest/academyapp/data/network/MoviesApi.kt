package ru.frozenrpiest.academyapp.data.network

import okhttp3.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesApi {

    @GET("configuration?")
    suspend fun getConfig(@Query("api_key") api_key: String = RetrofitModule.getMoviesApiKey()): Response

    @GET("movie/top_rated?")
    suspend fun getTopRatedMoviesId(
        @Query("api_key") apiKey: String = RetrofitModule.getMoviesApiKey(),
        @Query("language") language: String,
        @Query("page") page: Int,
    ): ResponseMoviesId

    @GET("movie/popular?")
    suspend fun getPopularMoviesId(
        @Query("api_key") apiKey: String = RetrofitModule.getMoviesApiKey(),
        @Query("language") language: String,
        @Query("page") page: Int,
    ): ResponseMoviesId

    @GET("movie/{id}?")
    suspend fun getMovieInfo(
        @Path("id") movieId: Int,
        @Query("api_key") api_key: String = RetrofitModule.getMoviesApiKey(),
        @Query("language") language: String
    ): ResponseMovieDetails

    @GET("movie/{id}/credits?")
    suspend fun getMovieCrew(
        @Path("id") movieId: Int,
        @Query("api_key") api_key: String = RetrofitModule.getMoviesApiKey(),
        @Query("language") language: String
    ): ResponseCrew

}

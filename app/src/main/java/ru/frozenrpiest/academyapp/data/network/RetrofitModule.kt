package ru.frozenrpiest.academyapp.data.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.create
import ru.frozenrpiest.academyapp.BuildConfig

object RetrofitModule {
    private val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }

    val okHttpClient = OkHttpClient().newBuilder()
            .build()

    @Suppress("EXPERIMENTAL_API_USAGE")
    private val retrofit = Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()

    val moviesApi: MoviesApi = retrofit.create()


    init {
        System.loadLibrary("keys")
    }
    private external fun getMoviesApiKey(): String

//
}
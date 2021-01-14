package ru.frozenrpiest.academyapp.data.network

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
@Parcelize
data class Response(

	@SerialName("page")
	val page: Int,

	@SerialName("total_pages")
	val totalPages: Int,

	@SerialName("results")
	val results: List<ResultsItem>,

	@SerialName("total_results")
	val totalResults: Int
) : Parcelable

@Parcelize
data class ResultsItem(

	@SerialName("overview")
	val overview: String,

	@SerialName("original_language")
	val originalLanguage: String,

	@SerialName("original_title")
	val originalTitle: String,

	@SerialName("video")
	val video: Boolean,

	@SerialName("title")
	val title: String,

	@SerialName("genre_ids")
	val genreIds: List<Int>,

	@SerialName("poster_path")
	val posterPath: String,

	@SerialName("backdrop_path")
	val backdropPath: String,

	@SerialName("release_date")
	val releaseDate: String,

	@SerialName("popularity")
	val popularity: Double,

	@SerialName("vote_average")
	val voteAverage: Double,

	@SerialName("id")
	val id: Int,

	@SerialName("adult")
	val adult: Boolean,

	@SerialName("vote_count")
	val voteCount: Int
) : Parcelable

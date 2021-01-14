package ru.frozenrpiest.academyapp.data.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
@Serializable
data class ResponseMoviesId(

	@SerialName("page")
	val page: Int,

	@SerialName("total_pages")
	val totalPages: Int,

	@SerialName("results")
	val results: List<ResponceMovieId>,

	@SerialName("total_results")
	val totalResults: Int
)

@Serializable
data class ResponceMovieId(
	@SerialName("id")
	val id: Int,
)

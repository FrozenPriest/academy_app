package ru.frozenrpiest.academyapp.data

import androidx.recyclerview.widget.DiffUtil
import ru.frozenrpiest.academyapp.adapters.ItemAdapterMovie
import ru.frozenrpiest.academyapp.adapters.MoviesListDiffUtilCallback

class Repository {
    fun loadMoviesIntoAdapter(newMovies: List<Movie>, itemAdapterMovie: ItemAdapterMovie) {

        val moviesListDiffUtilCallback = MoviesListDiffUtilCallback(itemAdapterMovie.items, newMovies)
        itemAdapterMovie.bindMovies(newMovies)
        val diffResult = DiffUtil.calculateDiff(moviesListDiffUtilCallback)
        diffResult.dispatchUpdatesTo(itemAdapterMovie)

    }


}
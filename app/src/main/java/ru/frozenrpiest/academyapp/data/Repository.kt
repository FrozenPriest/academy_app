package ru.frozenrpiest.academyapp.data

import android.content.Context
import androidx.recyclerview.widget.DiffUtil
import kotlinx.coroutines.*
import ru.frozenrpiest.academyapp.adapters.ItemAdapterMovie
import ru.frozenrpiest.academyapp.adapters.MoviesListDiffUtilCallback

class Repository {
    private val exceptionHandler = CoroutineExceptionHandler { coroutineContext, exception ->
        println("CoroutineExceptionHandler got $exception in $coroutineContext")
    }
    private val scope = CoroutineScope(
        SupervisorJob() +
                Dispatchers.Default +
                exceptionHandler
    )
    fun loadMoviesIntoAdapter(context: Context, itemAdapterMovie: ItemAdapterMovie) {

        scope.launch {
            println("Scope opened")
            val newMovies = loadMovies(context)
            withContext(Dispatchers.Main) {
                val moviesListDiffUtilCallback = MoviesListDiffUtilCallback(itemAdapterMovie.items, newMovies)
                itemAdapterMovie.bindMovies(newMovies)
                val diffResult = DiffUtil.calculateDiff(moviesListDiffUtilCallback)
                diffResult.dispatchUpdatesTo(itemAdapterMovie)
            }

        }
    }


}
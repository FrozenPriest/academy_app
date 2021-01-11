package ru.frozenrpiest.academyapp.fragments.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.frozenrpiest.academyapp.data.Movie
import ru.frozenrpiest.academyapp.data.loadMovies

class MoviesListViewModel(
    application: Application
): AndroidViewModel(application) {
    private val _mutableMovieList = MutableLiveData<List<Movie>>(emptyList())
    private val _mutableLoadingState = MutableLiveData(false)

    val moviesList: LiveData<List<Movie>> get() = _mutableMovieList
    val loadingState: LiveData<Boolean> get() = _mutableLoadingState

    init {
        reloadMovies()
    }

    fun reloadMovies() {
        viewModelScope.launch {
            _mutableLoadingState.value = true

            _mutableMovieList.value = loadMovies(getApplication())

            _mutableLoadingState.value = false

        }
    }

}
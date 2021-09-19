package ru.frozenrpiest.academyapp.fragments.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.frozenrpiest.academyapp.data.Movie
import ru.frozenrpiest.academyapp.data.network.loadIntoLocalDatabase
import ru.frozenrpiest.academyapp.data.network.loadMoviesLocal
import ru.frozenrpiest.academyapp.data.network.loadMoviesNetwork

class MoviesListViewModel() : ViewModel() {
    private val _mutableMovieList = MutableLiveData<List<Movie>>(emptyList())
    private val _mutableLoadingState = MutableLiveData(LoadingState.LOADING)

    val moviesList: LiveData<List<Movie>> get() = _mutableMovieList
    val loadingState: LiveData<LoadingState> get() = _mutableLoadingState

    init {
        reloadLocal()
        reloadMovies()
    }

    private fun reloadLocal() {
        viewModelScope.launch {
            _mutableMovieList.value = loadMoviesLocal()
        }
    }

    fun reloadMovies() {
        viewModelScope.launch {
            _mutableLoadingState.value = LoadingState.LOADING

            try {
                val networkResult = loadMoviesNetwork()
                _mutableMovieList.value = networkResult
                _mutableLoadingState.value = LoadingState.SUCCESS

                loadIntoLocalDatabase(networkResult)
            } catch (e: Exception) {
                _mutableLoadingState.value = LoadingState.ERROR
            }


        }
    }

}
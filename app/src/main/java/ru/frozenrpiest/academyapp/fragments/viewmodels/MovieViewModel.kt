package ru.frozenrpiest.academyapp.fragments.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.frozenrpiest.academyapp.data.Movie

class MovieViewModel : ViewModel() {
    private val _mutableMovie = MutableLiveData<Movie>()

    val movieData: LiveData<Movie> get() = _mutableMovie


    fun reloadMovie(movie: Movie) {
        viewModelScope.launch {
            _mutableMovie.value = movie
        }
    }

}
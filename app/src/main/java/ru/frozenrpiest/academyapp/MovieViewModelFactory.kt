package ru.frozenrpiest.academyapp

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.frozenrpiest.academyapp.data.Repository

@Suppress("UNCHECKED_CAST")
class MovieViewModelFactory(private val application: Application): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = when (modelClass) {
        MovieViewModel::class.java -> MovieViewModel(application)
        else -> throw IllegalArgumentException("$modelClass is not registered ViewModel")
    } as T
}
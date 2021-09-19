package ru.frozenrpiest.academyapp.fragments.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

@Suppress("UNCHECKED_CAST")
class MovieViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = when (modelClass) {
        MovieViewModel::class.java -> MovieViewModel()
        else -> throw IllegalArgumentException("$modelClass is not registered ViewModel")
    } as T
}
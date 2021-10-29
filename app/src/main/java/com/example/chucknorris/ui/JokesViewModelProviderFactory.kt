package com.example.chucknorris.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.chucknorris.repository.JokesRepository

class JokesViewModelProviderFactory(
    val jokesRepository: JokesRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return JokesViewModel(jokesRepository) as T
    }
}
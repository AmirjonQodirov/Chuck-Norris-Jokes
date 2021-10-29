package com.example.chucknorris.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chucknorris.model.JokesResponse
import com.example.chucknorris.model.Value
import com.example.chucknorris.repository.JokesRepository
import com.example.chucknorris.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class JokesViewModel(
    val jokesRepository: JokesRepository
) : ViewModel() {

    val jokes: MutableLiveData<Resource<JokesResponse>> = MutableLiveData()

    fun getJokes(count: Int) = viewModelScope.launch {
        jokes.postValue(Resource.Loading())
        val response = jokesRepository.getJokes(count.toString())
        jokes.postValue(handleJokesResponse(response))
    }

    private fun handleJokesResponse(response: Response<JokesResponse>) : Resource<JokesResponse> {
        if(response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    fun saveJoke(value: Value) = viewModelScope.launch {
        jokesRepository.upsert(value)
    }

    fun getSavedJokes() = jokesRepository.getSavedJokes()

    fun deleteJoke(value: Value) = viewModelScope.launch {
        jokesRepository.deleteJoke(value)
    }

}
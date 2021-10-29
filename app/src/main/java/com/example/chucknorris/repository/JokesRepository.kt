package com.example.chucknorris.repository

import com.example.chucknorris.db.ValueDatabase
import com.example.chucknorris.model.Value
import com.example.chucknorris.network.RetrofitInstance

class JokesRepository(
    val db: ValueDatabase
) {
    suspend fun getJokes(count: String) =
        RetrofitInstance.api.getJokes(count)

    suspend fun upsert(value: Value) = db.getValueDao().upsert(value)

    fun getSavedJokes() = db.getValueDao().getAllJokes()

    suspend fun deleteJoke(value: Value) = db.getValueDao().deleteJoke(value)
}
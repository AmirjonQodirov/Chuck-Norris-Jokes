package com.example.chucknorris.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.chucknorris.model.Value

@Dao
interface ValueDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(value: Value): Long

    @Query("SELECT * FROM jokes")
    fun getAllJokes(): LiveData<List<Value>>

    @Delete
    suspend fun deleteJoke(value: Value)
}
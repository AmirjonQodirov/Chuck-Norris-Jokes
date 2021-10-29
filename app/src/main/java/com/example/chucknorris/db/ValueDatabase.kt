package com.example.chucknorris.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.chucknorris.model.Value

@Database(
    entities = [Value::class],
    version = 1
)

abstract class ValueDatabase : RoomDatabase() {

    abstract fun getValueDao(): ValueDao

    companion object {
        @Volatile
        private var instance: ValueDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDatabase(context).also { instance = it }
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                ValueDatabase::class.java,
                "jokes_db.db"
            ).build()
    }
}
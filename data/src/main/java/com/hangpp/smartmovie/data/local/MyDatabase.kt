package com.hangpp.smartmovie.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hangpp.smartmovie.data.model.MovieLiked

@Database(entities = [MovieLiked::class], version = 2)
abstract class MyDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}
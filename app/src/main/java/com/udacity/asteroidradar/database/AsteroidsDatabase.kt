package com.udacity.asteroidradar.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.udacity.asteroidradar.Asteroid

@Database(entities = [Asteroid::class], version = 1)
abstract class AsteroidsDatabase : RoomDatabase() {

    abstract val dao: AsteroidDao

    companion object {
        @Volatile
        private  var INSTANCE: AsteroidsDatabase? = null

        fun getDatabase(context: Context): AsteroidsDatabase {

            synchronized(this) {

                var instance = INSTANCE

                if (instance == null) {

                    instance = Room.databaseBuilder(
                            context.applicationContext,
                            AsteroidsDatabase::class.java,
                            "asteroids_database").build()
                    INSTANCE = instance
                }
                return instance

            }
        }

    }
}
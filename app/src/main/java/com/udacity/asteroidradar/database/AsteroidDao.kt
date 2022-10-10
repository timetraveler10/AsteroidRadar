package com.udacity.asteroidradar.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.udacity.asteroidradar.Asteroid

@Dao
interface AsteroidDao {

    //added filter options
    @Query("SELECT * FROM asteroid_table WHERE closeApproachDate = :today")
    fun getTodayAsteroids(today: String): LiveData<List<Asteroid>>

    @Query("SELECT * FROM asteroid_table WHERE closeApproachDate >= :today AND closeApproachDate <= :untilDate ORDER BY closeApproachDate")
    fun getWeekAsteroids(today: String, untilDate:String): LiveData<List<Asteroid>>

    @Query("SELECT * FROM asteroid_table WHERE closeApproachDate >= :today ORDER BY closeApproachDate")
    fun getAllAsteroids(today: String): LiveData<List<Asteroid>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAsteroid(asteroid: Asteroid)

    @Query("DELETE FROM asteroid_table WHERE closeApproachDate =:date  ")
    suspend fun deleteLastDayAsteroids(date: String)
}
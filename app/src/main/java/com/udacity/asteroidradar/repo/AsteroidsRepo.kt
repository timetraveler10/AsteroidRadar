package com.udacity.asteroidradar.repo

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.api.RetrofitService
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.database.AsteroidsDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.time.LocalDate

class AsteroidsRepo(database: AsteroidsDatabase) {

    private val dao = database.dao
    private val retrofitService = RetrofitService.getInstance()

    @SuppressLint("NewApi")
    val today = LocalDate.now().toString()

    var pictureOfDay: MutableLiveData<PictureOfDay> = MutableLiveData()

    private var savedAsteroids: LiveData<List<Asteroid>>? = null

    private var weekAsteroids: LiveData<List<Asteroid>>? = null

    private var todayAsteroids: LiveData<List<Asteroid>>? = null

    var asteroids = MediatorLiveData<List<Asteroid>>()

    @SuppressLint("NewApi")
    fun getWeekAsteroids() {
        weekAsteroids = dao.getWeekAsteroids(today , LocalDate.now().plusDays(6).toString())
        asteroids.addSource(weekAsteroids!!) {
            asteroids.value = it
        }
    }

    fun getAllSavedAsteroids() {
        savedAsteroids = dao.getAllAsteroids(today)
        asteroids.addSource(savedAsteroids!!) {
            asteroids.value = it
        }
    }


    @SuppressLint("NewApi")
    fun getTodayAsteroid() {
        todayAsteroids = dao.getTodayAsteroids(today = today)
        asteroids.addSource(todayAsteroids!!) {
            asteroids?.value = (it)
        }
    }


    private suspend fun insertAsteroids(asteroids: List<Asteroid>) {
        withContext(Dispatchers.IO) {
            asteroids.forEach {
                dao.insertAsteroid(it)
            }
        }
    }


    suspend fun getAsteroidsFromServer() {
        withContext(Dispatchers.IO) {
            val request = retrofitService.getAsteroids(startDate = "", endDate = "")
            if (request.isSuccessful && request.body() != null) {
                val result = parseAsteroidsJsonResult(JSONObject(request.body()!!))
                insertAsteroids(result)
            }
        }

    }

    @SuppressLint("NewApi")
    suspend fun deletePreviousDayAsteroids() {
        val prevDayStr = LocalDate.now().minusDays(1).toString()
        dao.deleteLastDayAsteroids(prevDayStr)
    }

    suspend fun getPictureOfTheDay() {

        return withContext(Dispatchers.Main) {
            try {
                pictureOfDay.value = retrofitService.getPictureOfTheDay()
            } catch (e: Exception) {
                pictureOfDay.value = null
            }
        }
    }
}
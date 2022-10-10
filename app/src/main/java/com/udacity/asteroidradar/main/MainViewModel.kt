package com.udacity.asteroidradar.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.database.AsteroidsDatabase
import com.udacity.asteroidradar.repo.AsteroidsRepo
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val database = AsteroidsDatabase.getDatabase(getApplication())
    private val repo = AsteroidsRepo(database)

    val pictureOfDay: LiveData<PictureOfDay>
        get() = repo.pictureOfDay

    val asteroids: LiveData<List<Asteroid>>?
        get() = repo.asteroids


    init {
        viewModelScope.launch {
            repo.getPictureOfTheDay()
        }
        showSavedAsteroids()

    }

    fun showSavedAsteroids() {
        repo.getAllSavedAsteroids()

    }

    fun showWeekAsteroids() {
        repo.getWeekAsteroids()
    }

    fun showTodayAsteroids() {
        repo.getTodayAsteroid()

    }

    fun fetchIfDBEmpty() {
        // fetching asteroid data if db is empty
        viewModelScope.launch {
            repo.getAsteroidsFromServer()
        }

    }

}




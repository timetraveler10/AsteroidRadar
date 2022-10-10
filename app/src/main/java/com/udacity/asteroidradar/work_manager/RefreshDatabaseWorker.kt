package com.udacity.asteroidradar.work_manager

import android.annotation.SuppressLint
import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.udacity.asteroidradar.database.AsteroidsDatabase
import com.udacity.asteroidradar.repo.AsteroidsRepo
import retrofit2.HttpException

class RefreshDatabaseWorker(application: Context, params: WorkerParameters) :
    CoroutineWorker(application, params) {

    companion object {
        const val WORK_NAME = "RefreshDatabaseWork"
    }

    @SuppressLint("NewApi")
    override suspend fun doWork(): Result {
        val database = AsteroidsDatabase.getDatabase(applicationContext)
        val repo = AsteroidsRepo(database)

        return try {
            repo.getAsteroidsFromServer()
            repo.deletePreviousDayAsteroids()
            Result.success()
        } catch (e: HttpException) {
            Result.retry()
        }
    }
}
package com.udacity.asteroidradar.work_manager

import android.app.Application
import android.os.Build
import androidx.work.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class AsteroidRadarApplication : Application() {
    private val applicationScope = CoroutineScope(Dispatchers.Default)

    override fun onCreate() {
        super.onCreate()
        applicationScope.launch {
            setupRecurringWork()
        }
    }

    private fun setupRecurringWork() {

        val constants = Constraints.Builder()
            .setRequiresCharging(true)
            .setRequiresBatteryNotLow(true)
            .setRequiredNetworkType(NetworkType.UNMETERED).apply {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                    setRequiresDeviceIdle(true)
            }.build()

        val repeatingRequest = PeriodicWorkRequestBuilder<RefreshDatabaseWorker>(
            1 ,
            TimeUnit.DAYS
        ).setConstraints(constants).build()

        WorkManager.getInstance().enqueueUniquePeriodicWork(
            RefreshDatabaseWorker.WORK_NAME ,
            ExistingPeriodicWorkPolicy.KEEP ,
            repeatingRequest)

    }
}
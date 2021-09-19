package ru.frozenrpiest.academyapp.workers

import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest
import java.util.concurrent.TimeUnit

class WorkRepository {

    private val constraints = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .setRequiresCharging(true)
        .build()

    val constrainedRequest =
        PeriodicWorkRequest.Builder(RefreshWorker::class.java, 8, TimeUnit.HOURS)
            .setConstraints(constraints)
            .build()
}
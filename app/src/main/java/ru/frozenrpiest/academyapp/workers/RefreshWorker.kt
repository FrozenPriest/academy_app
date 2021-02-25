package ru.frozenrpiest.academyapp.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.frozenrpiest.academyapp.data.network.loadIntoLocalDatabase
import ru.frozenrpiest.academyapp.data.network.loadMoviesNetwork

class RefreshWorker(
    appContext: Context,
    workerParams: WorkerParameters
) :
    CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        return@withContext try {
            val networkResult = loadMoviesNetwork()
            loadIntoLocalDatabase(networkResult)

            Result.success()
        }catch (e: Exception) {
            Result.failure()
        }
    }
}
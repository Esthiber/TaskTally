package edu.ucne.tasktally

import android.app.Application
import android.content.Context
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import dagger.hilt.android.HiltAndroidApp
import edu.ucne.tasktally.data.remote.sync.MyWorkerFactory
import edu.ucne.tasktally.data.remote.sync.SyncWorker
import javax.inject.Inject

@HiltAndroidApp
class TaskTallyApplication : Application(), Configuration.Provider {
    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()
}

fun triggerSyncWorker(context: Context) {
    val req = OneTimeWorkRequestBuilder<SyncWorker>().build()
    WorkManager.getInstance(context).enqueue(req)
}

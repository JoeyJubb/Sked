package uk.co.bubblebearapps.domain.worker

import androidx.work.*
import io.reactivex.rxjava3.core.Completable
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class TasksDownloaderImpl @Inject constructor(
    private val workManager: WorkManager
) : TasksDownloader {


    override fun scheduleTaskDownloads(): Completable = Completable.fromAction {
        workManager.enqueueUniquePeriodicWork(
            PERIODIC_WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            PeriodicWorkRequestBuilder<TasksDownloadWorker>(
                repeatInterval = 15L,
                repeatIntervalTimeUnit = TimeUnit.MINUTES
            ).setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.UNMETERED)
                    .build()
            ).build()
        )
    }

    override fun downloadTasksNow(): Completable = Completable.fromAction {
        workManager.enqueueUniqueWork(
            IMMEDIATE_WORK_NAME,
            ExistingWorkPolicy.KEEP,
            OneTimeWorkRequestBuilder<TasksDownloadWorker>().setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build()
            ).build()
        )
    }

    companion object {
        const val PERIODIC_WORK_NAME =
            "uk.co.bubblebearapps.domain.worker.TasksDownloaderImpl.PERIODIC_WORK_NAME"
        const val IMMEDIATE_WORK_NAME =
            "uk.co.bubblebearapps.domain.worker.TasksDownloaderImpl.IMMEDIATE_WORK_NAME"
    }
}
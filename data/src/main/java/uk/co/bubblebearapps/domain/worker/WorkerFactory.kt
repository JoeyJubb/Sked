package uk.co.bubblebearapps.domain.worker

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import uk.co.bubblebearapps.di.ChildWorkerFactory
import javax.inject.Inject
import javax.inject.Provider

class WorkerFactory @Inject constructor(
    private val workers: Map<Class<out ListenableWorker>, @JvmSuppressWildcards Provider<ChildWorkerFactory>>
) : WorkerFactory() {

    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? {

        // workerClassName is platform specific so it can't be used directly to instantiate a worker
        // on Android 6 it's `ConstraintTrackingWorker` whereas on Android 8 its the actual worker class name e.g. TextReviewWorker/StrikesUploadWorker
        // since both inherit from `ListenableWorker`, that can be used to pull out the assignable workerEntry
        val workerEntry = workers.entries.find {
            Class.forName(workerClassName).asSubclass(ListenableWorker::class.java).isAssignableFrom(it.key)
        }

        val workerFactory = workerEntry?.value

        // https://developer.android.com/reference/androidx/work/WorkerFactory
        // If a WorkerFactory is unable to create an instance of the ListenableWorker, it should return null so it can delegate to the default WorkerFactory.
        workerFactory?.let {
            return it.get().create(appContext, workerParameters)
        } ?: return null
    }
}
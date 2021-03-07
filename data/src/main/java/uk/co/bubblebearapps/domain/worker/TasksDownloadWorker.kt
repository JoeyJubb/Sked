package uk.co.bubblebearapps.domain.worker

import android.content.Context
import androidx.work.WorkerParameters
import androidx.work.rxjava3.RxWorker
import io.reactivex.rxjava3.core.Single
import org.threeten.bp.Clock
import org.threeten.bp.Instant
import timber.log.Timber
import uk.co.bubblebearapps.data.tasks.TaskEntity
import uk.co.bubblebearapps.data.tasks.TasksDao
import uk.co.bubblebearapps.data.tasks.UpdateResultEntity
import uk.co.bubblebearapps.data.tasks.remote.TasksApi
import uk.co.bubblebearapps.di.ApplicationContext
import uk.co.bubblebearapps.di.ChildWorkerFactory
import uk.co.bubblebearapps.domain.ext.mapValues
import javax.inject.Inject

class TasksDownloadWorker(
    context: Context,
    workerParameters: WorkerParameters,
    private val clock: Clock,
    private val tasksApi: TasksApi,
    private val tasksDao: TasksDao,
) : RxWorker(context, workerParameters) {

    override fun createWork(): Single<Result> =
        tasksApi.getTasks()
            .mapValues { task ->
                TaskEntity(
                    id = task.id,
                    name = task.name,
                    description = task.description,
                    type = task.type
                )
            }.flatMapCompletable { tasks ->
                tasksDao.replaceTable(tasks, Instant.now(clock))
            }.andThen(
                Single.just(Result.success())
            )
            .doOnError{
                Timber.e(it)
            }
            .onErrorResumeWith(
                tasksDao.setUpdateResult(
                    UpdateResultEntity(
                        updated = Instant.now(clock),
                        isSuccess = false
                    )
                ).andThen(
                    Single.just(Result.failure())
                )
            )

    class Factory @Inject constructor(
        private val clock: Clock,
        private val tasksApi: TasksApi,
        private val tasksDao: TasksDao,
    ) : ChildWorkerFactory {

        override fun create(appContext: Context, params: WorkerParameters): RxWorker {
            return TasksDownloadWorker(
                appContext, params, clock, tasksApi, tasksDao
            )
        }
    }
}
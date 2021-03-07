package uk.co.bubblebearapps.di

import android.content.Context
import androidx.work.Configuration
import androidx.work.ListenableWorker
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import uk.co.bubblebearapps.domain.worker.TasksDownloadWorker
import uk.co.bubblebearapps.domain.worker.WorkerFactory
import javax.inject.Singleton
import kotlin.reflect.KClass

@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER
)
@MapKey
annotation class WorkerKey(val value: KClass<out ListenableWorker>)

interface ChildWorkerFactory {
    fun create(appContext: Context, params: WorkerParameters): ListenableWorker
}

@Module(includes = [ProvidesWorkerModule::class])
abstract class WorkerModule {

    @Binds
    @IntoMap
    @WorkerKey(TasksDownloadWorker::class)
    internal abstract fun tasksDownloadWorker(workerWorker: TasksDownloadWorker.Factory): ChildWorkerFactory
}

@Module
class ProvidesWorkerModule {

    @Provides
    @Singleton
    fun workManager(
        @ApplicationContext context: Context,
        workerFactory: WorkerFactory
    ): WorkManager {

        WorkManager.initialize(
            context,
            Configuration.Builder()
                .setWorkerFactory(workerFactory)
                .build()
        )

        return WorkManager.getInstance(context)
    }
}

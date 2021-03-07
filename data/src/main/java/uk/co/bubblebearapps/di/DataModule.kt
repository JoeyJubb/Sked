package uk.co.bubblebearapps.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import uk.co.bubblebearapps.data.deviceinfo.DeviceInfoRepositoryImpl
import uk.co.bubblebearapps.data.tasks.ScheduleRepositoryImpl
import uk.co.bubblebearapps.data.tasks.TasksDao
import uk.co.bubblebearapps.data.tasks.TasksDatabase
import uk.co.bubblebearapps.data.tasks.TasksDatabaseBuilder
import uk.co.bubblebearapps.domain.repository.DeviceInfoRepository
import uk.co.bubblebearapps.domain.repository.ScheduleRepository
import uk.co.bubblebearapps.domain.worker.TasksDownloader
import uk.co.bubblebearapps.domain.worker.TasksDownloaderImpl

@Module(
    includes = [
        ProvidesDataModule::class,
        NetModule::class,
        WorkerModule::class
    ]
)
interface DataModule {

    @Binds
    fun scheduleRepository(impl: ScheduleRepositoryImpl): ScheduleRepository

    @Binds
    fun deviceInfoRepository(impl: DeviceInfoRepositoryImpl): DeviceInfoRepository

    @Binds
    fun tasksDownloaderImpl(impl: TasksDownloaderImpl): TasksDownloader

}

@Module
class ProvidesDataModule {

    @Provides
    fun tasksDatabase(tasksDatabaseBuilder: TasksDatabaseBuilder): TasksDatabase =
        tasksDatabaseBuilder.instance

    @Provides
    fun tasksDao(database: TasksDatabase): TasksDao = database.tasksDao()

}

package uk.co.bubblebearapps.domain.worker

import io.reactivex.rxjava3.core.Completable

interface TasksDownloader {

    fun scheduleTaskDownloads(): Completable

    fun downloadTasksNow(): Completable

}

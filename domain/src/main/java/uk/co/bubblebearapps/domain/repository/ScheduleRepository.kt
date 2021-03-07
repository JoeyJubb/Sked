package uk.co.bubblebearapps.domain.repository

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import uk.co.bubblebearapps.domain.model.Task
import uk.co.bubblebearapps.domain.model.TasksListMetaData

interface ScheduleRepository {

    fun observeTasks(): Observable<List<Task>>

}



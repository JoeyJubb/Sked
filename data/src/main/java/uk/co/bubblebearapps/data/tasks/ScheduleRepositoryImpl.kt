package uk.co.bubblebearapps.data.tasks

import dagger.Reusable
import io.reactivex.rxjava3.core.Observable
import uk.co.bubblebearapps.domain.ext.mapValues
import uk.co.bubblebearapps.domain.model.Task
import uk.co.bubblebearapps.domain.repository.ScheduleRepository
import javax.inject.Inject

@Reusable
class ScheduleRepositoryImpl @Inject constructor(
    private val tasksDao: TasksDao
) : ScheduleRepository {

    override fun observeTasks(): Observable<List<Task>> =
        // watch updates so that this won't emit until at least one update has been completed
        Observable.combineLatest(
            tasksDao.observeTasks(),
            tasksDao.observeUpdates()
        ) { tasks, _ ->
            tasks
        }
            .mapValues { entity ->
                Task(
                    id = entity.id,
                    name = entity.name,
                    description = entity.description,
                    type = entity.type
                )
            }

}

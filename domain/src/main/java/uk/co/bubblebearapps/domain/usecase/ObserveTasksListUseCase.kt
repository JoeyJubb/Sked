package uk.co.bubblebearapps.domain.usecase

import io.reactivex.rxjava3.core.Observable
import uk.co.bubblebearapps.domain.repository.ScheduleRepository
import uk.co.bubblebearapps.domain.model.Task
import javax.inject.Inject

class ObserveTasksListUseCase @Inject constructor(
    private val scheduleRepository: ScheduleRepository) {

    operator fun invoke(): Observable<List<Task>> = scheduleRepository.observeTasks()

}

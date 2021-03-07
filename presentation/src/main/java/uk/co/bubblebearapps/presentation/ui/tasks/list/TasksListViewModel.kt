package uk.co.bubblebearapps.presentation.ui.tasks.list

import androidx.annotation.DrawableRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.BehaviorSubject
import timber.log.Timber
import uk.co.bubblebearapps.domain.model.Task
import uk.co.bubblebearapps.domain.model.TaskType
import uk.co.bubblebearapps.domain.usecase.ObserveTasksListUseCase
import uk.co.bubblebearapps.presentation.R
import uk.co.bubblebearapps.presentation.base.BaseViewModel
import uk.co.bubblebearapps.presentation.ui.Action
import uk.co.bubblebearapps.presentation.ui.ViewState
import uk.co.bubblebearapps.presentation.util.ErrorMessageFactory
import uk.co.bubblebearapps.presentation.util.ResourcesWrapper
import javax.inject.Inject

class TasksListViewModel @Inject constructor(
    private val observeTasksListUseCase: ObserveTasksListUseCase,
    private val errorMessageFactory: ErrorMessageFactory,
    private val resourcesWrapper: ResourcesWrapper
) : BaseViewModel() {

    private val filterSubject = BehaviorSubject.createDefault(TaskType.values().toSet())

    private val _viewStateLiveData = MutableLiveData<ViewState<TasksListViewState>>()

    fun getViewStateLiveData(): LiveData<ViewState<TasksListViewState>> = _viewStateLiveData

    fun init() {
        async {
            Observable.combineLatest(
                observeTasksListUseCase(),
                filterSubject
            ) { tasks, filters ->
                tasks
                    .filter { task -> filters.contains(task.type) }
                    .map { task ->
                        PresentableTask(
                            id = task.id,
                            name = task.name,
                            description = task.description,
                            iconRes = task.getIconRes()
                        )
                    } to filters
            }
                .doOnSubscribe { _viewStateLiveData.postValue(ViewState.Busy) }
                .subscribe(
                    { (tasks, filters) ->/* success */
                        _viewStateLiveData.postValue(ViewState.Ready(TasksListViewState(tasks, filters)))
                    },
                    { error ->/* error */
                        Timber.e(error)
                        _viewStateLiveData.postValue(
                            ViewState.Error(
                                message = errorMessageFactory.getUserMessage(error),
                                action = Action(
                                    label = resourcesWrapper.getString(R.string.retry),
                                    invokable = { init() }
                                )
                            )
                        )
                    }
                )
        }
    }

    fun onTaskClicked(presentableTask: PresentableTask) {
        //TODO feature not implemented
    }

    fun onFilterChanged(filters: Set<TaskType>) {
        filterSubject.onNext(filters)
    }
}

@DrawableRes
private fun Task.getIconRes(): Int =
    when (type) {
        TaskType.GENERAL -> R.drawable.ic_task_general_24dp
        TaskType.MEDICATION -> R.drawable.ic_task_medication_24dp
        TaskType.HYDRATION -> R.drawable.ic_task_hydration_24dp
        TaskType.NUTRITION -> R.drawable.ic_task_nutrition_24dp
    }

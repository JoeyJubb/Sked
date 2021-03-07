package uk.co.bubblebearapps.presentation.ui.tasks.list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.rxjava3.core.Observable
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import uk.co.bubblebearapps.domain.model.Task
import uk.co.bubblebearapps.domain.model.TaskType
import uk.co.bubblebearapps.domain.usecase.ObserveTasksListUseCase
import uk.co.bubblebearapps.getOrAwaitValue
import uk.co.bubblebearapps.presentation.ui.ViewState
import uk.co.bubblebearapps.presentation.util.ErrorMessageFactory
import uk.co.bubblebearapps.presentation.util.ResourcesWrapper
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class TasksListViewModelTest {

    private val observeTasksListUseCase: ObserveTasksListUseCase = mock()
    private val errorMessageFactory: ErrorMessageFactory = mock()
    private val resourcesWrapper: ResourcesWrapper = mock()

    lateinit var viewmodel: TasksListViewModel

    @Rule
    @JvmField
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        whenever(resourcesWrapper.getString(any())).thenReturn("")
        whenever(errorMessageFactory.getUserMessage(any())).thenReturn("")

        viewmodel =
            TasksListViewModel(observeTasksListUseCase, errorMessageFactory, resourcesWrapper)
    }

    @Test
    fun `init journey`() {
        // given observeTasksListUseCase emits an error the first time around, and an empty list the second
        val error = mock<Throwable>()
        whenever(observeTasksListUseCase.invoke()).thenReturn(
            Observable.error(error),
            Observable.just(emptyList())
        )

        // when the viewmodel is init
        viewmodel.init()

        // then the view state will be an error
        val errorState = viewmodel.getViewStateLiveData().getOrAwaitValue()
        assertTrue(errorState is ViewState.Error)

        // when the action on the error is invoked
        errorState.action!!.invokable()

        // then observeTasksListUseCase will emit the emit list
        // and we will observe a ready state
        val readyState = viewmodel.getViewStateLiveData().getOrAwaitValue()
        assertTrue(readyState is ViewState.Ready)
    }

    @Test
    fun `onFilterChanged will filter list`() {
        // given observeTasksListUseCase emits a list
        whenever(observeTasksListUseCase.invoke()).thenReturn(
            Observable.just(
                listOf(
                    mockTask(TaskType.NUTRITION),
                    mockTask(TaskType.MEDICATION),
                    mockTask(TaskType.MEDICATION),
                    mockTask(TaskType.HYDRATION),
                    mockTask(TaskType.HYDRATION),
                    mockTask(TaskType.HYDRATION),
                    mockTask(TaskType.GENERAL),
                    mockTask(TaskType.GENERAL),
                    mockTask(TaskType.GENERAL),
                    mockTask(TaskType.GENERAL),
                )
            )
        )

        // when the viewmodel is init
        viewmodel.init()

        // then the view state will be ready and showing the whole list
        val firstState = viewmodel.getViewStateLiveData().getOrAwaitValue()
        assertTrue(firstState is ViewState.Ready)

        // now check all possible combinations of filters and their expected list size
        // this will become excessive if there are too many filters - at which point you'll
        // have to pick a reasonable spread of combinations instead of covering all
        listOf(
            emptySet<TaskType>() to 0,
            setOf(TaskType.NUTRITION) to 1,
            setOf(TaskType.MEDICATION) to 2,
            setOf(TaskType.HYDRATION) to 3,
            setOf(TaskType.GENERAL) to 4,
            setOf(TaskType.NUTRITION, TaskType.MEDICATION) to 3,
            setOf(TaskType.NUTRITION, TaskType.HYDRATION) to 4,
            setOf(TaskType.NUTRITION, TaskType.GENERAL) to 5,
            setOf(TaskType.MEDICATION, TaskType.HYDRATION) to 5,
            setOf(TaskType.MEDICATION, TaskType.GENERAL) to 6,
            setOf(TaskType.HYDRATION, TaskType.GENERAL) to 7,
            setOf(TaskType.NUTRITION, TaskType.MEDICATION, TaskType.HYDRATION) to 6,
            setOf(TaskType.NUTRITION, TaskType.MEDICATION, TaskType.GENERAL) to 7,
            setOf(TaskType.NUTRITION, TaskType.HYDRATION, TaskType.GENERAL) to 8,
            setOf(TaskType.MEDICATION, TaskType.HYDRATION, TaskType.GENERAL) to 9,
            setOf(
                TaskType.NUTRITION,
                TaskType.MEDICATION,
                TaskType.HYDRATION,
                TaskType.GENERAL
            ) to 10
        ).forEach { (filters, expectedListSize) ->

            viewmodel.onFilterChanged(filters)

            val state = viewmodel.getViewStateLiveData().getOrAwaitValue()
            assertTrue(state is ViewState.Ready)
            assertEquals(expectedListSize, state.data.tasksList.size)
        }
    }

    private fun mockTask(type: TaskType): Task = Task(
        "", "", "", type
    )


}
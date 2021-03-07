package uk.co.bubblebearapps.presentation.ui.tasks.list

import androidx.annotation.DrawableRes
import uk.co.bubblebearapps.domain.model.TaskId
import uk.co.bubblebearapps.domain.model.TaskType


data class TasksListViewState(
    val tasksList: List<PresentableTask>,
    val filters: Set<TaskType>
)

object BusyMarker
object EmptyMarker

data class PresentableTask(

    val id: TaskId,
    val name: String,
    val description: String,
    @DrawableRes
    val iconRes: Int
)
package uk.co.bubblebearapps.domain.model

import org.threeten.bp.Instant

typealias TaskId = String

data class Task(
    val id: TaskId,
    val name: String,
    val description: String,
    val type: TaskType
)

enum class TaskType {
    GENERAL,
    MEDICATION,
    HYDRATION,
    NUTRITION
}

data class TasksListMetaData(
    val downloaded: Instant
)
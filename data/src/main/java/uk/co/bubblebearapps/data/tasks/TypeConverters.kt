package uk.co.bubblebearapps.data.tasks

import androidx.room.TypeConverter
import org.threeten.bp.Instant
import uk.co.bubblebearapps.domain.model.TaskType

object TaskTypeTypeConverter {

    @TypeConverter
    @JvmStatic
    fun stringToTaskType(string: String): TaskType =
        when (string) {
            DB_VALUE_GENERAL -> TaskType.GENERAL
            DB_VALUE_MEDICATION -> TaskType.MEDICATION
            DB_VALUE_HYDRATION -> TaskType.HYDRATION
            DB_VALUE_NUTRITION -> TaskType.NUTRITION
            else -> throw IllegalArgumentException("Unrecognised task type: $string")
        }

    @TypeConverter
    @JvmStatic
    fun taskTypeToString(taskType: TaskType): String =
        when (taskType) {
            TaskType.GENERAL -> DB_VALUE_GENERAL
            TaskType.MEDICATION -> DB_VALUE_MEDICATION
            TaskType.HYDRATION -> DB_VALUE_HYDRATION
            TaskType.NUTRITION -> DB_VALUE_NUTRITION
        }


    // de-coupled from api and enum names
    private const val DB_VALUE_GENERAL = "General"
    private const val DB_VALUE_MEDICATION = "Medication"
    private const val DB_VALUE_HYDRATION = "Hydration"
    private const val DB_VALUE_NUTRITION = "Nutrition"

}

object InstantTypeConverter {

    @TypeConverter
    @JvmStatic
    fun stringToInstant(string: String): Instant = Instant.parse(string)

    @TypeConverter
    @JvmStatic
    fun instantToString(instant: Instant): String = instant.toString()

}
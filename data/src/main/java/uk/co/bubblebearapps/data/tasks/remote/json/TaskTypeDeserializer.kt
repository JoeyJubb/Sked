package uk.co.bubblebearapps.data.tasks.remote.json

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import uk.co.bubblebearapps.domain.model.TaskType
import java.lang.IllegalArgumentException
import java.lang.reflect.Type
import javax.inject.Inject

class TaskTypeDeserializer @Inject constructor() : JsonDeserializer<TaskType> {

    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext
    ): TaskType = when (json.asString) {
        API_VALUE_GENERAL -> TaskType.GENERAL
        API_VALUE_HYDRATION -> TaskType.HYDRATION
        API_VALUE_MEDICATION -> TaskType.MEDICATION
        API_VALUE_NUTRITION -> TaskType.NUTRITION
        else -> throw IllegalArgumentException("Unknown task type: $json")
    }

    companion object {
        // de-coupled from database and enum names to allow for changes in api
        private const val API_VALUE_GENERAL = "general"
        private const val API_VALUE_MEDICATION = "medication"
        private const val API_VALUE_HYDRATION = "hydration"
        private const val API_VALUE_NUTRITION = "nutrition"
    }
}
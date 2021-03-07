package uk.co.bubblebearapps.data.tasks.remote

import com.google.gson.annotations.SerializedName
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import uk.co.bubblebearapps.domain.model.TaskId
import uk.co.bubblebearapps.domain.model.TaskType

interface TasksApi {

    @GET("tasks.json")
    fun getTasks(): Single<List<GetTasksResponseItem>>

}

data class GetTasksResponseItem(
    @SerializedName("id") val id: TaskId,
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String,
    @SerializedName("type") val type: TaskType
)
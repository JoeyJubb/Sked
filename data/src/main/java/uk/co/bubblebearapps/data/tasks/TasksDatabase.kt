package uk.co.bubblebearapps.data.tasks

import android.content.Context
import androidx.room.*
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import org.threeten.bp.Instant
import uk.co.bubblebearapps.di.ApplicationContext
import uk.co.bubblebearapps.domain.model.TaskId
import uk.co.bubblebearapps.domain.model.TaskType
import javax.inject.Inject
import javax.inject.Singleton

@Entity(tableName = "tasks")
data class TaskEntity(
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = false) val id: TaskId,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "type") val type: TaskType
)

@Entity(tableName = "updates")
data class UpdateResultEntityComplete(
    @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = true) val id: Long,
    @ColumnInfo(name = "updated", index = true) val updated: Instant,
    @ColumnInfo(name = "isSuccess") val isSuccess: Boolean
)

data class UpdateResultEntity(
    @ColumnInfo(name = "updated") val updated: Instant,
    @ColumnInfo(name = "isSuccess") val isSuccess: Boolean,
)

@Dao
abstract class TasksDao {

    @Query("SELECT id, name, description, type FROM tasks")
    abstract fun observeTasks(): Observable<List<TaskEntity>>

    @Insert(entity = UpdateResultEntityComplete::class)
    abstract fun setUpdateResult(updateResult: UpdateResultEntity): Completable

    fun replaceTable(taskEntities: List<TaskEntity>, now: Instant): Completable =
        Completable.fromAction { blockingReplaceTable(taskEntities, now) }

    @Transaction
    protected open fun blockingReplaceTable(taskEntities: List<TaskEntity>, now: Instant) {
        blockingClearTable()
        blockingInsertTasks(taskEntities)
        blockingSetUpdateTime(UpdateResultEntity(now, true))
    }


    @Query("DELETE FROM tasks")
    protected abstract fun blockingClearTable()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    protected abstract fun blockingInsertTasks(taskEntities: List<TaskEntity>)

    @Insert(entity = UpdateResultEntityComplete::class)
    protected abstract fun blockingSetUpdateTime(now: UpdateResultEntity)

    @Query("SELECT updated, isSuccess from updates ORDER BY updated DESC LIMIT 1")
    abstract fun observeUpdates(): Observable<UpdateResultEntity>
}

@Database(entities = [TaskEntity::class, UpdateResultEntityComplete::class], version = 1)
@TypeConverters(
    TaskTypeTypeConverter::class,
    InstantTypeConverter::class,
)
abstract class TasksDatabase : RoomDatabase() {
    abstract fun tasksDao(): TasksDao
}

@Singleton
class TasksDatabaseBuilder @Inject constructor(@ApplicationContext context: Context) {

    val instance: TasksDatabase by lazy {
        Room.databaseBuilder(
            context.applicationContext,
            TasksDatabase::class.java, "tasks.db"
        ).build()
    }
}

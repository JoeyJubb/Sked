package uk.co.bubblebearapps.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.Reusable
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import uk.co.bubblebearapps.data.tasks.remote.interceptors.ErrorWrapperInterceptor
import uk.co.bubblebearapps.data.tasks.remote.json.TaskTypeDeserializer
import uk.co.bubblebearapps.data.tasks.remote.TasksApi
import uk.co.bubblebearapps.domain.model.TaskType
import javax.inject.Singleton

@Module()
class NetModule {

    @Provides
    @Reusable
    fun okHttpClient(
        errorWrapperInterceptor: ErrorWrapperInterceptor
    ): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(errorWrapperInterceptor)
            .build()
    }

    @Provides
    @Reusable
    fun gson(
        taskTypeDeserializer: TaskTypeDeserializer
    ): Gson =
        GsonBuilder()
            .registerTypeAdapter(TaskType::class.java, taskTypeDeserializer)
            .create()

    @Provides
    @Reusable
    fun gsonConverterFactory(gson: Gson): GsonConverterFactory =
        GsonConverterFactory.create(gson)

    @Provides
    @Singleton
    fun retrofit(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava3CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    @Provides
    @Reusable
    fun tasksApi(retrofit: Retrofit): TasksApi {
        return retrofit.create(TasksApi::class.java)
    }

    companion object {
        private const val BASE_URL = "https://adam-deleteme.s3.amazonaws.com/"
    }
}

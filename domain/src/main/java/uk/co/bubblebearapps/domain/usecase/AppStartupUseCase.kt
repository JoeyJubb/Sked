package uk.co.bubblebearapps.domain.usecase

import io.reactivex.rxjava3.core.Single
import uk.co.bubblebearapps.domain.exception.UserNotSignedInException
import uk.co.bubblebearapps.domain.model.SplashDestination
import uk.co.bubblebearapps.domain.worker.TasksDownloader
import javax.inject.Inject

class AppStartupUseCase @Inject constructor(
    private val getIsUserSignedInUseCase: GetIsUserSignedInUseCase,
    private val tasksDownloader: TasksDownloader,
) : () -> Single<SplashDestination> {

    override fun invoke(): Single<SplashDestination> =
        getIsUserSignedInUseCase()
            .andThen(tasksDownloader.scheduleTaskDownloads())
            .andThen(Single.just<SplashDestination>(SplashDestination.GoToTasksList))
            .onErrorResumeNext { error ->
                when(error){
                    is UserNotSignedInException -> Single.just(SplashDestination.GoToSignIn)
                    else -> Single.error(error)
                }
            }
}
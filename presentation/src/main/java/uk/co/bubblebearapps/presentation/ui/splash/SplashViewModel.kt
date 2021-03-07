package uk.co.bubblebearapps.presentation.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import timber.log.Timber
import uk.co.bubblebearapps.domain.model.SplashDestination
import uk.co.bubblebearapps.domain.usecase.AppStartupUseCase
import uk.co.bubblebearapps.presentation.R
import uk.co.bubblebearapps.presentation.base.BaseViewModel
import uk.co.bubblebearapps.presentation.ui.Action
import uk.co.bubblebearapps.presentation.ui.ViewState
import uk.co.bubblebearapps.presentation.util.ErrorMessageFactory
import uk.co.bubblebearapps.presentation.util.ResourcesWrapper
import javax.inject.Inject

class SplashViewModel @Inject constructor(
    private val appStartupUseCase: AppStartupUseCase,
    private val resourcesWrapper: ResourcesWrapper,
    private val errorMessageFactory: ErrorMessageFactory
) : BaseViewModel() {

    private val _viewStateLiveData = MutableLiveData<ViewState<SplashDestination>>()

    fun getViewStateLiveData(): LiveData<ViewState<SplashDestination>> = _viewStateLiveData

    init {
        getDestination()
    }

    private fun getDestination() {
        async {
            appStartupUseCase()
                .doOnSubscribe { _viewStateLiveData.postValue(ViewState.Busy) }
                .subscribe(
                    {  /* success */
                        _viewStateLiveData.postValue(ViewState.Ready(it))
                    },
                    { error ->/* error */
                        Timber.e(error)
                        _viewStateLiveData.postValue(
                            ViewState.Error(
                                message = errorMessageFactory.getUserMessage(error),
                                action = Action(
                                    label = resourcesWrapper.getString(R.string.retry),
                                    invokable = { getDestination() }
                                )
                            )
                        )
                    }
                )
        }
    }

}
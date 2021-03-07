package uk.co.bubblebearapps.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import timber.log.Timber
import uk.co.bubblebearapps.domain.usecase.ObserveInternetConnectivityUseCase
import uk.co.bubblebearapps.presentation.base.BaseViewModel
import javax.inject.Inject

class MainActivityViewModel @Inject constructor(private val observeInternetConnectivityUseCase: ObserveInternetConnectivityUseCase) :
    BaseViewModel() {

    private val _networkConnectionLiveData = MutableLiveData<Boolean>()

    fun getNetworkConnectionLiveData(): LiveData<Boolean> =
        _networkConnectionLiveData

    init {
        observeOnlineStatus()
    }

    private fun observeOnlineStatus() {
        async {
            observeInternetConnectivityUseCase()
                .subscribe(
                    { deviceIsOnline ->
                        _networkConnectionLiveData.postValue(deviceIsOnline)
                    },
                    { error ->
                        _networkConnectionLiveData.postValue(false)
                        Timber.e(error)
                    }
                )
        }
    }
}

package uk.co.bubblebearapps.domain.usecase

import io.reactivex.rxjava3.core.Observable
import uk.co.bubblebearapps.domain.repository.DeviceInfoRepository
import javax.inject.Inject

class ObserveInternetConnectivityUseCase @Inject constructor(private val deviceInfoRepository : DeviceInfoRepository) {

    operator fun invoke(): Observable<Boolean> = deviceInfoRepository.observeInternetConnection()
}

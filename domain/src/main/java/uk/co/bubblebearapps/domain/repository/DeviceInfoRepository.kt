package uk.co.bubblebearapps.domain.repository

import io.reactivex.rxjava3.core.Observable

interface DeviceInfoRepository {

    fun observeInternetConnection(): Observable<Boolean>

}

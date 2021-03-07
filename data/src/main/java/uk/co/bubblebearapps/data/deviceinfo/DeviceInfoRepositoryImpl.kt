package uk.co.bubblebearapps.data.deviceinfo

import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.net.NetworkCapabilities.NET_CAPABILITY_INTERNET
import android.net.NetworkRequest
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import dagger.Reusable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import uk.co.bubblebearapps.di.ApplicationContext
import uk.co.bubblebearapps.domain.ext.safeOnNext
import uk.co.bubblebearapps.domain.repository.DeviceInfoRepository
import javax.inject.Inject


@Reusable
class DeviceInfoRepositoryImpl @Inject constructor(@ApplicationContext private val context: Context) :
    DeviceInfoRepository {


    @Synchronized
    override fun observeInternetConnection(): Observable<Boolean> = Observable.create { emitter ->

        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val networkCallback: NetworkCallback = object : NetworkCallback() {

            override fun onAvailable(network: Network) {
                emitter.safeOnNext(true)
            }

            override fun onLost(network: Network) {
                emitter.safeOnNext(false)
            }
        }

        connectivityManager.registerCallback(networkCallback)

        emitter.setDisposable(Disposable.fromRunnable {
            connectivityManager.unregisterNetworkCallback(networkCallback)
        })

        emitter.safeOnNext(connectivityManager.isOnline())
    }

    private fun ConnectivityManager.registerCallback(networkCallback: NetworkCallback) {
        if (VERSION.SDK_INT >= VERSION_CODES.N) {
            registerDefaultNetworkCallback(networkCallback)
        } else {
            NetworkRequest
                .Builder()
                .addCapability(NET_CAPABILITY_INTERNET)
                .build()
                .let { request ->
                    registerNetworkCallback(request, networkCallback)
                }
        }
    }
}

private fun ConnectivityManager.isOnline(): Boolean =
    if (VERSION.SDK_INT >= VERSION_CODES.M) {
        getNetworkCapabilities(activeNetwork)?.hasCapability(NET_CAPABILITY_INTERNET) == true
    } else {
        activeNetworkInfo?.isConnected == true
    }


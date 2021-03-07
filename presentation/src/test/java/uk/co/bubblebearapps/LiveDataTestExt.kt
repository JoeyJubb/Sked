package uk.co.bubblebearapps

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

/**
 * A LiveData that is not observed won’t emit updates, so calling LiveData.getValue() won't work in test cases (unless you subscribe first)
 *
 * This subscribes to the LiveData, captures its first emission, then stops observing
 *
 * @see <a href="https://medium.com/androiddevelopers/unit-testing-livedata-and-other-common-observability-problems-bb477262eb04">Unit testing Live Data</a>
 */
internal fun <T> LiveData<T>.getOrAwaitValue(
    time: Long = 300,
    timeUnit: TimeUnit = TimeUnit.MILLISECONDS
): T {
    var data: T? = null
    val latch = CountDownLatch(1)
    val observer = object : Observer<T> {
        override fun onChanged(o: T?) {
            data = o
            latch.countDown()
            this@getOrAwaitValue.removeObserver(this)
        }
    }

    this.observeForever(observer)

    // Don't wait indefinitely if the LiveData is not set.
    if (!latch.await(time, timeUnit)) {
        throw TimeoutException("LiveData value was never set.")
    }

    @Suppress("UNCHECKED_CAST")
    return data as T
}
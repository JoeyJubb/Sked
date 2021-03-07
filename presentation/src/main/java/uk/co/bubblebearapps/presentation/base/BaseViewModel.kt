package uk.co.bubblebearapps.presentation.base

import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable

abstract class BaseViewModel : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        clear()
    }

    private fun clear() {
        compositeDisposable.clear()
    }

    protected fun async(work: () -> Disposable) {
        compositeDisposable.add(work())
    }
}

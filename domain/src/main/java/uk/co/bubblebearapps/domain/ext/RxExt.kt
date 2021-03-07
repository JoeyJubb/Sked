package uk.co.bubblebearapps.domain.ext

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableEmitter
import io.reactivex.rxjava3.core.Single

fun <T, R> Observable<List<T>>.mapValues(transform: (T) -> R): Observable<List<R>> =
    map { it.map(transform) }


fun <T, R> Single<List<T>>.mapValues(transform: (T) -> R): Single<List<R>> =
    map { it.map(transform) }


fun <T> ObservableEmitter<in T>.safeOnNext(value: T) {
    takeUnless { this.isDisposed }?.onNext(value)
}

fun <T> ObservableEmitter<in T>.safeOnError(error: Throwable) {
    takeUnless { this.isDisposed }?.onError(error)
}

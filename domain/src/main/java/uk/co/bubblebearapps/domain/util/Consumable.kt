package uk.co.bubblebearapps.domain.util

class Consumable<T>(private val value: T) {

    private var consumed = false

    @Synchronized
    fun get(): T? = if (!consumed) {
        consumed = true
        value
    } else {
        null
    }
}

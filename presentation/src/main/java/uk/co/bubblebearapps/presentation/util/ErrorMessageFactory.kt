package uk.co.bubblebearapps.presentation.util

interface ErrorMessageFactory {

    fun getUserMessage(throwable: Throwable): String
}

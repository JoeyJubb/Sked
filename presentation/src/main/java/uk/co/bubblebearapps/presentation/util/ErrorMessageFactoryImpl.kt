package uk.co.bubblebearapps.presentation.util

import uk.co.bubblebearapps.domain.exception.ApiException
import uk.co.bubblebearapps.presentation.R
import javax.inject.Inject

class ErrorMessageFactoryImpl @Inject constructor(resourcesWrapper: ResourcesWrapper) :
    ErrorMessageFactory, ResourcesWrapper by resourcesWrapper {

    override fun getUserMessage(throwable: Throwable) = when (throwable) {
        is ApiException -> createApiMessage(throwable)
        else -> getString(R.string.error_generic)
    }

    private fun createApiMessage(apiException: ApiException): String =
        when (apiException.resultCode) {
            in 400..499 -> {
                getString(R.string.api_error_dev)
            }
            else -> {
                getString(R.string.api_error_fallback)
            }
        }

}
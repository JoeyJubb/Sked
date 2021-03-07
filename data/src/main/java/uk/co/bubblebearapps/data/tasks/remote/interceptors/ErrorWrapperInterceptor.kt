package uk.co.bubblebearapps.data.tasks.remote.interceptors

import okhttp3.Interceptor
import okhttp3.Response
import uk.co.bubblebearapps.domain.exception.ApiException
import javax.inject.Inject

/*
 * Just throws a wrapped ApiException if something goes wrong
 */
class ErrorWrapperInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(
                chain.request()
        )

        if (response.code in 400..599) {
            throw ApiException(response.code, response.message)
        } else {
            return response
        }
    }
}

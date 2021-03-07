package uk.co.bubblebearapps.domain.exception

class ApiException(val resultCode: Int, message: String) : RuntimeException(message)

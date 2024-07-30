package com.hangpp.domain.util

sealed class Result<out T>(val data: T?) {
    data class Success<out T>(val successData: T? = null) : Result<T>(successData)
    data object Loading : Result<Nothing>(null)
    data class Error<out T>(val msg: String = "", val errorData: T? = null) : Result<T>(errorData)
}

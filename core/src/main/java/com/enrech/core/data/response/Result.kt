package com.enrech.core.data.response

sealed class Result<out R> {

    data class Success<T>(val data: T) : Result<T>()
    data class Error(val failure: Failure) : Result<Nothing>()

    inline fun <T> fold(fnFailure: (Failure) -> T, fnResult: (R) -> T) {
        when (this) {
            is Error -> fnFailure(this.failure)
            is Success -> fnResult(this.data)
        }
    }

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$failure]"
        }
    }
}
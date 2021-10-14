package com.enrech.core.data.response

sealed class Failure {

    sealed class ApiFailure: Failure() {
        object Network: ApiFailure()
        object NotFound: ApiFailure()
    }

    sealed class LocalFailure: Failure() {
        object NotFound: LocalFailure()
    }

    object Unknown: Failure()
}
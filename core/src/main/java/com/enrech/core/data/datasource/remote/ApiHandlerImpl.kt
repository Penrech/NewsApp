package com.enrech.core.data.datasource.remote

import com.enrech.core.data.response.Failure
import com.enrech.core.data.response.Result
import com.enrech.core.utils.ConnectivityHandler
import com.enrech.core.utils.ConnectivityHandlerImpl
import retrofit2.Response
import javax.inject.Inject

class ApiHandlerImpl @Inject constructor(connectivityHandlerImpl: ConnectivityHandlerImpl) :
    ApiHandler, ConnectivityHandler by connectivityHandlerImpl {

    companion object {
        private const val NOT_FOUND_CODE = 404
    }

    override suspend fun <ApiResponse> fetchApiResponse(call: suspend () -> Response<ApiResponse>): Result<ApiResponse> {
        return try {
            when {
                isNetworkAvailable() -> handleResponse(call)
                else -> Result.Error(Failure.ApiFailure.Network)
            }
        } catch (e: Exception) {
            Result.Error(Failure.ApiFailure.Unknown)
        }
    }

    private suspend fun <Input> handleResponse(call: (suspend () -> Response<Input>)): Result<Input> {
        val response = call()
        return when {
            response.isSuccessful -> {
                response.body()?.let { Result.Success(it) }
                    ?: Result.Error(Failure.ApiFailure.Unknown)
            }
            response.code() == NOT_FOUND_CODE -> Result.Error(Failure.ApiFailure.NotFound)
            else -> Result.Error(Failure.ApiFailure.Unknown)
        }
    }
}
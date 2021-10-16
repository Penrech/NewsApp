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
            call.handleResponse()
        } catch (e: Exception) {
            Result.Error(Failure.ApiFailure.Unknown)
        }
    }

    private suspend fun <Input> (suspend () -> Response<Input>).handleResponse(): Result<Input> {
        return when {
            isNetworkAvailable() -> {
                val response = this()
                when {
                    response.isSuccessful -> {
                        response.body()?.let { Result.Success(it) }
                            ?: Result.Error(Failure.ApiFailure.NotFound)
                    }
                    response.code() == NOT_FOUND_CODE -> Result.Error(Failure.ApiFailure.NotFound)
                    else -> Result.Error(Failure.ApiFailure.Unknown)
                }
            }
            else -> Result.Error(Failure.ApiFailure.Network)
        }
    }
}
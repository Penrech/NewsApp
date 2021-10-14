package com.enrech.core.data.datasource.remote

import com.enrech.core.data.response.Result
import retrofit2.Response

interface ApiHandler {
    suspend fun <ApiResponse> fetchApiResponse(call: suspend () -> Response<ApiResponse>): Result<ApiResponse>
}
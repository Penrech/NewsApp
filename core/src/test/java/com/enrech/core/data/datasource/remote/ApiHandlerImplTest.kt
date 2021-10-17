package com.enrech.core.data.datasource.remote

import com.enrech.core.data.response.Failure
import com.enrech.core.data.response.Result
import com.enrech.core.utils.ConnectivityHandlerImpl
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.Response

@ExperimentalCoroutinesApi
class ApiHandlerImplTest {

    private val connectivityHandler: ConnectivityHandlerImpl = mockk(relaxed = true)
    private lateinit var sut: ApiHandlerImpl

    @Before
    fun setUp() {
        sut = spyk(ApiHandlerImpl(connectivityHandler), recordPrivateCalls = true)
    }

    @Test
    fun `given call invocation throws exception, expect unknown api failure`() = runBlockingTest {
        //ARRANGE
        val expectedResult = Failure.ApiFailure.Unknown
        val call: suspend () -> Response<Any> = mockk(relaxed = true)
        every { connectivityHandler.isNetworkAvailable() } returns true
        coEvery { call.invoke() } throws Exception()

        //ACT
        val result = sut.fetchApiResponse(call)

        //ASSERT
        assertEquals(expectedResult, (result as Result.Error).failure)
    }

    @Test
    fun `given no network connection, expect network failure`() = runBlockingTest {
        //ARRANGE
        val expectedResult = Failure.ApiFailure.Network
        val call: suspend () -> Response<Any> = mockk(relaxed = true)
        every { connectivityHandler.isNetworkAvailable() } returns false

        //ACT
        val result = sut.fetchApiResponse(call)

        //ASSERT
        assertEquals(expectedResult, (result as Result.Error).failure)
    }

    @Test
    fun `given call response is successful but body is not valid, expect not api unknown failure`() = runBlockingTest {
        //ARRANGE
        val expectedResult = Failure.ApiFailure.Unknown
        val call: suspend () -> Response<Any> = mockk(relaxed = true)
        val response: Response<Any> = mockk(relaxed = true) {
            every { isSuccessful } returns true
            every { body() } returns null
        }
        every { connectivityHandler.isNetworkAvailable() } returns true
        coEvery { call.invoke() } returns response

        //ACT
        val result = sut.fetchApiResponse(call)

        //ASSERT
        assertEquals(expectedResult, (result as Result.Error).failure)
    }

    @Test
    fun `given call response is successful and body is valid, expect data response`() = runBlockingTest {
        //ARRANGE
        val expectedResult: Any = mockk(relaxed = true)
        val call: suspend () -> Response<Any> = mockk(relaxed = true)
        val response: Response<Any> = mockk(relaxed = true) {
            every { isSuccessful } returns true
            every { body() } returns expectedResult
        }
        every { connectivityHandler.isNetworkAvailable() } returns true
        coEvery { call.invoke() } returns response

        //ACT
        val result = sut.fetchApiResponse(call)

        //ASSERT
        assertEquals(expectedResult, (result as Result.Success).data)
    }

    @Test
    fun `given response code is 404, expect api failure not found`() = runBlockingTest {
        //ARRANGE
        val expectedResult = Failure.ApiFailure.NotFound
        val call: suspend () -> Response<Any> = mockk(relaxed = true)
        val response: Response<Any> = mockk(relaxed = true) {
            every { isSuccessful } returns false
            every { code() } returns 404
        }
        every { connectivityHandler.isNetworkAvailable() } returns true
        coEvery { call.invoke() } returns response

        //ACT
        val result = sut.fetchApiResponse(call)

        //ASSERT
        assertEquals(expectedResult, (result as Result.Error).failure)
    }

    @Test
    fun `given response fails and response code is not 404, expect api failure unknown`() = runBlockingTest {
        //ARRANGE
        val expectedResult = Failure.ApiFailure.Unknown
        val call: suspend () -> Response<Any> = mockk(relaxed = true)
        val response: Response<Any> = mockk(relaxed = true) {
            every { isSuccessful } returns false
        }
        every { connectivityHandler.isNetworkAvailable() } returns true
        coEvery { call.invoke() } returns response

        //ACT
        val result = sut.fetchApiResponse(call)

        //ASSERT
        assertEquals(expectedResult, (result as Result.Error).failure)
    }
}
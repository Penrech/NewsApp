package com.enrech.articles.data.datasource

import com.enrech.core.data.datasource.remote.ApiHandlerImpl
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.slot
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import retrofit2.Response

@ExperimentalCoroutinesApi
class ArticlesApiDatasourceTest {

    private val apiService: ArticlesApiService = mockk(relaxed = true)
    private val apiHandler: ApiHandlerImpl = mockk(relaxed = true)
    private lateinit var sut: ArticlesApiDatasource

    @Before
    fun setUp() {
        sut = ArticlesApiDatasource(apiService, apiHandler)
    }

    @Test
    fun `given get article list action, expect api service to fetch article list`() = runBlockingTest {
        //ARRANGE
        val lambdaSlot = slot<(suspend () -> Response<Any>)>()
        coJustRun { apiHandler.fetchApiResponse(capture(lambdaSlot)) }

        //ACT
        sut.getArticlesList()

        //ASSERT
        lambdaSlot.captured.invoke()
        coVerify { apiService.fetchArticlesList() }
    }

    @Test
    fun `given get article detail action with id, expect api service to fetch article detail with that id`() = runBlockingTest {
        //ARRANGE
        val id = 1
        val lambdaSlot = slot<(suspend () -> Response<Any>)>()
        coJustRun { apiHandler.fetchApiResponse(capture(lambdaSlot)) }

        //ACT
        sut.getArticleDetail(id)

        //ASSERT
        lambdaSlot.captured.invoke()
        coVerify { apiService.fetchArticleDetail(id) }
    }
}
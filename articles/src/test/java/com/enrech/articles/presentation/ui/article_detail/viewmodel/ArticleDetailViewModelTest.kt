package com.enrech.articles.presentation.ui.article_detail.viewmodel

import com.enrech.articles.domain.usecase.GetArticleDetailUseCase
import com.enrech.articles.presentation.ui.article_detail.model.ArticleDetailsEmptyViews
import com.enrech.articles.presentation.ui.article_detail.model.DetailedArticleVo
import com.enrech.articles.presentation.ui.article_detail.state.ArticleDetailViewState
import com.enrech.core.data.response.Failure
import com.enrech.core.data.response.Result
import com.enrech.core.presentation.ui.empty_view.model.CoreErrorsEmptyViews
import com.enrech.core.utils.test.CoroutineTestRule
import io.mockk.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
class ArticleDetailViewModelTest {

    private val getArticleDetailUseCase: GetArticleDetailUseCase = mockk(relaxed = true)
    private val ioDispatcher: CoroutineDispatcher = TestCoroutineDispatcher()
    private lateinit var sut: ArticleDetailViewModel

    @get:Rule
    val coroutineTestRule = CoroutineTestRule(ioDispatcher as TestCoroutineDispatcher)

    @Before
    fun setUp() {
        sut = spyk(ArticleDetailViewModel(getArticleDetailUseCase, ioDispatcher))
    }

    @Test
    fun `given load details action, expect loading state to be set at the beginning of the flow`() =
        coroutineTestRule.testDispatcher.runBlockingTest {
            //ARRANGE
            val id = 1
            val stateObserver: MutableList<ArticleDetailViewState> = mutableListOf()
            val expectedResult = ArticleDetailViewState.Loading
            val observer = sut.viewState.onEach { stateObserver.add(it) }.launchIn(this)
            mockSuccess(id = 1)

            //ACT
            sut.loadDetailsWithId(id)

            //ASSERT
            assertEquals(expectedResult, stateObserver.first())
            observer.cancel()
        }

    @Test
    fun `given load details with some article id succeed, expect state to be success with proper data`() =
        coroutineTestRule.testDispatcher.runBlockingTest {
            //ARRANGE
            val id = 1
            val expectedResult: DetailedArticleVo = mockk(relaxed = true)
            mockSuccess(id, expectedResult)

            //ACT
            sut.loadDetailsWithId(id)

            //ARRANGE
            assertEquals(
                expectedResult,
                (sut.viewState.value as ArticleDetailViewState.Success).data
            )
        }

    @Test
    fun `given load details failed with no network error, expect state to be error with no network connection empty view`() =
        coroutineTestRule.testDispatcher.runBlockingTest {
            //ARRANGE
            val id = 1
            val failure = Failure.ApiFailure.Network
            mockFailure(id, failure)
            val lambdaSlot = slot<(() -> Unit)>()
            mockkConstructor(CoreErrorsEmptyViews.NoNetworkConnection::class)
            every {
                anyConstructed<CoreErrorsEmptyViews.NoNetworkConnection>() setProperty "retryCallback" value capture(
                    lambdaSlot
                )
            } just runs

            //ACT
            sut.loadDetailsWithId(id)

            //ASSERT
            assert((sut.viewState.value as ArticleDetailViewState.Error).emptyVo is CoreErrorsEmptyViews.NoNetworkConnection)
            verifyRetryCallback(callback = lambdaSlot.captured, id = id)
        }

    @Test
    fun `given load details failed with unknown api error, expect state to be error with network database error empty view`() =
        coroutineTestRule.testDispatcher.runBlockingTest {
            //ARRANGE
            val id = 1
            val failure = Failure.ApiFailure.Unknown
            mockFailure(id, failure)
            val lambdaSlot = slot<(() -> Unit)>()
            mockkConstructor(CoreErrorsEmptyViews.NetworkDatabase::class)
            every {
                anyConstructed<CoreErrorsEmptyViews.NetworkDatabase>() setProperty "retryCallback" value capture(
                    lambdaSlot
                )
            } just runs

            //ACT
            sut.loadDetailsWithId(id)

            //ASSERT
            assert((sut.viewState.value as ArticleDetailViewState.Error).emptyVo is CoreErrorsEmptyViews.NetworkDatabase)
            verifyRetryCallback(callback = lambdaSlot.captured, id = id)
        }

    @Test
    fun `given load details failed with not found api error, expect state to be error with not found error empty view`() =
        coroutineTestRule.testDispatcher.runBlockingTest {
            //ARRANGE
            val id = 1
            val failure = Failure.ApiFailure.NotFound
            mockFailure(id, failure)

            //ACT
            sut.loadDetailsWithId(id)

            //ASSERT
            assertEquals(
                ArticleDetailsEmptyViews.NotFound(),
                (sut.viewState.value as ArticleDetailViewState.Error).emptyVo
            )
        }

    @Test
    fun `given load details failed with error not api related, expect state to be error with general error empty view`() =
        coroutineTestRule.testDispatcher.runBlockingTest {
            //ARRANGE
            val id = 1
            val failure = Failure.Unknown
            mockFailure(id, failure)

            //ACT
            sut.loadDetailsWithId(id)

            //ASSERT
            assertEquals(
                CoreErrorsEmptyViews.General(),
                (sut.viewState.value as ArticleDetailViewState.Error).emptyVo
            )
        }

    private suspend fun mockSuccess(
        id: Int = 0,
        expectedResult: DetailedArticleVo = mockk(relaxed = true)
    ) {
        coEvery { getArticleDetailUseCase.invoke(id) } returns Result.Success(expectedResult)
    }

    private suspend fun mockFailure(id: Int, failure: Failure) {
        coEvery { getArticleDetailUseCase.invoke(id) } returns Result.Error(failure)
    }

    private fun verifyRetryCallback(callback: () -> Unit, id: Int) {
        clearAllMocks()
        callback.invoke()
        coVerify { sut.loadDetailsWithId(id) }
    }

}
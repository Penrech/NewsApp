package com.enrech.articles.presentation.ui.article_list.viewmodel

import com.enrech.articles.domain.usecase.GetArticleListUseCase
import com.enrech.articles.presentation.ui.article_list.model.ArticleListEmptyViews
import com.enrech.articles.presentation.ui.article_list.model.SimpleArticleVo
import com.enrech.articles.presentation.ui.article_list.state.ArticleListViewState
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
class ArticleListViewModelTest {

    private val getArticleListUseCase: GetArticleListUseCase = mockk(relaxed = true)
    private val ioDispatcher: CoroutineDispatcher = TestCoroutineDispatcher()
    private lateinit var sut: ArticleListViewModel

    @get:Rule
    val coroutineTestRule = CoroutineTestRule(ioDispatcher as TestCoroutineDispatcher)

    @Before
    fun setUp() {
        sut = spyk(ArticleListViewModel(getArticleListUseCase, ioDispatcher))
    }

    @Test
    fun `given load article list action, expect loading state to be set at the beginning of the flow`() =
        coroutineTestRule.testDispatcher.runBlockingTest {
            //ARRANGE
            val stateObserver: MutableList<ArticleListViewState> = mutableListOf()
            val expectedResult = ArticleListViewState.Loading
            val observer = sut.viewState.onEach { stateObserver.add(it) }.launchIn(this)
            mockSuccess()

            //ACT
            sut.loadArticleList()

            //ASSERT
            assertEquals(expectedResult, stateObserver.first())
            observer.cancel()
        }

    @Test
    fun `given load article list action succeed, expect state to be success with proper data`() =
        coroutineTestRule.testDispatcher.runBlockingTest {
            //ARRANGE
            val expectedResult: List<SimpleArticleVo> = listOf(mockk(relaxed = true))
            mockSuccess(expectedResult)

            //ACT
            sut.loadArticleList()

            //ARRANGE
            assertEquals(
                expectedResult.first(),
                (sut.viewState.value as ArticleListViewState.Success).data.first()
            )
        }

    @Test
    fun `given load article list action succeed with empty list, expect state to be set to empty`() =
        coroutineTestRule.testDispatcher.runBlockingTest {
            //ARRANGE
            mockSuccess()

            //ACT
            sut.loadArticleList()

            //ARRANGE
            assertEquals(
                ArticleListEmptyViews.EmptyList(),
                (sut.viewState.value as ArticleListViewState.ErrorOrEmpty).emptyVo
            )
        }

    @Test
    fun `given load article list failed with no network error, expect state to be error with no network connection empty view`() =
        coroutineTestRule.testDispatcher.runBlockingTest {
            //ARRANGE
            val failure = Failure.ApiFailure.Network
            mockFailure(failure)
            val lambdaSlot = slot<(() -> Unit)>()
            mockkConstructor(CoreErrorsEmptyViews.NoNetworkConnection::class)
            every {
                anyConstructed<CoreErrorsEmptyViews.NoNetworkConnection>() setProperty "retryCallback" value capture(
                    lambdaSlot
                )
            } just runs

            //ACT
            sut.loadArticleList()

            //ASSERT
            assert((sut.viewState.value as ArticleListViewState.ErrorOrEmpty).emptyVo is CoreErrorsEmptyViews.NoNetworkConnection)
            verifyRetryCallback(callback = lambdaSlot.captured)
        }

    @Test
    fun `given load article list failed with api error not connection related, expect state to be error with network database error empty view`() =
        coroutineTestRule.testDispatcher.runBlockingTest {
            //ARRANGE
            val failure = Failure.ApiFailure.Unknown
            mockFailure(failure)
            val lambdaSlot = slot<(() -> Unit)>()
            mockkConstructor(CoreErrorsEmptyViews.NetworkDatabase::class)
            every {
                anyConstructed<CoreErrorsEmptyViews.NetworkDatabase>() setProperty "retryCallback" value capture(
                    lambdaSlot
                )
            } just runs

            //ACT
            sut.loadArticleList()

            //ASSERT
            assert((sut.viewState.value as ArticleListViewState.ErrorOrEmpty).emptyVo is CoreErrorsEmptyViews.NetworkDatabase)
            verifyRetryCallback(callback = lambdaSlot.captured)
        }

    @Test
    fun `given load article list failed with error not api related, expect state to be error with general error empty view`() =
        coroutineTestRule.testDispatcher.runBlockingTest {
            //ARRANGE
            val failure = Failure.Unknown
            mockFailure(failure)

            //ACT
            sut.loadArticleList()

            //ASSERT
            assertEquals(
                CoreErrorsEmptyViews.General(),
                (sut.viewState.value as ArticleListViewState.ErrorOrEmpty).emptyVo
            )
        }

    private suspend fun mockSuccess(
        expectedResult: List<SimpleArticleVo> = emptyList()
    ) {
        coEvery { getArticleListUseCase.invoke() } returns Result.Success(expectedResult)
    }

    private suspend fun mockFailure(failure: Failure) {
        coEvery { getArticleListUseCase.invoke() } returns Result.Error(failure)
    }

    private fun verifyRetryCallback(callback: () -> Unit) {
        clearAllMocks()
        callback.invoke()
        coVerify { sut.loadArticleList() }
    }

}
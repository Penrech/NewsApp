package com.enrech.articles.presentation.ui.article_list.viewmodel

import com.enrech.articles.domain.usecase.GetArticleListUseCase
import com.enrech.articles.presentation.ui.article_list.model.SimpleArticleVo
import com.enrech.core.data.response.Failure
import com.enrech.core.data.response.Result
import com.enrech.core.utils.test.CoroutineTestRule
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.spyk
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

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
    fun `given load article list action, expect loading state to be set at the beginning of the flow`() = coroutineTestRule.testDispatcher.runBlockingTest {  }

    @Test
    fun `given load article list action succeed, expect state to be success with proper data`() = coroutineTestRule.testDispatcher.runBlockingTest {  }

    @Test
    fun `given load article list failed with no network error, expect state to be error with no network connection empty view`() = coroutineTestRule.testDispatcher.runBlockingTest {  }

    @Test
    fun `given load article list failed with api error not connection related, expect state to be error with network database error empty view`() = coroutineTestRule.testDispatcher.runBlockingTest {  }

    @Test
    fun `given load article list failed with error not api related, expect state to be error with general error empty view`() = coroutineTestRule.testDispatcher.runBlockingTest {  }

    private suspend fun mockSuccess(
        expectedResult: SimpleArticleVo = mockk(relaxed = true)
    ) {
        coEvery { getArticleListUseCase.invoke() } returns Result.Success(listOf(expectedResult))
    }

    private suspend fun mockFailure(failure: Failure) {
        coEvery { getArticleListUseCase.invoke() } returns Result.Error(failure)
    }

}
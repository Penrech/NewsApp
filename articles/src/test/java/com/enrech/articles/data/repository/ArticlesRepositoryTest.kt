package com.enrech.articles.data.repository

import com.enrech.articles.data.datasource.ArticlesApiDatasource
import com.enrech.articles.data.model.ArticleDetailResponse
import com.enrech.articles.data.model.ArticleListItemResponse
import com.enrech.articles.domain.mapper.ArticleDetailMapper
import com.enrech.articles.domain.mapper.ArticleListMapper
import com.enrech.articles.presentation.ui.article_detail.model.DetailedArticleVo
import com.enrech.articles.presentation.ui.article_list.model.SimpleArticleVo
import com.enrech.core.data.response.Failure
import com.enrech.core.data.response.Result
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class ArticlesRepositoryTest {

    private val articlesApiDatasource: ArticlesApiDatasource = mock()
    private val articleListMapper: ArticleListMapper = mock()
    private val articleDetailMapper: ArticleDetailMapper = mock()
    private lateinit var sut: ArticlesRepository

    @Before
    fun setUp() {
        sut = ArticlesRepository(articlesApiDatasource, articleListMapper, articleDetailMapper)
    }

    @Test
    fun `given get article list action, ensure get article list fetched from datasource`() = runBlockingTest {
        //ARRANGE
        mockArticleListError()

        //ACT
        sut.getArticlesList()

        //ASSERT
        verify(articlesApiDatasource).getArticlesList()
    }

    @Test
    fun `given get article detail action with id, ensure get article detail fetched with id from datasource`() = runBlockingTest {
        //ARRANGE
        val id = 1
        mockArticleDetailError()

        //ACT
        sut.getArticleDetail(id)

        //ASSERT
        verify(articlesApiDatasource).getArticleDetail(id)
    }

    @Test
    fun `given article list action succeed, expect success data mapped as result`() = runBlockingTest {
        //ARRANGE
        val data: List<ArticleListItemResponse> = emptyList()
        mockArticleListSuccess(data)
        val expectedResult = emptyList<SimpleArticleVo>()
        doReturn(expectedResult).`when`(articleListMapper).toVo(any())

        //ACT
        val result = sut.getArticlesList()

        //ASSERT
        verify(articleListMapper).toVo(data)
        assertEquals(expectedResult, (result as Result.Success).data)
    }

    @Test
    fun `given article list action failed, expect failure sent as error`() = runBlockingTest {
        //ARRANGE
        val failure = Failure.ApiFailure.NotFound
        mockArticleListError(failure)

        //ACT
        val result = sut.getArticlesList()

        //ASSERT
        assertEquals(failure, (result as Result.Error).failure)
    }

    @Test
    fun `given article detail action succeed, expect success data mapped as result`() = runBlockingTest {
        //ARRANGE
        val id = 1
        val data: ArticleDetailResponse = mock()
        val expectedResult: DetailedArticleVo = mock()
        mockArticleDetailSuccess(data)
        doReturn(expectedResult).`when`(articleDetailMapper).toVo(any())

        //ACT
        val result = sut.getArticleDetail(id)

        //ASSERT
        assertEquals(expectedResult, (result as Result.Success).data)
    }

    @Test
    fun `given article detail action failed, expect failure sent as error`() = runBlockingTest {
        //ARRANGE
        val id = 1
        val failure = Failure.ApiFailure.NotFound
        mockArticleDetailError(failure)

        //ACT
        val result = sut.getArticleDetail(id)

        //ASSERT
        assertEquals(failure, (result as Result.Error).failure)
    }

    private suspend fun mockArticleListError(failure: Failure = Failure.Unknown) {
        doReturn(Result.Error(failure)).`when`(articlesApiDatasource).getArticlesList()
    }

    private suspend fun mockArticleListSuccess(data: List<ArticleListItemResponse>) {
        doReturn(Result.Success(data)).`when`(articlesApiDatasource).getArticlesList()
    }

    private suspend fun mockArticleDetailError(failure: Failure = Failure.Unknown) {
        doReturn(Result.Error(failure)).`when`(articlesApiDatasource).getArticleDetail(any())
    }

    private suspend fun mockArticleDetailSuccess(data: ArticleDetailResponse) {
        doReturn(Result.Success(data)).`when`(articlesApiDatasource).getArticleDetail(any())
    }

}
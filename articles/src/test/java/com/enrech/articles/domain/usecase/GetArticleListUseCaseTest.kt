package com.enrech.articles.domain.usecase

import com.enrech.articles.data.repository.ArticlesRepository
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class GetArticleListUseCaseTest {

    private val articlesRepository: ArticlesRepository = mock()
    private lateinit var sut: GetArticleListUseCase

    @Before
    fun setUp() {
        sut = GetArticleListUseCase(articlesRepository)
    }

    @Test
    fun `given use case invoked, expect get article list from repository`() = runBlockingTest {
        //ACT
        sut.invoke()

        //ASSERT
        verify(articlesRepository).getArticlesList()
    }
}
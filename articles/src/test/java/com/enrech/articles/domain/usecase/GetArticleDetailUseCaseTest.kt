package com.enrech.articles.domain.usecase

import com.enrech.articles.data.repository.ArticlesRepository
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class GetArticleDetailUseCaseTest {

    private val articlesRepository: ArticlesRepository = mock()
    private lateinit var sut: GetArticleDetailUseCase

    @Before
    fun setUp() {
        sut = GetArticleDetailUseCase(articlesRepository)
    }

    @Test
    fun `given use case invoked, expect get article details from repository with provided id`() = runBlockingTest {
        //ARRANGE
        val id = 1

        //ACT
        sut.invoke(id)

        //ASSERT
        verify(articlesRepository).getArticleDetail(id)
    }
}
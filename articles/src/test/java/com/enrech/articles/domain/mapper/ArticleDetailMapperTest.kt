package com.enrech.articles.domain.mapper

import com.enrech.articles.data.model.ArticleDetailResponse
import com.enrech.articles.presentation.ui.article_detail.model.DetailedArticleVo
import org.junit.Assert.assertEquals
import org.junit.Test

class ArticleDetailMapperTest {

    private val sut = ArticleDetailMapper()

    @Test
    fun `given article from dto to vo mapped, expect result to consistent`() {
        //ARRANGE
        val entryData = mockedDto
        val expectedResult = mockedVo

        //ACT
        val result = sut.toVo(entryData)

        //ASSERT
        assertEquals(expectedResult, result)
    }

    private val mockedDto =
        ArticleDetailResponse(
            id = 1,
            title = "title",
            subtitle = "subtitle",
            body = "body",
            date = "01/01/2021 00:01"
        )

    private val mockedVo =
        DetailedArticleVo (
            id = 1,
            title = "title",
            subtitle = "subtitle",
            body = "body",
            date = "01/01/2021 00:01"
        )
}
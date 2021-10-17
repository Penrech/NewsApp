package com.enrech.articles.domain.mapper

import com.enrech.articles.data.model.ArticleListItemResponse
import com.enrech.articles.presentation.ui.article_list.model.SimpleArticleVo
import org.junit.Assert.assertEquals
import org.junit.Test

class ArticleListMapperTest {

    private val sut = ArticleListMapper()

    @Test
    fun `given article list from dto to vo mapped, expect result to consistent`() {
        //ARRANGE
        val entryData = mockedDtoList
        val expectedResult = mockedVoList

        //ACT
        val result = sut.toVo(entryData)

        //ASSERT
        assertEquals(expectedResult.first(), result.first())
    }

    private val mockedDtoList: List<ArticleListItemResponse> = listOf(
        ArticleListItemResponse(
            id = 1,
            title = "title",
            subtitle = "subtitle",
            date = "01/01/2021 00:01"
        )
    )

    private val mockedVoList: List<SimpleArticleVo> = listOf(
        SimpleArticleVo(
            id = 1,
            title = "title",
            subtitle = "subtitle",
            date = "01/01/2021 00:01"
        )
    )
}
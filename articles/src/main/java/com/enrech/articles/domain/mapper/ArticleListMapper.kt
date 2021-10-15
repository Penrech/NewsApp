package com.enrech.articles.domain.mapper

import com.enrech.articles.data.model.ArticleListItemResponse
import com.enrech.articles.presentation.model.SimpleArticleVo
import javax.inject.Inject

class ArticleListMapper @Inject constructor() {

    fun toVo(dtoList: List<ArticleListItemResponse>): List<SimpleArticleVo> =
        dtoList.map { fromListItemDtoToListItemVo(it) }

    private fun fromListItemDtoToListItemVo(dtoItem: ArticleListItemResponse): SimpleArticleVo =
        SimpleArticleVo(
            id = dtoItem.id,
            title = dtoItem.title,
            subtitle = dtoItem.subtitle,
            date = dtoItem.date
        )
}